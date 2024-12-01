package org.example;

public class ScoreServiceImpl implements ScoreService {
    public ScoreServiceImpl() {

    }
    @Override
    public double score(Person person) {
        System.out.println("Hello! Get Score:");
        person.Hello();
        return 0f;
    }
}
