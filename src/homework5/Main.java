package homework5;

public class Main {

    public static void main(String[] args) {
        final int SIZE = 10_000_000;
        final int HALF_SIZE = SIZE / 2;
        float[] numbersArray = createAndFillArray(SIZE);
        workingTimeWithoutThreads(numbersArray);
        workingTimeWithThreads(numbersArray, HALF_SIZE);
    }

    private static float[] createAndFillArray(int size) {
        float[] numbersArray = new float[size];
        for (int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] = 1f;
        }
        return numbersArray;
    }

    private static float[] countingArrayValuesByFormula(float[] arrayNumbers) {
        for (int i = 0; i < arrayNumbers.length; i++) {
            arrayNumbers[i] = (float) (arrayNumbers[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) *
                    Math.cos(0.4f + i / 2f));
        }
        return arrayNumbers;
    }

    private static void workingTimeWithoutThreads(float[] numbersArray) {
        long currentTime = System.currentTimeMillis();
        countingArrayValuesByFormula(numbersArray);
        long completionTime = System.currentTimeMillis();
        float deltaTime = completionTime - currentTime;
        System.out.println("Working time without Threads: " + deltaTime + " millis");
    }

    private static void workingTimeWithThreads(float[] array, int halfSize) {
        float[] firstArrayForWorkingWithThread = new float[halfSize];
        float[] secondArrayForWorkingWithThread = new float[halfSize];
        long currentTime = System.currentTimeMillis();

        System.arraycopy(array, 0, firstArrayForWorkingWithThread, 0, halfSize);
        System.arraycopy(array, 0, secondArrayForWorkingWithThread, 0, halfSize);

        new Thread(() -> {
            countingArrayValuesByFormula(firstArrayForWorkingWithThread);
            System.arraycopy(firstArrayForWorkingWithThread, 0, array, 0, firstArrayForWorkingWithThread.length);
        }).start();

        new Thread(() -> {
            countingArrayValuesByFormula(secondArrayForWorkingWithThread);
            System.arraycopy(secondArrayForWorkingWithThread, 0, array, 0, secondArrayForWorkingWithThread.length);
        }).start();

        long completionTime = System.currentTimeMillis();
        float deltaTime = completionTime - currentTime;
        System.out.println("Working time with Threads: " + deltaTime + " millis");
    }

}
