package org.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        configureServer();
        ScoreService scoreService = createScoreClient();

        Person curr = new Person(10, "Petya");
        double result = scoreService.score(curr);
        System.out.println("Result on client is " + result);
    }

    private static void configureServer() throws InterruptedException {
        ServerFactory serverFactory = new ServerFactoryImpl();
        serverFactory.listen(8000, new ScoreServiceImpl());
    }

    private static ScoreService createScoreClient() {
        ClientFactory factory = new ClientFactoryImpl("127.0.0.1", 8000);
        return factory.newClient(ScoreService.class);
    }
}