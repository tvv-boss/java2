package ru.geekbrains.java2.dz.dz6.TymkivVitaly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServCons s = new ServCons();
        s.startservcons();
        s.catchClient();
        new Thread() {
            public void run() {
                while (true) {
                    String txt = null;
                    try {
                        txt = s.in.readLine();
                    } catch (IOException e) {
                        System.out.println(" Client not connect ");
                        try {
                            Thread.sleep(1000);
                            break;
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (txt != null) {
                        try {
                            s.sendMessage(txt);
                        } catch (IOException e) {
                            System.out.println(" Client not connect ");
                            try {
                                Thread.sleep(2000);
                                break;
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    s.writeToConsole();
                } catch (IOException e) {
                    System.out.println(" Client not connect ");
//                    e.printStackTrace();
                }
            }
        }.start();
    }
}

class ServCons {
    BufferedReader in = null;
    PrintWriter out = null;
    ServerSocket serverSocket = null;
    Socket socket = null;
    String input;
    BufferedReader console = null;
    static final public int PORT= 3345;

    void startservcons() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Can't open port " + PORT);
            System.exit(1);
        }
        System.out.print("Server started.");
    }

    void catchClient() throws IOException {
        try {
            socket = serverSocket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(1);
        }

        in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);

        System.out.println(" Expectation ");
    }

    void sendMessage(String msg) throws IOException {
        if (msg.equalsIgnoreCase("close") || msg.equalsIgnoreCase("exit")){
            System.out.println("Break Client");
            close();
        }
        out.println(" echo " + msg);
        System.out.println(msg);
    }

    void close() throws IOException {
        out.close();
        in.close();
        socket.close();
        serverSocket.close();
    }

    void writeToConsole() throws IOException {
        while (true) {
            console = new BufferedReader(new InputStreamReader(System.in));
            String txt = console.readLine();
            if (txt.equalsIgnoreCase("close") || txt.equalsIgnoreCase("exit")){
                System.out.println("Break Client");
                close();
            }
            sendMessage(txt);
        }
    }
}