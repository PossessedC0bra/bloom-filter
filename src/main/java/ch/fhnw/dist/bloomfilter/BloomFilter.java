package ch.fhnw.dist.bloomfilter;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class BloomFilter {

    private static final Random RANDOM = new Random();

    private final HashFunction[] hashFunctions;
    private final boolean[] bits;

    /**
     * Initializes a new bloom filter. Calculates the optimal filter size and number of hash functions based on the
     * given number of expected elements <code>n</code> and the expected false positive rate <code>p</code>
     *
     * @param n expected number of elements to be inserted
     * @param p false positive rate
     */
    public BloomFilter(int n, double p) {
        int m = Math.max(1, (int) -((n * Math.log(p)) / (Math.log(2) * Math.log(2))));
        int k = Math.max(1, (int) (((double) m / n) * Math.log(2)));

        hashFunctions = new HashFunction[k];
        for (int i = 0; i < k; i++) {
            hashFunctions[i] = Hashing.murmur3_128(RANDOM.nextInt());
        }

        bits = new boolean[m];
    }

    /* *************************************************************************** */

    /**
     * Adds the given string into the bloom filter.
     *
     * @param s the string to add into the bloom filter
     */
    public void add(String s) {
        for (HashFunction hashFunction : hashFunctions) {
            bits[getIndex(hashFunction.hashBytes(s.getBytes(StandardCharsets.UTF_8)).asInt())] = true;
        }
    }

    /**
     * Returns <code>true</code> when the given string <b>might</b> have been added to this bloom filter or
     * <code>false</code> when the given string <b>definitely</b> was not added to the bloom filter
     *
     * @param s the string to test
     * @return true if the given string might have been added </br>
     * false if the given string was definitely not added
     */
    public boolean mightContain(String s) {
        for (HashFunction hashFunction : hashFunctions) {
            if (!bits[getIndex(hashFunction.hashBytes(s.getBytes(StandardCharsets.UTF_8)).asInt())]) {
                return false;
            }
        }

        return true;
    }

    private int getIndex(int hash) {
        return Math.abs(hash) % bits.length;
    }
}
