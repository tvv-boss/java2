package ru.geekbrains.java2.dz.dz8.TymkivVitaly.server;

import java.util.ArrayList;
import java.util.Random;

public class ServerBot {
    public class Question {
        private String question;
        private String answer;

        public Question(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    private ArrayList<Question> list;
    private ArrayList<ClientHandler> alreadyAnswered;
    private MyServer server;
    private boolean active;
    private int questionNumber;
    private Random random;

    public ServerBot(MyServer server) {
        this.server = server;
        list = new ArrayList<>();
        list.add(new Question("Какого цвета трава?", "Зеленая"));
        random = new Random();
        alreadyAnswered = new ArrayList<>();
    }

    public void start() {
        if (!active) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int time = 0;
                    while (active) {
                        try {
                            Thread.sleep(1000);
                            time++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (time == 10) {
                            stop();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.start();
            active = true;
            questionNumber = random.nextInt(list.size());
            alreadyAnswered.clear();
            server.broadcastMsg("Викторина: " + list.get(questionNumber).question);
        }
    }

    public void stop() {
        if (active) {
            active = false;
            server.broadcastMsg("Викторина: никто не смог ответить на вопрос");
            server.broadcastMsg("Викторина: правильный ответ - " + list.get(questionNumber).answer);
        }
    }

    public void tryToAnswer(ClientHandler o, String answer) {
        if (active) {
            if (!alreadyAnswered.contains(o)) {
                alreadyAnswered.add(o);
                if (answer.equals(list.get(questionNumber).answer)) {
                    server.broadcastMsg("Викторина: " + o.getName() + " правильно ответил на вопрос");
                    active = false;
                } else {
                    server.broadcastMsg("Викторина: " + o.getName() + " ошибся");
                }
            } else {
                o.sendMsg("Викторина: Вы уже пробовали ответить на вопрос...");
            }
        }
    }
}
