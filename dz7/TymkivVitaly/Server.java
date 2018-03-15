package ru.geekbrains.java2.dz.dz7.TymkivVitaly;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    static final int PORT = 3443;
//    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
        public void sendMessageToAllClientsPrivate(String msg, ClientHandler client) {
        String msgtemp = msg;
        String name, clientName, clientNametmp;
            if (msgtemp.indexOf("Введите ваше имя:")  == -1 && client.getClientName().indexOf("user")!= -1 ) {
            clientName = msgtemp.substring(msgtemp.indexOf(" ") + 1, msgtemp.length()).trim();
            clientName = clientName.substring(0, clientName.indexOf(":")).trim();
            client.setClientName(clientName);
        }
        int j = 0;
        if (msgtemp.indexOf("/w") != -1){
            name = msgtemp.substring(msgtemp.indexOf("/w")+3, msgtemp.length());
            char[] charr = name.toCharArray();
            for (int i = 0; i < charr.length; i++) {
                if (charr[i] == ' ' ){
                    j = i;
                    name = name.substring(0, j);
                    msgtemp = msgtemp.substring(msgtemp.indexOf("/w")+3+j, msgtemp.length());
                    break;
                }
            }
                for (ClientHandler o : clients) {
                    if (o.getClientName().equals(name)) {
                        o.sendMsg(" private msg from " + client.getClientName() + " : "+ msgtemp);
                        client.sendMsg(msg);
                    }
            }
        }else {
            for (ClientHandler o : clients) {
                o.sendMsg(msg);
            }
        }
    }
        public void sendMessageToAllClients(String msg, ClientHandler client) {
            for (ClientHandler o : clients) {
                o.sendMsg(msg);
            }
        }
    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
