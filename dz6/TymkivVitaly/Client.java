package ru.geekbrains.java2.dz.dz6.TymkivVitaly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static ru.geekbrains.java2.dz.dz6.TymkivVitaly.ServCons.PORT;

public class Client {
    public static void main(String[] args) throws IOException {
        ClientCons cliets = new ClientCons();
        System.out.println("Client started. Connecting to localhost:" + PORT);

        new Thread() {
            public void run() {
                try {
                    cliets.readMSG();
                } catch (IOException e) {
                    System.out.println(" Quit ");
                    try {
                        cliets.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    cliets.sendMSG();
                } catch (IOException e) {
                    System.out.println(" Chat down ");
                    try {
                        cliets.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }.start();
    }
}

class ClientCons {
    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out= null;
    BufferedReader console = null;
    String userMSG, serverMSG;

    public ClientCons() throws IOException {
        socket = new Socket("localhost",PORT);
        in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    void sendMSG() throws IOException {
        while (true) {
            if ((userMSG = console.readLine()) != null) {
                out.println(userMSG);
                if (userMSG.equalsIgnoreCase("close") || userMSG.equalsIgnoreCase("exit")){
                    System.out.println("Break chat");
                    close();
                    break;
                }
            }
        }
    }

    void readMSG() throws IOException {
        while (true) {
            if ((serverMSG = in.readLine()) != null){
                System.out.println(serverMSG);
            }
        }
    }

    void close() throws IOException {
        out.close();
        in.close();
        console.close();
        socket.close();
    }
}