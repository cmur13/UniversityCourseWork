//
//  Name:     Murillo, Clarissa
//  Homework: 1
//  Due:      2/9/23
//  Course:   CS 2400.03
//
//  Description:
//    implements the interface BagInterface and the class ArrayBag.
//    Then implements the class JavaKeywords that uses the interface and class. Reads the
//    Java keywords from the file and store the keywords in a bag
//

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class JavaKeywords {
    public static void main(String[] args) throws Exception {

        System.out.println("Java Keywords by C. Murillo. \n");

        ArrayBag<String> bag = new ArrayBag<>();
        File keywords = new File("JavaKeywords.txt");
        //try {
        Scanner sc = new Scanner(keywords);
        while (sc.hasNextLine()) {
            bag.add(sc.nextLine());
        }
        sc.close();

        System.out.println(bag.getCurrentSize() + " Java Keywords loaded. \n");
        for (String keyword : args) {
            if (bag.contains(keyword)) {
                System.out.println(keyword + " is a keyword");
            } else {
                System.out.println(keyword + " is not a keyword");
            }
        }

        //below are the test to test the methods in BagInterface
        System.out.println("\n\nTesting the Interface");

        Object[] tempKeywordsBag = bag.toArray();
        //returns all the entries in the bag
        System.out.println("\n" + Arrays.toString(tempKeywordsBag) + "\n");

        //tests the getCurrentSize() method
        System.out.println("The size of the ArrayBag is: " + bag.getCurrentSize());


        //test for getFrequencyOf(), the frequency of the word true is printed using getFrequency
        System.out.println("The word 'true' appears " + bag.getFrequencyOf("true"));

        //tests the add method, returns true if the addition was successful
        System.out.println("The item that was added is " + bag.add("hello"));

        //tests the remove() method, null if successful
        System.out.println("The item that is removed is '" + bag.remove() + "',");

        //the boolean remove() method is used to remove the word "super" from the keywordsBag
        if (bag.remove("super")) {
            System.out.println("'super' was successfully removed.");
        } else {
            System.out.println("Unable to find word.");
        }

        //tests the contains() method
        System.out.println("The word 'while' is in the bag: " +bag.contains("while"));

        //tests the clear method, the bag should be empty
        bag.clear();
        if (bag.isEmpty()) {
            System.out.println("The bag is empty");
        } else {
            System.out.println("There are still " + bag.getCurrentSize());
        }
    }
}