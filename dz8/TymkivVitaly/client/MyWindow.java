package ru.geekbrains.java2.dz.dz8.TymkivVitaly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyWindow extends JFrame {
    JTextArea jta;
    JTextField jtf;
    JButton btnSend;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public MyWindow() {
        setTitle("Chat Client");
        setBounds(600, 400, 400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jta = new JTextArea();
        jta.setEditable(false);
        JScrollPane jsp = new JScrollPane(jta);
        add(jsp, BorderLayout.CENTER);
        JPanel jpBottom = new JPanel();
        jpBottom.setLayout(new BorderLayout());
        jtf = new JTextField();
        jpBottom.add(jtf);
        btnSend = new JButton("SEND");
        jpBottom.add(btnSend, BorderLayout.EAST);
        add(jpBottom, BorderLayout.SOUTH);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String str = in.readUTF();
                        jta.append(str);
                        jta.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMsg() {
        try {
            out.writeUTF(jtf.getText());
            jtf.setText("");
            jtf.grabFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
