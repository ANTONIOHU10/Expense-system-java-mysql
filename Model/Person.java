package Model;

import java.util.Objects;

public class Person {
    private final String name;
    private final String surname;
    private final String id;

    public Person(String name,String surname, String id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    /*
    @Override
    public String toString() {
        return name;
    }
     */
}
