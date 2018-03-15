package ru.geekbrains.java2.dz.dz_final_java1.TymkivVitaly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

public class TicTacToe extends JFrame{
    public TicTacToe(){
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(620,620);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel jBottomPanel = new JPanel();
        jBottomPanel.setLayout(new GridLayout());
        JButton jbStart = new JButton("Start new Game");
        JButton jbExit = new JButton("Exit");

        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new TicTacToe();
            }
        });
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        GameField gf = new GameField();
        jBottomPanel.add(jbStart);
        jBottomPanel.add(jbExit);
        add(gf, BorderLayout.CENTER);
        add(jBottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
