package ru.geekbrains.java2.dz.dz8.TymkivVitaly.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private long authTimer;
    private long time_out = 1000;

    public long getAuthTimer() {
        return authTimer;
    }
    public void setAuthTimer(long authTimer) {
        this.authTimer = authTimer;
    }

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.name = "";
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            authTimer = System.currentTimeMillis();
            new Thread(() -> {
                try {
                    while (true) { // Запускаем цикл авторизации
                        String str = in.readUTF();
                        if (str.startsWith("/auth ")) { // Ожидаем от клиента сообщения вида "/auth login1 pass1"
                            String[] s = str.split("\\s"); // Разбиваем сообщения по пробелам
                            String newNick = myServer.getAuthService().getNickByLoginPass(s[1], s[2]); // Запрашиваем у сервиса авторизации ник по логину паролю
                            if (newNick != null) { // если пришел не пустой ник из сервиса авторизации
                                if (!myServer.isNickBusy(newNick)) { // проверяем не занят ли он
                                    name = newNick; // присваиваем обработчику клиента ник
                                    sendMsg("/authok " + newNick); // отсылаем сообщение клиенту о том что он удачно авторизован
                                    myServer.subscribe(this); // подписываем его на рассылку
                                    break; // выходим из цикла авторизации
                                } else sendMsg("Эта учетная запись уже кем-то используется");
                            } else sendMsg("Неверные логин/пароль");
                        } else sendMsg("Необходимо для начала авторизоваться");
                    }
                    while (true) { // заходим в цикл общения с клиентом
                        String str = in.readUTF(); // читаем сообщения от клиента
                        System.out.println(name + ": " + str); // печатаем в консоль
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) break;
                            if (str.startsWith("/w ")) { // /w nick4 aksjdh vakhsdv ekwjhewk
                                String[] parts = str.split("\\s", 3);
                                myServer.sendPersonalMsg(this, parts[1], parts[2]);
                            }
                            if (str.startsWith("/answer ")) {
                                myServer.getBot().tryToAnswer(this, str.split("\\s")[1]);
                            }
                            if (str.equals("/vik")) {
                                myServer.getBot().start();
                            }
                        } else {
                            myServer.broadcastMsg(name + ": " + str); // и рассылаем всем клиентам
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    myServer.unsubscribe(this); // если завершили работу с клиентом, отписываем его от рассылки
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
