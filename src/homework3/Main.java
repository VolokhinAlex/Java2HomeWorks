package homework3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {

    /**
     * Дз:
     * 1. Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся); +
     * - Найти список слов, из которых состоит текст (Дубликаты не считать); +
     * - Посчитать сколько раз встречается каждое слово (использовать HashMap); +
     * 2. Написать простой класс PhoneBook(внутри использовать HashMap); +
     * - В качестве ключа использовать фамилию; +
     * - В каждой записи всего два поля: phone, e-mail; + -
     * - Отдельно метод для поиска номера по фамилии(ввели фамилию, получили ArrayList телефонов), +
     * и отдельно метод для поиска e-mail по фамилии. Следует учесть, что под одной фамилией может быть несколько
     * записей. Итого должно получиться 3 класса Main, PhoneBook, Person.
     */

    public static void main(String[] args) {
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot",
                "cherry", "garlic", "apple", "melon", "banana", "olive", "mango", "mushroom", "nut", "olive", "cherry",
                "avocado", "lemon", "pepper", "apple", "lemon", "potato", "nut", "pepper", "mango", "apricot", "carrot"};


        HashSet<String> wordsSet = new HashSet<>();
        wordsSet.addAll(Arrays.asList(words));
        System.out.printf("Total words = %d, Total words without duplicates = %d\nList of words without duplicates - %s", words.length, wordsSet.size(), wordsSet);

        System.out.println("\n****************  How many times does each word of the list occur ?  **********************");
        HashMap<String, Integer> countWord = new HashMap<>();
        for (String word : words) {
            if (!countWord.containsKey(word)) {
                countWord.put(word, 0);
            }
            countWord.put(word, countWord.get(word) + 1);
        }
        for (Map.Entry<String, Integer> entry : countWord.entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + " time(s), ");
        }

        System.out.println("\n***********************************************");
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.fillPhoneBook("Петров", "+7 (212) 222 55 77", "petrov1@gmail.com");
        phoneBook.fillPhoneBook("Петров", "+7 (290) 245 15 22", "petrov90@gmail.com");
        phoneBook.fillPhoneBook("Иванов", "+7 (999) 576 23 77", "ivanov31@gmail.com");
        phoneBook.fillPhoneBook("Сидоров", "+7 (300) 561 20 09", "sidorov42@gmail.com");
        phoneBook.printPhoneBook();
        System.out.println("***********************************************");

        phoneBook.searchPhoneByLastName("Петров");
        phoneBook.searchEmailByLastName("Иванов");
    }
}
