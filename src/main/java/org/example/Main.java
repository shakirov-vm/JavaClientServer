package org.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        configureServer();

        ScoreService scoreService1 = createScoreClient(8000);
        Person curr = new Person(10, "Petya1");
        double result = scoreService1.score(curr);
        System.out.println("Result on client is for Petya1: " + result);
        result = scoreService1.score(new Person(20, "Petya2"));
        System.out.println("Result on client is for Petya1: " + result);

        ScoreService scoreService2 = createScoreClient(8001);
        result = scoreService2.score(new Person(30, "Petya3"));
        System.out.println("Result on client is for Petya3: " + result);
    }

    private static void configureServer() throws InterruptedException {
        ServerFactory serverFactory = new ServerFactoryImpl();
        serverFactory.listen(8000, new ScoreServiceImpl());
        serverFactory.listen(8001, new ScoreServiceImpl());
    }

    private static ScoreService createScoreClient(int port) {
        ClientFactory factory = new ClientFactoryImpl("127.0.0.1", port);
        return factory.newClient(ScoreService.class);
    }
}