package org.example;

public class ScoreServiceImpl implements ScoreService {
    public ScoreServiceImpl() {}

    @Override
    public double score(Person person) {
        System.out.println("Hello! Get Score: " + ((double) person.name.length() + person.age));
        person.Hello();
        return (double) person.name.length() + person.age;
    }
}
