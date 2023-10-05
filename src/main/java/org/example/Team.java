package org.example;

import java.util.ArrayList;
import java.util.List;


public class Team {
    private final List<Person> people;

    public Team() {
        people = new ArrayList<>();
    }

    public Team(List<Person> people) {
        this.people = people;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public double calculateTeamRate() {
        return people.stream().mapToInt(Person::rate).average().orElse(0);
    }
}

