package id206214280_id316650399;

import java.io.Serializable;
import java.util.Arrays;
import java.lang.Object;
import java.util.Iterator;


public class Set<T> implements Serializable {

//    A collection that contains no duplicate elements. More formally, sets contain no pair of elements e1 and e2 such that e1.equals(e2), and at most one null element. As implied by its name, this interface models the mathematical set abstraction.
//    The Set interface places additional stipulations, beyond those inherited from the Collection interface, on the contracts of all constructors and on the contracts of the add, equals and hashCode methods. Declarations for other inherited methods are also included here for convenience. (The specifications accompanying these declarations have been tailored to the Set interface, but they do not contain any additional stipulations.)

    private T[] genericArray;
    private int currentSize;

    public Set() {
        genericArray = (T[]) new Object[currentSize];
    }

    public void removeAll(){
        for(int i=0;i<genericArray.length;i++){
            remove(genericArray[i]);
            currentSize--;
        }
    }


    public boolean remove(T e) {
        if (contains(e)) {
            for (int i = 0; i < currentSize; i++) {
                if (genericArray[i].equals(e)) {
                    //check if the object is in last place
                    if (i == currentSize - 1) {
                        this.genericArray = Arrays.copyOfRange(genericArray, 0, i);
                        currentSize--;
                        return true;
                    }
                    //if object is in the start of array
                    if (i == 0) {
                        this.genericArray = Arrays.copyOfRange(genericArray, 1, currentSize);
                        currentSize--;
                        return true;
                    }
                    //if object is in the middle
                    if (0 < i && i < currentSize - 1) {
                        T[] newGenericArray = (T[]) new Object[currentSize - 1];
                        T[] copyArray1 = Arrays.copyOfRange(genericArray, 0, i);
                        T[] copyArray2 = Arrays.copyOfRange(genericArray, i + 1, currentSize);
                        int k = 0;
                        while (k < copyArray1.length) {
                            newGenericArray[k] = copyArray1[k];
                            k++;
                        }
                        int t = 0;
                        while (t < copyArray2.length) {
                            newGenericArray[k] = copyArray2[t];
                            k++;
                            t++;
                        }
                        currentSize--;
                        this.genericArray = newGenericArray;
                        return true;


                    }
                }
            }
        }
        return false;
    }

    public Object[] toArray() {
        return Arrays.copyOf(genericArray, currentSize);
    }


    public boolean add(T e) {
        if (contains(e)) {
            return false;
        }
        genericArray = Arrays.copyOf(genericArray, ++currentSize);
        genericArray[currentSize - 1] = e;
        return true;
    }

    public int size() {
        return currentSize;
    }


    public boolean contains(Object e) {
        if (e instanceof Object) {
            for (int i = 0; i < currentSize; i++) {
                if (genericArray[i].equals(e)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Object)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("[");
        for (int i = 0; i < currentSize; i++) {
            if (i == currentSize - 1) {
                str.append(genericArray[i]);
            } else {
                str.append(genericArray[i] + ",");
            }
        }
        str.append("]");
        return str.toString();
    }
}
