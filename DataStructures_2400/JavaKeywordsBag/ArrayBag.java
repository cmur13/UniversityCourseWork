//
//  Name:     Murillo, Clarissa
//  Homework: 1
//  Due:      2/9/23
//  Course:   CS 2400.03
//
//  Description:
//         A class of bags whose entries are stored in a fixed-size array.
//

public final class ArrayBag<T> implements BagInterface<T> {
    private final T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY= 100; //this won't work for the code, you can change it to 100

    /** Creates an empty bag whose initial capacity is 100. */
    public ArrayBag(){
        this(DEFAULT_CAPACITY);
    } //end default constructor

    /** Creates an empty bag having a given initial capacity.
        @param desiredCapacity The integer capacity desired. */
    public ArrayBag(int desiredCapacity){
        // The cast is safe because the new array contains null entries.
        @SuppressWarnings("unchecked")
        T[] tempBag= (T[])new Object[desiredCapacity]; //Unchecked cast
        bag = tempBag;
        numberOfEntries = 0;
    } //end constructor

    @Override
    public int getCurrentSize(){

        return numberOfEntries;
    }
    //true if bag is empty otherwise false
    @Override
    public boolean isEmpty(){

        return numberOfEntries==0;
    }

    /** Adds a new entry to this bag.
        @param newEntry The object to be added as a new entry.
        @return True if the addition is successful, or false if not. */
    @Override
    public boolean add(T newEntry){
        boolean result =true;
        if (isArrayFull()){
            result=false;
        }
        else{
            bag[numberOfEntries]=newEntry;
            numberOfEntries++;
        }
        return result;
    } //end add

    public T remove(){
        if(!isEmpty()){
            return bag[numberOfEntries--];
        }
        return null;
    }
    @Override
    public boolean remove(T anEntry){
        for (int i=0; i<numberOfEntries; i++){
            if(bag[i].equals(anEntry)){
                for (int j=i;j<numberOfEntries-1;j++)
                    bag[j]=bag[j+1];
                numberOfEntries --;
                return true;
            }
        }
        return false;
    }
    @Override
    public void clear(){
        numberOfEntries=0;
    }
    @Override
    public int getFrequencyOf(T anEntry){
        int frequency=0;
        for(int i=0;i<numberOfEntries;i++){
            if(bag[i].equals(anEntry)) {
                frequency++;
            }
        }
        return frequency;
    }

    public boolean contains (T anEntry){
        for (int i = 0; i<numberOfEntries;i++){
            if(bag[i].equals(anEntry)){
                return true;
            }
        }
        return false;
    }

    /** Retrieves all entries that are in this bag
        @return A newly allocated array of all the entries in the bag. */
    public T[] toArray(){
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object [numberOfEntries]; //Unchecked cast
        for (int i = 0; i < numberOfEntries; i ++){
            result[i] = bag[i];
        } //end of the for loop
        return result;
    } //end toArray

    // Returns true if the ArrayBag is full, or false if not.
    private boolean isArrayFull(){
        return numberOfEntries >= bag.length;
    } //end isArrayFull
} //end ArrayBag
