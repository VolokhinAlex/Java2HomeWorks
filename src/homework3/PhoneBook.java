package homework3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneBook {

    private HashMap<String, ArrayList<Person>> phoneBook = new HashMap<>();
    private ArrayList<Person> personInfo;

    /**
     * The method adds items to the phone book.
     * @param lastname - lastname.
     * @param phone    - number.
     * @param email    - email.
     */

    public void fillPhoneBook(String lastname, String phone, String email) {
        if (!phoneBook.containsKey(lastname)) {
            personInfo = new ArrayList<>();
        }
        personInfo.add(new Person(phone, email));
        phoneBook.put(lastname, personInfo);
    }

    /**
     * The method prints all the contacts of the phone book.
     */

    public void printPhoneBook() {
        System.out.println("All contacts of the phone book");
        for (Map.Entry<String, ArrayList<Person>> entry : phoneBook.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    /**
     * A method for searching for a number by lastname.
     * @param lastname - lastname.
     */

    public void searchPhoneByLastName(String lastname) {
        System.out.println("List of all numbers with the lastname: " + lastname);
        for (Map.Entry<String, ArrayList<Person>> entry : phoneBook.entrySet()) {
            if (lastname.equals(entry.getKey())) {
                for (Person phones : entry.getValue()) {
                    System.out.println(phones.getPhone());
                }
            }
        }
    }

    /**
     * A method for searching for mail by lastname.
     * @param lastname - lastname
     */

    public void searchEmailByLastName(String lastname) {
        System.out.println("List of all emails with the lastname: " + lastname);
        for (Map.Entry<String, ArrayList<Person>> entry : phoneBook.entrySet()) {
            if (lastname.equals(entry.getKey())) {
                for (Person emails : entry.getValue()) {
                    System.out.println(emails.getEmail());
                }
            }
        }
    }
}
