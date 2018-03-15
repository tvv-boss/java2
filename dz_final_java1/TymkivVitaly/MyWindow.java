package ru.geekbrains.java2.dz.dz_final_java1.TymkivVitaly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
    public MyWindow(){
        setTitle("My Window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //закрыть процесс окна
        setSize(600,600);
        setLocationRelativeTo(null); //вызовет окно по центру экрана пользователя
        setResizable(false);

        setLayout(new FlowLayout());

        JButton button = new JButton("Button");
        JButton button2 = new JButton("Button");
        JButton button3 = new JButton("Button");
        JButton button4 = new JButton("Button");
        button.setPreferredSize(new Dimension(200,75));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Java!");
            }
        });
        add(button);
        add(button2);
        add(button3);
        add(button4);

        setVisible(true); //метод должен вызывать в последнюю очередь
    }
}
