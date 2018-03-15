package ru.geekbrains.java2.dz.dz8.TymkivVitaly.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyServer {
    private ServerSocket server;
    private final int PORT = 8189;
    private AuthService authService;
    private ServerBot bot;
    int timeout_auth = 30000;

    public ServerBot getBot() {
        return bot;
    }

    public AuthService getAuthService() {
        return authService;
    }

    private List<ClientHandler> clients; // Список клиентов

    public MyServer() {
        Socket socket = null;

        try {
            server = new ServerSocket(PORT);
            clients = new CopyOnWriteArrayList<>();
            bot = new ServerBot(this);
            authService = new DBAuthService();
            authService.start(this); // Запустить сервис авторизации

            new Thread(() -> {
            for (ClientHandler o: clients){
                System.out.println(System.currentTimeMillis() - o.getAuthTimer());
                if (o.getName().isEmpty()){
                    if (System.currentTimeMillis() - o.getAuthTimer() > timeout_auth) unsubscribe(o);
                }
                if (System.currentTimeMillis() - o.getAuthTimer() > timeout_auth) unsubscribe(o);
            }
            }).start();

            while (true) {
                System.out.println("Сервер ожидает подключения клиента...");
                socket = server.accept(); // Ожидаем подключения клиента
                System.out.println("Клиент подключился: " + socket);
                new ClientHandler(this, socket); // Создаем обработчик клиента
                for (ClientHandler o: clients){
                    System.out.println(System.currentTimeMillis() - o.getAuthTimer());
                    if (o.getName().isEmpty()){
                        if (System.currentTimeMillis() - o.getAuthTimer() > timeout_auth) unsubscribe(o);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            authService.stop(); // Остановить сервис авторизации
            try {
                server.close(); // Закрываем сервер
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Подписать клиента на рассылку
     *
     * @param o обработчик клиента
     */
    public synchronized void subscribe(ClientHandler o) {
        broadcastMsg("Клиент " + o.getName() + " зашел в чат");
        clients.add(o);
        broadcastClientsList();
    }

    /**
     * Отписать клиента от рассылки
     *
     * @param o обработчик клиента
     */
    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
        broadcastMsg("Клиент " + o.getName() + " вышел из чата");
        broadcastClientsList();
    }

    /**
     * Проверка на занятость ника
     *
     * @param nick проверяемый ник
     * @return
     */
    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

    /**
     * Рассылка сообщения всем клиентам
     *
     * @param msg
     */
    public synchronized void broadcastMsg(String msg) {
        String c = getCurrentTime();
        for (ClientHandler o : clients) {
            o.sendMsg(c + " " + msg);
        }
    }

    public synchronized String getCurrentTime() {
        return "" + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
    }

    public synchronized void broadcastClientsList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler o : clients) {
            sb.append(o.getName() + " ");
        }
        String list = sb.toString();
        for (ClientHandler o : clients) {
            o.sendMsg(list); // /clients nick1 nick2 nick3
        }
    }

    public synchronized void sendPersonalMsg(ClientHandler src, String dest, String msg) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(dest)) {
                o.sendMsg(getCurrentTime() + " от " + src.getName() + ": " + msg);
                src.sendMsg(getCurrentTime() + " клиенту " + dest + ": " + msg);
                return;
            }
        }
        src.sendMsg("Клиент " + dest + " отсутствует в чат-комнате");
    }

    public synchronized void startBot() {
        bot.start();
    }
}
