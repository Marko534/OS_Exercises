package Mid1.Ex1;

class ShardResource {
    private int counter;

    public ShardResource() {
        this.counter = 0;
    }

    public void increment() {
        this.counter++;
    }

    public int getCounter() {
        return counter;
    }
}

class CustomRunnable implements Runnable {
    private ShardResource resource;

    public CustomRunnable(ShardResource resource) {
        this.resource = resource;
    }


    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            this.resource.increment();
            System.out.print("R");
        }
    }
}

class CustomThread extends Thread {
    private ShardResource resource;

    public CustomThread(ShardResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            this.resource.increment();
            System.out.print("T");
        }
    }
}

public class ThreadExample{
    public static void main(String[] args) throws InterruptedException {
        ShardResource resource = new ShardResource();
        Thread t1 = new CustomThread(resource); //T
        Thread t2 = new Thread(new CustomRunnable(resource)); //R
        Thread t3 = new CustomThread(resource); //T
        t1.start();
        t2.start();
        t2.join(); // 6
        t3.start();
        resource.increment();
        int finalCounter = resource.getCounter();
        t3.join();
        resource.increment();
        System.out.println();
        System.out.println(finalCounter);
    }
}
