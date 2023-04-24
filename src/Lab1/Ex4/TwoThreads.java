package Lab1.Ex4;

public class TwoThreads {
    public static class ThreadAB implements Runnable{
        private String str1;
        private String str2;

        public ThreadAB(String str1, String str2) {
            this.str1 = str1;
            this.str2 = str2;
        }

        @Override
        public void run() {
            System.out.println(str1);
            System.out.println(str2);
        }
    }

    public static void main(String[] args) {
        Runnable t1 = new ThreadAB("a", "b");
        Runnable t2 = new ThreadAB("1", "2");
        new Thread(t1).start();
        new Thread(t2).start();

    }
}