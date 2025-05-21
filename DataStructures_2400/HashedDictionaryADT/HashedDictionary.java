//
// Name:    Murillo, Clarissa
// Project: 3
// Due:     3/24/2023
// Course:  cs-2400-03-sp23
//
// Description:
//           This class implements the Dictionary ADT using HashedDictionary. It also
//           provides a method named getCollisionCount that will return the number of collisions.
//
import java.util.Iterator;
import java.util.NoSuchElementException;
public class HashedDictionary<K, V> implements DictionaryInterface<K, V>
{
    // The dictionary:
    private int collisionCount;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5; // Must be prime
    private static final int MAX_CAPACITY = 10000;
    // The hash table:
    private Entry<K, V>[] hashTable;
    private int tableSize; // Must be prime
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    private boolean integrityOK = false;
    protected final Entry<K, V> AVAILABLE = new Entry<> (null, null);

    public HashedDictionary() {
        this(DEFAULT_CAPACITY); // Call next constructor
    } // end default constructor
    public HashedDictionary(int initialCapacity) {
        checkCapacity(initialCapacity);
        numberOfEntries = 0; // Dictionary is empty
        // Set up hash table:
        // Initial size of hash table is same as initialCapacity if it is prime;
        // otherwise increase it until it is prime size
        tableSize = getNextPrime(initialCapacity);
        checkSize(tableSize);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        Entry<K, V>[] temp = (Entry<K, V>[]) new Entry[initialCapacity];
        hashTable = temp;
        collisionCount =0;
        integrityOK = true;
    } // end constructor

    public int getCollisionCount(){
        return collisionCount;
    }
    public V add(K key, V value) {
        checkInitialization();
        int index = getHashIndex(key);
        if(hashTable[index] == null){
            hashTable[index] = new Entry<K,V>(key, value);
            numberOfEntries++;
            return null;
        }else if(hashTable[index].key.equals(key)){
            hashTable[index].value = value;
            return null;
        }
        int compareIndex = linearProbe(index, key);
        //collisionCount++; Does collision Count go here?
        //compare the index before and after probing and see if they are the same
        if(hashTable[compareIndex] == null){
            hashTable[compareIndex] = new Entry<K,V>(key, value);
            numberOfEntries++;
            collisionCount++; //when you insert a new entry, increment the collisionCount
            return null;
        }
        else if(hashTable[compareIndex].key.equals(key)){
            hashTable[compareIndex].value = value;
            return null;
        }
        return null;
    }// end add
    public V remove(K key) { //You don't need to implement the method remove
        throw new UnsupportedOperationException("remove() to be implemented");
    } // end remove
    public V getValue(K key) {
        checkInitialization();
        V result = null;
        int index = getHashIndex(key);
        index = locate(index, key);
        if(index != -1)
            result = hashTable[index].getValue();
        return result;
    }
    // contains is not required for this project, but it made it easier
    // for me to write code for wordFrequency.java
    public boolean contains(K key) {
        checkInitialization();
        boolean result = false;
        int index = getHashIndex(key);
        // Search for key in the chain
        int chain = locate(index, key);
        if (chain >= 0) {
            result = true;
        }
        return result;
    }

    public Iterator<K> getKeyIterator() {
        return new KeyIterator();
    } // end getKeyIterator

    // you don't need this method
    public Iterator<V> getValueIterator() {
        throw new UnsupportedOperationException("getValueIterator to be implemented");
    } // end getValueIterator
    public boolean isEmpty()
    {
        throw new UnsupportedOperationException("isEmpty to be implemented");
    } // end isEmpty

