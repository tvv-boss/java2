package ru.geekbrains.java2.dz.dz8.TymkivVitaly.server;

/**
 * Описание того, что должен уметь делать сервис авторизации
 */
public interface AuthService {
    void start(MyServer server);
    String getNickByLoginPass(String login, String pass);
    void stop();
}
