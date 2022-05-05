package id206214280_id316650399;

import javax.naming.spi.ObjectFactoryBuilder;
import java.io.Serializable;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Arrays;

public  class Set<T> implements Serializable {

//    A collection that contains no duplicate elements. More formally, sets contain no pair of elements e1 and e2 such that e1.equals(e2), and at most one null element. As implied by its name, this interface models the mathematical set abstraction.
//    The Set interface places additional stipulations, beyond those inherited from the Collection interface, on the contracts of all constructors and on the contracts of the add, equals and hashCode methods. Declarations for other inherited methods are also included here for convenience. (The specifications accompanying these declarations have been tailored to the Set interface, but they do not contain any additional stipulations.)

    private T setObject;
    private T[] genericArray;
    private int currentSize;

    public Set() {
        genericArray = (T[]) new Object[currentSize];
    }

    public boolean remove(T e) {
        if(contains(e)){
            for(int i=0;i<currentSize;i++){
                if(genericArray[i].equals(e)){

                }
            }
        }
        return false;
    }


    public boolean add(T e) {
        if (contains(e)) {
            System.out.println("cant add already in set");
            return false;
        }
        genericArray = Arrays.copyOf(genericArray, ++currentSize);
        genericArray[currentSize - 1] = e;
        return true;
    }

    public int size() {
        return currentSize;
    }
    public T get(int index){
        if(genericArray!=null){
            return genericArray[index];
        }
        return null;
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
