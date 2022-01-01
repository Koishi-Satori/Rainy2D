package rainy2D.util;

public class SimpleArray<E> {

    Object[] objects;
    boolean[] isFull;

    int size;

    public SimpleArray(int size) {

        this.size = size;

        this.objects = new Object[size];
        this.isFull = new boolean[size];

    }

    public E get(int index) {

        return (E) objects[index];

    }

    public void set(E e, int index) {

        this.objects[index] = e;
        this.isFull[index] = true;

    }

    public void add(E e) {

        int index = 0;

        for(; index < this.size; ++index) {
            if(!this.isFull[index]) {
                this.objects[index] = e;
                break;
            }
        }

    }

}
