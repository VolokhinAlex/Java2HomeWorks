package homework2;

public class Main {

    /**
     * 1. Есть строка вида "10 3 1 2\n2 3 2 2\n5 6 7 1\n 300 3 1 0";
     * Написать метод, на вход которого подается такая строка, метод должен преобразовать строку в двумерный массив типа String[][];
     * 2. Преобразовать все элементы массива в числа типа int, просуммировать, поделить полученную сумму на 2, и вернуть результат.
     * 3. Ваши методы должны бросить исключения в случаях:
     * Если размер матрицы, полученной из строки, не равен 4x4;
     * Если в одной из ячеек полученной матрицы не число;
     * 4. В методе main необходимо вызвать полученные методы, обработать возможные исключения и вывести результат расчета.
     * 5. * Написать собственные классы исключения для каждого из случаев. RuntimeException
     */

    private static String str = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
    private static String[][] stringToArray;

    public static void main(String[] args) {
        try {
            convertStringToArray(str);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("The sum of the array numbers divided by 2 = " + operationWithArray());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private static void convertStringToArray(String str) throws ArrayIndexOutOfBoundsException {

        String[] string = str.split("\n");
        stringToArray = new String[string.length][string.length];
        for (int i = 0; i < string.length; i++) {
            String[] arr = string[i].split(" ");
            for (int j = 0; j < arr.length; j++) {
                if (arr.length != 4) {
                    throw new ArrayIndexOutOfBoundsException("The size of the matrix should be 4x4!");
                }
                stringToArray[i][j] = arr[j];
                System.out.print(stringToArray[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static int operationWithArray() throws NumberFormatException {

        String[] string = str.split("\n");
        int sum = 0;
        int[][] arr = new int[string.length][string.length];
        for (int i = 0; i < arr.length; i++) {
            String[] arr3 = string[i].split(" ");
            for (int j = 0; j < arr3.length; j++) {
                if (!isNumeric(arr3[j])) {
                    throw new NumberFormatException("A string cannot be converted to a number.");
                }
                sum = sum + Integer.parseInt(arr3[j]);
                arr[i][j] = Integer.parseInt(arr3[j]);
            }
        }
        return sum / 2;

    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}
