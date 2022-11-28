package ch.fhnw.dist.bloomfilter;

import ch.fhnw.dist.util.FileUtil;

public class Main {

    public static void main(String[] args) {
        double p = Double.parseDouble(args[0]);

        String[] words = FileUtil.getWords(FileUtil.getResourceFile("/words.txt"));

        BloomFilter bloomFilter = new BloomFilter(words.length / 2, p);
        for (int i = 0; i < words.length; i++) {
            // insert only every other word
            if (i % 2 == 0) {
                continue;
            }

            bloomFilter.add(words[i]);
        }

        int falsePositives = 0;
        for (int i = 0; i < words.length; i++) {
            // skip every word at an even index
            if (i % 2 != 0) {
                continue;
            }

            if (bloomFilter.mightContain(words[i])) {
                falsePositives++;
            }
        }

        System.out.println(String.format("False positives: %d", falsePositives));
        System.out.println(String.format("False positives rate: %f", (double) falsePositives / (words.length / 2)));
    }
}
