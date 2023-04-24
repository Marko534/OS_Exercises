package Lab1.Ex5;

import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class CountThree {

    public static int NUM_RUNS = 100;
    /**
     * TODO: definirajte gi potrebnite elementi za sinhronizacija
     */
    protected int occurrences = 0;
    private Semaphore semaphore;

    public void init() {
        this.semaphore = new Semaphore(1);
    }

    class Counter extends Thread {
        private int count;
        private int[] data;
        public Counter(int[] data) {
            this.data = data;
            this.count = 0;
        }

        public void count(int[] data) throws InterruptedException {
            for (int i = 0; i < data.length; i++) {
                if (data[i] % 3 == 0) {
                    count++;
                }
            }
            try {
                semaphore.acquire();
                occurrences += count;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }



        @Override
        public void run() {
            try {
                count(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            CountThree environment = new CountThree();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {

        init();

        HashSet<Thread> threads = new HashSet<Thread>();
        Scanner s = new Scanner(System.in);
        int total = s.nextInt();
        int frags = total/NUM_RUNS;
        for (int i = 0; i < NUM_RUNS; i++){
            int[] data = new int[frags];
            for (int j = 0; j<frags;j++){
                data[j] = s.nextInt();
            }
            Counter c = new Counter(data);
            threads.add(c);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println(occurrences);
    }
}