package homework3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneBook {

    private HashMap<String, ArrayList<Contact>> phoneBook = new HashMap<>();
    private ArrayList<Contact> personInfo;

    /**
     * The method adds items to the phone book.
     *
     * @param lastname - lastname - Keys.
     * @param phone    - number - value.
     * @param email    - email - value.
     */

    public void fillPhoneBook(String lastname, String phone, String email) {
        if (!phoneBook.containsKey(lastname)) {
            personInfo = new ArrayList<>();
        }
        if (phoneBook.containsKey(lastname)) {
            personInfo = phoneBook.get(lastname);
        }
        personInfo.add(new Contact(phone, email));
        phoneBook.put(lastname, personInfo);
    }

    /**
     * The method prints all the contacts of the phone book.
     */

    public void printPhoneBook() {
        System.out.println("All contacts of the phone book");
        for (Map.Entry<String, ArrayList<Contact>> entry : phoneBook.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    /**
     * A method for searching for a number by lastname.
     *
     * @param lastname - lastname - Keys.
     */

    public void searchPhoneByLastName(String lastname) {
        System.out.println("List of all numbers with the lastname: " + lastname);
        for (Map.Entry<String, ArrayList<Contact>> entry : phoneBook.entrySet()) {
            if (lastname.equals(entry.getKey())) {
                for (Contact phones : entry.getValue()) {
                    System.out.println(phones.getPhone());
                }
            }
        }
    }

    /**
     * A method for searching for mail by lastname.
     *
     * @param lastname - lastname - Keys.
     */

    public void searchEmailByLastName(String lastname) {
        System.out.println("List of all emails with the lastname: " + lastname);
        for (Map.Entry<String, ArrayList<Contact>> entry : phoneBook.entrySet()) {
            if (lastname.equals(entry.getKey())) {
                for (Contact emails : entry.getValue()) {
                    System.out.println(emails.getEmail());
                }
            }
        }
    }
}
