package rainy2D.util;

public class Array<E> {

    private Object[] objects;
    private int size;
    private int addCount;

    private Object DEFAULT_NONNULL_OBJ;

    private static int DEFAULT_CAPACITY = 50;

    public Array(int capacity){

        this.objects = new Object[capacity];

    }

    public Array(){

        this(DEFAULT_CAPACITY);

    }

    public static void arrayCopy(Object[] oldArray, Object[] newArray, int oldStartPos, int newStartPos, int copyLength) {

        if(newStartPos + copyLength < newArray.length) {
            System.arraycopy(oldArray, oldStartPos, newArray, newStartPos, copyLength);
        }

    }

    public void checkRangeNeedIncrease(int index, Object obj) {

        if(size >= objects.length){

            Object[] newObjects = new Object[size * 2];

            if(index == -1 && obj == null) {
                Array.arrayCopy(objects, newObjects, 0, 0, size);
            }
            else {
                Array.arrayCopy(objects, newObjects, index, index + 1, size - index);
            }

            this.objects = newObjects;

        }

    }

    public boolean checkIndex(int index){

        if(index >= size || index < 0){
            return false;
        }

        return true;

    }

    public int size(){

        return size;

    }

    public int sizeFromZero() {

        return size - 1;

    }

    public int indexOf(Object obj) {

        if (obj == null) {
            for (int i = 0; i < size; i++)
                if (objects[i]==null)
                    return i;
        }
        else {
            for (int i = 0; i < size; i++)
                if (obj.equals(objects[i]))
                    return i;
        }

        return -1;

    }

    public void add(Object obj){

        this.addCount++;
        if(addCount == 1) {
            this.DEFAULT_NONNULL_OBJ = obj;
        }
        this.checkRangeNeedIncrease(-1, null);
        this.objects[size++] = obj;

    }

    public E get(int index){

        if(checkIndex(index)) {
            if(objects[index] != null) {
                return (E) objects[index];
            }
        }

        return (E) DEFAULT_NONNULL_OBJ;

    }

    public void clear(){

        for(int i = 0; i < size; i++){
            this.objects[i] = null;
        }

    }

    public void remove(int index){

        if(checkIndex(index)) {
            if(index == size){
                this.objects[index] = null;
            }
            else {
                Array.arrayCopy(objects, objects, index + 1, index, size - index);
            }

            this.size--;
        }

    }

    public boolean contains(E obj) {

        return indexOf(obj) >= 0;

    }

}