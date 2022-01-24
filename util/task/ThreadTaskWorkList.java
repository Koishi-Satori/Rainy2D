package rainy2D.util.task;

import rainy2D.util.Array;

public class ThreadTaskWorkList {

    public Array<Task> tasks;

    public ThreadTaskWorkList() {

        tasks = new Array<>();
        Worker worker = new Worker();
        worker.start();

    }

    public void submit(Task task) {

        tasks.add(task);

    }

    private class Worker extends Thread {

        Task task;

        public void run() {

            try {

                while(true) {

                    if(tasks.size() > 0) {
                        task = tasks.get(0);
                        tasks.remove(0);
                    }

                    if(task != null) {
                        task.run();
                    }

                    sleep(20);

                }

            }
            catch(InterruptedException e) {}

        }

    }
}