    public int getSize() {
        return numberOfEntries;
    } // end getSize
    public final void clear() {
        throw new UnsupportedOperationException("clear to be implemented");
    } // end clear

    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % hashTable.length;
        if (hashIndex < 0) {
            hashIndex = hashIndex + hashTable.length;
        } // end if
        return hashIndex;
    } // end getHashIndex

    private int linearProbe(int index, K key) {
        boolean found = false;
        int availableIndex = -1; // Index of first location in removed state
        while (!found && (hashTable[index] != null)) {
            if (hashTable[index] !=AVAILABLE) {
                if (key.equals(hashTable[index].getKey()))
                    found = true; // Key found
                else // Follow probe sequence
                    index = (index + 1) % hashTable.length; // Linear probing
            }
            else // Skip entries that were removed
            {
                // Save index of first element from which an entry had been removed
                if (availableIndex == -1)
                    availableIndex = index;
                index = (index + 1) % hashTable.length; // Linear probing
            } // end if
        } // end while
        // Assertion: Either key or null is found at hashTable[index]
        if (found || (availableIndex == -1))
            return index; // Index of either key or null
        else
            return availableIndex; // Index of an available element
    } // end linearProbe

    //
    private int getNextPrime(int anInteger) {
        // see whether an integer is even
        // if it is, add 1 to make it odd since it cannot be prime
        if (anInteger % 2 == 0) {
            anInteger++;
        }
        // while loop tests odd integers
        while (!isPrime(anInteger)) {
            anInteger = anInteger + 2;
        }
        // don't forget to return the int
        return anInteger;
    } // end getNextPrime

    // isPrime tests whether an integer is prime or not
    // Note: 2 and 3 are prime but 1 and even integers are not
    private boolean isPrime(int anInteger) {
        boolean prime;
        boolean done = false;
        // 1 and even numbers are not prime, so you return false
        if ((anInteger == 1) || (anInteger % 2 == 0)) {
            prime = false;
        }
        // 2 and 3 are prime so return true since it is prime
        else if ((anInteger == 2) || (anInteger == 3)) {
            prime = true;
        }
        // else, an odd integer 5 or greater is prime if it is not divisible by
        // every odd integer up to its square root
        else
        {
            // do I need this assert statement?
            assert (anInteger % 2 != 0) && (anInteger >= 5);
            prime = true;
            for (int divisible = 3; !done && (divisible * divisible <= anInteger); divisible = divisible + 2)
            {
                if (anInteger % divisible == 0) {
                    prime = false;
                    done = true;
                }
            }
        }
        return prime;
    } // end isPrime

    private int locate(int index, K key) {
        int chain = index;
        boolean found = false;
        while (!found && (hashTable[chain] != null)) {
            if (key.toString().equalsIgnoreCase(hashTable[chain].getKey().toString())) { //ignore upper/lower case
                found = true;
            }
            else {
                chain = (chain + 1) % hashTable.length; // Linear probing
            }
        }
        if (!found) {
            chain = -1;
        }
        return chain;
    } // end locate

    // checkInitialization Throws an exception if this object is not initialized.
    private void checkInitialization() {
        if (!integrityOK)
            throw new SecurityException("HashedDictionary object is not initialized properly.");
    } // end checkInitialization

    // Ensures that the capacity is not too small or too large.
    private void checkCapacity(int capacity)
    {
        if (capacity < DEFAULT_CAPACITY)
            capacity = DEFAULT_CAPACITY;
        else if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a dictionary " + "whose capacity is larger than " + MAX_CAPACITY);
    } // end checkCapacity

    // checkSize throws an exception if the hash table becomes too large.
    private void checkSize(int size) {
        if (tableSize > MAX_SIZE)
            throw new IllegalStateException("Dictionary has become too large.");
    } // end checkSize

    private class KeyIterator implements Iterator<K> {
        private int currentIndex; // Current position in hash table
        private int numberLeft; // Number of entries left in iteration
        private KeyIterator() {
            currentIndex = 0;
            numberLeft = numberOfEntries;
        } // end default constructor

        public boolean hasNext() {
            return numberLeft > 0;
        } // end hasNext
        public K next() {
            K result = null;
            if (hasNext()) {
                // Skip table locations that do not contain a current entry
                while ((hashTable[currentIndex] == null) || hashTable[currentIndex] ==AVAILABLE) {
                    currentIndex++;
                } // end while
                result = hashTable[currentIndex].getKey();
                numberLeft--;
                currentIndex++;
            } // end if
            else
                throw new NoSuchElementException();
            return result;
        } // end next
        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end KeyIterator

    protected final class Entry<K, V> {
        private K key;
        private V value;
        private Entry(K searchKey, V dataValue) {
            key = searchKey;
            value = dataValue;
        } // end constructor
        private K getKey() {
            return key;
        } // end getKey
        private V getValue() {
            return value;
        } // end getValue
    } // end Entry
} // end HashedDictionary