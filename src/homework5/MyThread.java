package homework5;

public class MyThread extends Thread {
    private float[] numbersArray;
    private int offset;

    MyThread(float[] numbersArray, int offset) {
        this.numbersArray = numbersArray;
        this.offset = offset;
        start();
    }

    @Override
    public void run() {
        for (int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] = (float) (numbersArray[i] *
                    Math.sin(0.2f + (i + offset) / 5f) * Math.cos(0.2f + (i + offset) / 5f) *
                    Math.cos(0.4f + (i + offset) / 2f));
        }
    }
}
