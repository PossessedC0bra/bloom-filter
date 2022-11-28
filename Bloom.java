import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Bloom {
    /**
     * Bloom Filter
     * 
     */
    private int k, m;
    private int[] filter;
    private Set<String> set;

    /**
      * Task 2a - Choosing right filtersize m and number of k hashfunctions
      * @param fileName    FilePath
      * @param p           arbitrary probability
      */
    public Bloom(String fileName, double p){
        Set<String> data = readFile(fileName);
        int n = data.size();
        this.m = (int) (( n * Math.log(p) ) / ( Math.log(2) ));
        this.k = (int) ((m/n) * Math.log(2));
        this.filter = buildFilter(n, data);
    }

      /* 
      * Task 2b - Implement hashfunctions (murmur3_128)
      */
    private int[] buildFilter(int size, Set<String> set){
        int[] flt = new int[size];

        for( String word : set){
            flt[...hashSomethingSomething(word)] = 1;
        }
        return flt;
    }
      
      /* 
      * Task 2c - Read file words.txt
      */
      private Set<String> readFile(String filename){
        Set<String> words = new HashSet<String>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;

            while( (str = in.readLine()) != null){
                words.add(str);
            }
            in.close();

        } catch (IOException e){}

        return words;
      }

}
