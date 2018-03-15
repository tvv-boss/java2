package ru.geekbrains.java2.dz.dz8.TymkivVitaly.server;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private List<User> users;

    public BaseAuthService() {
        users = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            users.add(new User("login" + i, "pass" + i, "nick" + i));
        }
    }

    @Override
    public void start(MyServer server) {
        System.out.println("Сервис авторизации запущен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        return users.stream().filter(user -> user.getLogin().equals(login) && user.getPass() == pass.hashCode())
                .map(user -> user.getNick()).findFirst().get();
//        for (User o : users) {
//            if (o.getLogin().equals(login) && o.getPass() == pass.hashCode()) {
//                return o.getNick();
//            }
//        }
//        return null;
    }

    @Override
    public void stop() {
        System.out.println("Сервис авторизации остановлен");
    }
}
