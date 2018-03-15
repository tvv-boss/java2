package ru.geekbrains.java2.dz.dz_final_java1.TymkivVitaly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Scanner;

//import static com.company.workgroup.GameField.Cross_Zero.*;

public class GameField extends JPanel{
//    private char[][] map;
//    public final int SIZE = 3;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;

    public static int SIZE = 3 ;
    public static int DOTS_TO_WIN = 3 ;
    public static final char DOT_EMPTY = '•' ;
    public static final char DOT_X = 'X' ;
    public static final char DOT_O = 'O' ;
    public static char [][] map ;
    public static Random rand = new Random();
    public static int LAST_X;
    public static int LAST_Y;
    public static int KONTR_X;
    public static int KONTR_Y;
    public static int clX;
    public static int clY;
    public static char humanSymbol;
    public static char aiSymbol;


    public GameField(){
        map = new char[SIZE][SIZE];
        int gameok = JOptionPane.showConfirmDialog(
                null,
                "Начинаем игру ?" ,
                "Крестики нолики",
                JOptionPane.YES_NO_OPTION);
        if(gameok == JOptionPane.YES_OPTION){
//            JOptionPane.showMessageDialog(null, "Opening...");
        }else {
            JOptionPane.showMessageDialog(null, "Goodbye");
            System.exit(0);
        }
        initMap();
        // выбор символа?
        int n = JOptionPane.showConfirmDialog(
                null,
                "Играем за Х ?!" ,
                "Выбор первого хода",
                JOptionPane.YES_NO_OPTION);

        if(n == JOptionPane.YES_OPTION){
            humanSymbol = DOT_X;
            aiSymbol = DOT_O;
        }else {
            humanSymbol = DOT_O;
            aiSymbol = DOT_X;
            aiTurn();
            repaint();
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                System.out.println(e.getX() + " " + e.getY());
                clX = e.getX()/cellWidth;
                clY = e.getY()/cellHeight;
                System.out.println((clX+1) + " " + (clY+1));
//                map[clX][clY] = 'X';
                if (isCellValid (clX , clY)) gamelogic();
//                if(e.getY() > 560 && e.getY() < 630) new TicTacToe();
                repaint();//вызывает метод paintComponent();
            }
        });
    }

    public void gamelogic(){

        humanTurn();
        printMap();
        repaint();
        if(checkWin(humanSymbol)){
            JOptionPane.showMessageDialog(null, "Победил человек!");
            System.out.println("Победил человек!");
            initMap();
            repaint();
//            new TicTacToe();
        }
        if(isMapFull()){
            JOptionPane.showMessageDialog(null, "Ничья!");
            System.out.println("Ничья");
            initMap();
            repaint();
//            new TicTacToe();
        }
        aiTurn();
        printMap();
        repaint();
        if(checkWin(aiSymbol)){
            JOptionPane.showMessageDialog(null, "Победил Искуственный интеллект");
            System.out.println("Победил Искуственный интеллект");
            initMap();
            repaint();
//            new TicTacToe();
        }
        if(isMapFull()){
            JOptionPane.showMessageDialog(null, "Ничья!");
            System.out.println("Ничья");
            initMap();
            repaint();
//            new TicTacToe();
        }
    }

    public static boolean isMapFull(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    public static boolean checkWin ( char symb ) {
        int diag1 = 0;
        int diag2 = 0;
        for(int i = 0; i < SIZE; i++){
            int row = 0;
            int column = 0;
            for(int j = 0; j < SIZE; j++){
                if(map[i][j] == symb) row++;
                else row = 0;
                if(map[j][i] == symb) column++;
                else column = 0;
                if(i==j && map[i][j] == symb) diag1++;
                if(i+j == SIZE-1 && map[i][j] == symb) diag2++;
                if(row == DOTS_TO_WIN || column == DOTS_TO_WIN || diag1 == DOTS_TO_WIN || diag2 == DOTS_TO_WIN) return true;
            }
        }
        return false;
    }

    public static void aiTurn () {
        int x , y , z, a;
        do {
            if (isCellValid ( 1 , 1 )) {
                x = y = 1;
            }else{
                x = rand . nextInt ( SIZE );
                y = rand . nextInt ( SIZE );
                for (int i = 0; i < 10; i++) {
                    if (LAST_X == SIZE - 1) {
                        z = rand.nextInt(SIZE - 1) + 1;
                    } else if (LAST_X == 0) {
                        z = rand.nextInt(SIZE - 1);
                    } else {
                        z = rand.nextInt(SIZE);
                    }
                    if (LAST_Y == SIZE - 1) {
                        a = rand.nextInt(SIZE - 1) + 1;
                    } else if (LAST_Y == 0) {
                        a = rand.nextInt(SIZE - 1);
                    } else {
                        a = rand.nextInt(SIZE);
                    }
                    if (isCellValid ( z , a )){
                        x = z;
                        y = a;
                        break;
                    }
                }
                if (checkKontrWin(aiSymbol)){
                    x = KONTR_X;
                    y = KONTR_Y;
                }
            }
        } while (! isCellValid ( x , y ));
        System . out . println ( "Компьютер походил в точку " + ( x + 1 ) + " " + ( y + 1 ));
//            clX = x;
//            clY = y;
        map [ y ][ x ] = aiSymbol ;
    }
    public static boolean checkKontrWin ( char symb ) {
        int fl;
        for (int i = 0; i < map.length ; i++) {
            int win_lineg =0, win_lineg1  = 0;
            fl =0;
            for (int k = 0; k <  map.length ; k++) {
                if (map[k][i] == symb){
                    win_lineg = 0;
                    continue;
                }else if (map[k][i] == '•' && fl == 0){
                    KONTR_Y = k;
                    KONTR_X = i;
                    fl++;
                    win_lineg++;
                    if (win_lineg == DOTS_TO_WIN) return true;
                }else if (map[k][i] == '•' && fl > 0){
//                    fl = 0;
//                    break;
                }else {
                    win_lineg++;
                    if (win_lineg == DOTS_TO_WIN) return true;
                }
            }
            fl = 0;
            for (int j = 0; j < map.length ; j++) {
                if (map[i][j] == symb) {
                    win_lineg = 0;
                    continue;
                } else if (map[i][j] == '•' && fl == 0) {
                    KONTR_Y = i;
                    KONTR_X = j;
                    fl++;
                    win_lineg1++;
                    if (win_lineg1 == DOTS_TO_WIN) return true;
                } else if (map[i][j] == '•' && fl > 0) {
//                    fl = 0;
//                    break;
                } else{
                    win_lineg1++;
                    if (win_lineg1 == DOTS_TO_WIN) return true;
                }
            }
        }
        // DOTS_TO_WIN
        int win_linep = 0, win_linep1 = 0;
        fl = 0;
        for (int k = 0; k < map.length ; k++) {
            if (map[k][k] == symb){
                win_linep = 0;
                continue;
            }else if (map[k][k] == '•' && fl == 0){
                KONTR_Y = k;
                KONTR_X = k;
                fl++;
                win_linep++;
                if(win_linep == DOTS_TO_WIN )return true;
            }else if (map[k][k] == '•' && fl > 0){
//                fl = 0;
//                break;
            }else {
                win_linep++;
                if(win_linep == DOTS_TO_WIN )return true;
            }
        }
        fl = 0;
        for (int k = 0, j = map.length - 1; k < map.length ; k++, j--) {
            if (map[k][j] == symb){
                win_linep1 = 0;
                continue;
            }else if (map[k][j] == '•' && fl == 0){
                KONTR_Y = k;
                KONTR_X = j;
                fl++;
                win_linep1++;
                if (win_linep1 == DOTS_TO_WIN) return true;
            }else if (map[k][j] == '•' && fl > 0){
//                fl = 0;
//                //break;
            }else {
                win_linep1++;
                if (win_linep1 == DOTS_TO_WIN) return true;
            }
        }
        return false;
    }
    public static void humanTurn () {
        int x , y ;
        do {
            x = clX;
            y = clY;
//                System . out . println ( "Введите координаты в формате X Y" );
//                x = sc . nextInt () - 1 ;
//                y = sc . nextInt () - 1 ;
        } while (! isCellValid ( x , y )); // while(isCellValid(x, y) == false)
        LAST_Y = y;
        LAST_X = x;
        clX = x;
        clY = y;
        map [ y ][ x ] = humanSymbol ;
    }
    public static boolean isCellValid ( int x , int y ) {
        if ( x < 0 || x >= SIZE || y < 0 || y >= SIZE ) return false ;
        if ( map [ y ][ x ] == DOT_EMPTY ) return true ;
        return false ;
    }
    public static void initMap () {
        map = new char [ SIZE ][ SIZE ];
        for ( int i = 0 ; i < SIZE ; i ++) {
            for ( int j = 0 ; j < SIZE ; j ++) {
                map [ i ][ j ] = DOT_EMPTY ;
            }
        }
    }
    public static void printMap () {
        for ( int i = 0 ; i <= SIZE ; i ++) {
            System . out . print ( i + " " );
        }
        System . out . println ();
        for ( int i = 0 ; i < SIZE ; i ++) {
            System . out . print (( i + 1 ) + " " );
            for ( int j = 0 ; j < SIZE ; j ++) {
                System . out . print ( map [ i ][ j ] + " " );
            }
            System . out . println ();
        }
        System . out . println ();
    }

    @Override
    protected void paintComponent(Graphics g){ //вызывается для отрисовки JPanel.
        width = getWidth();
        height = getHeight();
        cellWidth = width/SIZE;
        cellHeight = height/SIZE;
        for(int i = 0; i < SIZE + 1; i++){
            g.drawLine(0, i*cellHeight, width, i*cellHeight); //горизонтальная линия
            g.drawLine(i*cellWidth, 0, i*cellWidth, height); //вертикальная линия
        }
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(map[i][j] == 'X'){
                    g.setColor(Color.BLACK);
                    g.drawLine(j*cellWidth+10, i*cellHeight+10, j*cellWidth+cellWidth -10, i*cellHeight+cellHeight -10);
                    g.drawLine(j*cellWidth+cellWidth -10, i*cellHeight + 10, j*cellWidth +10, i*cellHeight+cellHeight-10);
                }else if (map[i][j] == 'O'){
                    g.setColor(Color.BLUE);
                    g.fillOval(j*cellWidth + 10, i*cellHeight + 10, cellWidth - 20, cellHeight - 20);
                    g.setColor(Color.WHITE);
                    g.fillOval(j*cellWidth + 20, i*cellHeight + 20, cellWidth - 40, cellHeight - 40);

                }
            }
        }

    }
}
