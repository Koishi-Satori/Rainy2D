package rainy2D.util;

public class Array<E> {

    public Object[] objects;
    private int size;

    private static int DEFAULT_CAPACITY = 50;

    public static Array EMPTY = new Array();

    public Array(int capacity){

        objects = new Object[capacity];

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

            objects = newObjects;

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

    /**
     * @return 数组可存长度，批量添加使用
     */
    public int length() {

        return objects.length;

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

        checkRangeNeedIncrease(-1, null);
        objects[size++] = obj;

    }

    public void set(int index, Object obj) {

        if(checkIndex(index)) {
            if(objects[index] == null) {
                size++;
            }
            objects[index] = obj;
        }

    }

    public E get(int index){

        if(checkIndex(index)) {
            if(objects[index] != null) {
                return (E) objects[index];
            }
        }

        return null;

    }

    public void clear(){

        objects = new Object[DEFAULT_CAPACITY];
        size = 0;

    }

    public void remove(int index){

        if(checkIndex(index)) {
            if(index == size){
                objects[index] = null;
            }
            else {
                Array.arrayCopy(objects, objects, index + 1, index, size - index);
            }

            size--;
        }

    }

    public void remove(E obj){

        remove(indexOf(obj));

    }

    public boolean contains(E obj) {

        return indexOf(obj) >= 0;

    }

}
