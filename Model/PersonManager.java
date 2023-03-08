package Model;

import java.util.HashMap;
import java.util.Map;

public class PersonManager {
    private Map<String, Person> persons;

    public PersonManager() {
        this.persons = new HashMap<>();
    }

    public void addPerson(Person person) {
        persons.put(person.getName(), person);
    }

    public Person getPerson(String name) {
        return persons.get(name);
    }

    public void removePerson(String name) {
        persons.remove(name);
    }

    public boolean personExists(String name) {
        return persons.containsKey(name);
    }
}
