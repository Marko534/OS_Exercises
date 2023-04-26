package Lab2.Ex2Second;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

class Lab2 {

    static ArrayList<Semaphore> semaphores = new ArrayList<>();
    //TODO: Initialize the semaphores you need
    public static void main(String[] args) throws InterruptedException {

        //STARTING CODE, DON'T MAKE CHANGES
        //-----------------------------------------------------------------------------------------
        String final_text = "Bravo!!! Ja resi zadacata :)";
        int m = 10, n = 100;
        Object[][] data = new Object[m][n];
        Random rand = new Random();
        int k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int random = rand.nextInt(100);
                //% number the biger the number the more spread out they are
                if (random % 15 == 0 & k < final_text.length()) {
                    data[i][j] = final_text.charAt(k);
                    k++;
                } else {
                    data[i][j] = rand.nextInt(100);
                }
            }
        }

        DataMatrix matrix = new DataMatrix(m, n, data);
        StatisticsResource statisticsResource = new StatisticsResource();
        //-----------------------------------------------------------------------------------------

        //ONLY TESTING CODE, SO YOU CAN TAKE A LOOK OF THE OUTPUT YOU NEED TO GET AT THE END
        //YOU CAN COMMENT OR DELETE IT AFTER YOU WRITE THE CODE USING THREADS
        //-----------------------------------------------------------------------------------------
//        Concatenation concatenation = new Concatenation(matrix, statisticsResource);
        StringBuilder mainStrin = new StringBuilder();
        ArrayList<Concatenation> threads = new ArrayList<>();

        for (int i =0;i < matrix.getM()-1; i++){
            semaphores.add(new Semaphore(0));
        }
        semaphores.add(new Semaphore(1));

        for (int i = 0; i < matrix.getM(); i++) {
            threads.add(new Concatenation(matrix, statisticsResource ,i));
            threads.get(i).start();
        }

//        threads.get(matrix.getM()-1).join();
        for (int i = matrix.getM()-1; i >-1; i--) {
            threads.get( i).join();
        }
//        concatenation.concatenate();

        statisticsResource.printString();
        //-----------------------------------------------------------------------------------------

        //TODO: Run the threads from the Concatenation class

        //TODO: Wait 10seconds for all threads to finish

        //TODO: Print the string you get, call function printString()

        //TODO: Check if some thread is still alive, if so kill it and print "Possible deadlock"

    }

    // TODO: Make the Concatenation Class  a Thread Class
    static class Concatenation extends Thread {

        private DataMatrix matrix;
        private StatisticsResource statisticsResource;
        private int line;
        private StringBuilder letters;

        public Concatenation(DataMatrix matrix, StatisticsResource statisticsResource, int line) {
            this.matrix = matrix;
            this.statisticsResource = statisticsResource;
            this.line = line;
            this.letters = new StringBuilder("");
        }

        //        concatenation function implemented on the whole matrix, so you can take a look of the task's logic
        public void concatenate() {
            for (int j = this.matrix.getN()-1; j >-1; j--) {
                if (this.matrix.isString(line, j)) {
                    this.letters.append(this.matrix.getEl(line, j).toString());
                }
            }
        }

        @Override
        public void run() {
            try {
                this.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void execute() throws InterruptedException {
            this.concatenate();
            semaphores.get(this.line).acquire();
            System.out.printf("\n"+ letters + " "+ line);
            this.statisticsResource.concatenateString(letters.toString());
            if(line!=0){
                semaphores.get(this.line-1).release();
            }
            semaphores.get(this.line).release();
        }
    }

    //-------------------------------------------------------------------------
    //YOU ARE NOT CHANGING THE CODE BELOW
    static class DataMatrix {

        private int m, n;
        private Object[][] data;

        public DataMatrix(int m, int n, Object[][] data) {
            this.m = m;
            this.n = n;
            this.data = data;
        }

        public int getM() {
            return m;
        }

        public int getN() {
            return n;
        }

        public Object[][] getData() {
            return data;
        }

        public Object getEl(int i, int j) {
            return data[i][j];
        }

        public Object[] getRow(int pos) {
            return this.data[pos];
        }

        public Object[] getColumn(int pos) {
            Object[] result = new Object[m];
            for (int i = 0; i < m; i++) {
                result[i] = data[i][pos];
            }
            return result;
        }

        public boolean isString(int i, int j) {
            return this.data[i][j] instanceof Character;
        }


    }

    static class StatisticsResource {

        private String concatenatedString;

        public StatisticsResource() {
            this.concatenatedString = "";
        }

        //function for String concatenation
        public void concatenateString(String new_character) {
            concatenatedString += new_character;
        }

        //function for printing the concatenated string
        public void printString() {
            System.out.println("Here is the phrase from the matrix: " + concatenatedString);
        }

    }
}


