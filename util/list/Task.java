package rainy2D.util.list;

public class Task extends Thread {

    public void set(Task task) {

        new Thread(task).start();

    }

}
