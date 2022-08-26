package homework5;

public class Main {

    public static void main(String[] args) {
        final int SIZE = 10_000_000;
        final int HALF_SIZE = SIZE / 2;
        float[] arrayForWorkingWithoutMultithreading = createAndFillArray(SIZE);
        float[] arrayForWorkingWithMultithreading = createAndFillArray(SIZE);
        workingTimeWithoutMultithreading(arrayForWorkingWithoutMultithreading);
        workingTimeWithMultithreading(arrayForWorkingWithMultithreading, HALF_SIZE);
    }

    private static float[] createAndFillArray(int size) {
        float[] numbersArray = new float[size];
        for (int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] = 1f;
        }
        return numbersArray;
    }

    private static void countingArrayValuesByFormula(float[] arrayNumbers) {
        for (int i = 0; i < arrayNumbers.length; i++) {
            arrayNumbers[i] = (float) (arrayNumbers[i] *
                    Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) *
                    Math.cos(0.4f + i / 2f));
        }
    }

    private static void workingTimeWithoutMultithreading(float[] numbersArray) {
        long currentTime = System.currentTimeMillis();
        countingArrayValuesByFormula(numbersArray);
        long completionTime = System.currentTimeMillis();
        float deltaTime = completionTime - currentTime;
        System.out.println("Running time of the program in single-threaded mode: " + deltaTime / 1000 + " sec");
    }

    private static void workingTimeWithMultithreading(float[] array, int halfSize) {
        float[] firstArrayForWorkingWithThread = new float[halfSize];
        float[] secondArrayForWorkingWithThread = new float[halfSize];
        long currentTime = System.currentTimeMillis();

        System.arraycopy(array, 0, firstArrayForWorkingWithThread, 0, halfSize);
        System.arraycopy(array, halfSize, secondArrayForWorkingWithThread, 0, halfSize);

        MyThread threadFirst = new MyThread(firstArrayForWorkingWithThread, 0);
        MyThread threadSecond = new MyThread(secondArrayForWorkingWithThread, halfSize);

        try {
            threadFirst.join();
            threadSecond.join();
            System.arraycopy(firstArrayForWorkingWithThread, 0, array, 0, halfSize);
            System.arraycopy(secondArrayForWorkingWithThread, 0, array, halfSize, halfSize);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long completionTime = System.currentTimeMillis();
        float deltaTime = completionTime - currentTime;
        System.out.println("Program running time in multithreading: " + deltaTime / 1000 + " sec");
    }


}
