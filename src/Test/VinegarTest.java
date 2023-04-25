package Test;

import java.util.HashSet;

import java.util.concurrent.Semaphore;


public class VinegarTest {
    static Semaphore Carbon = new Semaphore(2);
    static Semaphore Hydrogen = new Semaphore(4);
    static Semaphore Oxygen = new Semaphore(2);
    static Semaphore HydrogenHere = new Semaphore(0);
    static Semaphore OxygenHere = new Semaphore(0);
    static int c = 0;
    static Semaphore CanBond = new Semaphore(0);
    static Semaphore HydrogenDone = new Semaphore(0);
    static Semaphore OxygenDone = new Semaphore(0);
    static Semaphore GoNext = new Semaphore(8);
    static Semaphore CarbonCountLock = new Semaphore(1);


    public static void main(String[] args) {

        HashSet<Thread> threads = new HashSet<>();

        for (int i = 0; i < 20; i++) {

            threads.add(new C());

            threads.add(new H());

            threads.add(new H());

            threads.add(new O());

        }

        // run all threads in background
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Possible deadlock!");
                Thread.currentThread().interrupt();
            }
        }

        // after all of them are started, wait each of them to finish for maximum 2_000 ms


        // for each thread, terminate it if it is not finished


        System.out.println("Process finished.");


    }


    static class C extends Thread {
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        public void execute() throws InterruptedException {

            // at most 2 atoms should print this in parallel
            Carbon.acquire();
            GoNext.acquire();

            System.out.println("C here.");
            CarbonCountLock.acquire();
            c++;
            if (c == 2) {

                OxygenHere.acquire(2);
                HydrogenHere.acquire(4);
                CanBond.release(8);
            }
            CarbonCountLock.release();
            CanBond.acquire();

            // after all atoms are present, they should start with the bonding process together

            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process

            System.out.println("C done.");
            CarbonCountLock.acquire();

            if (c == 2) {
                c = 0;
                OxygenDone.acquire(2);
                HydrogenDone.acquire(4);
                System.out.println("Molecule created.");
                GoNext.release(8);

            }
            CarbonCountLock.release();
            Carbon.release();

            // only one atom should print the next line, representing that the molecule is created


        }

    }


    static class H extends Thread {
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        public void execute() throws InterruptedException {

            // at most 4 atoms should print this in parallel
            Hydrogen.acquire();
            GoNext.acquire();
            System.out.println("H here.");
            HydrogenHere.release();
            // after all atoms are present, they should start with the bonding process together
            CanBond.acquire();
            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process

            System.out.println("H done.");
            HydrogenDone.release();
            Hydrogen.release();
            // only one atom should print the next line, representing that the molecule is created


        }

    }


    static class O extends Thread {

        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void execute() throws InterruptedException {

            // at most 2 atoms should print this in parallel
            Oxygen.acquire();
            GoNext.acquire();

            System.out.println("O here.");
            OxygenHere.release();
            CanBond.acquire();

            // after all atoms are present, they should start with the bonding process together

            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process

            System.out.println("O done.");
            OxygenDone.release();
            Oxygen.release();
            // only one atom should print the next line, representing that the molecule is created


        }

    }


}
