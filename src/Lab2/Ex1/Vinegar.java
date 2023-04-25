package Lab2.Ex1;

import java.util.HashSet;


public class Vinegar {


    public static void main(String[] args) {

        HashSet<Thread> threads = new HashSet<>();

        for (int i = 0; i < 20; i++) {

            threads.add(new C());

            threads.add(new H());

            threads.add(new H());

            threads.add(new O());

        }

        // run all threads in background


        // after all of them are started, wait each of them to finish for maximum 2_000 ms


        // for each thread, terminate it if it is not finished

        System.out.println("Possible deadlock!");

        System.out.println("Process finished.");


    }


    static class C {


        public void execute() throws InterruptedException {

            // at most 2 atoms should print this in parallel

            System.out.println("C here.");

            // after all atoms are present, they should start with the bonding process together

            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process

            System.out.println("C done.");

            // only one atom should print the next line, representing that the molecule is created

            System.out.println("Molecule created.");

        }

    }


    static class H {


        public void execute() throws InterruptedException {

            // at most 4 atoms should print this in parallel

            System.out.println("H here.");

            // after all atoms are present, they should start with the bonding process together

            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process

            System.out.println("H done.");

            // only one atom should print the next line, representing that the molecule is created

            System.out.println("Molecule created.");

        }

    }


    static class O {


        public void execute() throws InterruptedException {

            // at most 2 atoms should print this in parallel

            System.out.println("O here.");

            // after all atoms are present, they should start with the bonding process together

            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process

            System.out.println("O done.");

            // only one atom should print the next line, representing that the molecule is created

            System.out.println("Molecule created.");

        }

    }


}


