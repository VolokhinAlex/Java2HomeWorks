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

    public static void main(String[] args) {
        String strMatrix = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
        String[][] stringToArray;

        try {
            stringToArray = convertStringToArray(strMatrix);
            System.out.println("The sum of the array numbers divided by 2 = " + operationWithArray(stringToArray));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод преобразует переданную строку в двумерный массив значений.
     * @param strForConvert - строка для преобразования в двумерный массив
     * @return - заполненный двумерный массив.
     * @throws ArrayIndexOutOfBoundsException - исключение при выходе за пределы массива, в этом случае размер массива должен быть 4x4
     */

    private static String[][] convertStringToArray(String strForConvert) throws ArrayIndexOutOfBoundsException {
        String[] additionalArrayForConvert = strForConvert.split("\n");
        String[][] stringToArray = new String[additionalArrayForConvert.length][additionalArrayForConvert.length];
        for (int i = 0; i < additionalArrayForConvert.length; i++) {
            String[] additionalArray = additionalArrayForConvert[i].split(" ");
            for (int j = 0; j < additionalArray.length; j++) {
                if (additionalArray.length != 4) {
                    throw new ArrayIndexOutOfBoundsException("The size of the matrix should be 4x4!");
                }
                stringToArray[i][j] = additionalArray[j];
                System.out.print(stringToArray[i][j] + " ");
            }
            System.out.println();
        }
        return stringToArray;
    }

    /**
     * Метод суммирует все числа двумерного массива и делит их на 2, после чего возвращает результат деления.
     * @param arrayString - двумерный массив заполненный строкой.
     * @return - сумма чисел массива деленная на 2
     * @throws NumberFormatException - ошибка преобразования строки в число.
     */

    private static int operationWithArray(String[][] arrayString) throws NumberFormatException {
        int sumArray = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (isNumeric(arrayString[i][j])) {
                    sumArray += Integer.parseInt(arrayString[i][j]);
                } else {
                    throw new NumberFormatException("A string cannot be converted to a number.");
                }
            }
        }
        return sumArray / 2;
    }

    /**
     * Метод преобразует все элементы массива в числа int и возвращает результат, возможно это или нет.
     * @param strMatrix - строка для преобразования
     * @return - True: если получилось преобразовать, иначе False
     * @throws java.lang.NumberFormatException - ошибка преобразования строки в число.
     */

    private static boolean isNumeric(String strMatrix) throws java.lang.NumberFormatException {
        try {
            Integer.parseInt(strMatrix);
            return true;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }


}
