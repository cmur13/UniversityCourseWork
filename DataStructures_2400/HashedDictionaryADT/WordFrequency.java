//
// Name:    Murillo, Clarissa
// Project: 3
// Due:     3/24/2023
// Course:  cs-2400-03-sp23
//
// Description:
//           WordFrequency uses the Dictionary ADT to study the number of collisions that
//           occur with different hash table lengths (1361, 1637, and 2011)
//
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class WordFrequency {
    public static void main(String[] args) {
        // create three different dictionaries with the 3 different hash table lengths
        HashedDictionary<String, Integer> dict1 = new HashedDictionary<>(1361);
        HashedDictionary<String, Integer> dict2 = new HashedDictionary<>(1637);
        HashedDictionary<String, Integer> dict3 = new HashedDictionary<>(2011);
        // Required IO statements
        System.out.println("Word Frequency by C. Murillo");
        System.out.println("Count Word");
        System.out.println("----- --------------------");
        // read the and process the usconstitution.txt
        try {
            Scanner sc = new Scanner(new File("usconstitution.txt"));
            while (sc.hasNext()) {
                String word = sc.next().toLowerCase();
                // add the word to each of the dictionaries
                // do I need to add for every hashTable length?
                // Answer: Yes, so you can implement the add method for every dictionary to get the collision count
                dict1.add(word, dict1.contains(word) ? dict1.getValue(word) + 1 : 1);
                dict2.add(word, dict2.contains(word) ? dict2.getValue(word) + 1 : 1);
                dict3.add(word, dict3.contains(word) ? dict3.getValue(word) + 1 : 1);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        //you only need to iterate one dictionary once
        Iterator<String> keyIterator = dict1.getKeyIterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            System.out.printf("%-6d%-24s\n", dict1.getValue(key), key);
        }
        System.out.println();

        // print out the number of unique words and collision counts
        // statements for required IO
        System.out.println("Unique Words = " + dict1.getSize()+ "\n");
        System.out.println("Table");
        System.out.println("Length  Collision");
        System.out.println("  1361  " +  dict1.getCollisionCount());
        System.out.println("  1637  " +  dict2.getCollisionCount());
        System.out.println("  2011  " +  dict3.getCollisionCount());

    }
}
