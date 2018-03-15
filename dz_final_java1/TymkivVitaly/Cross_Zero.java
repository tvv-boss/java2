package ru.geekbrains.java2.dz.dz_final_java1.TymkivVitaly;

import java.util.Random;
import java.util.Scanner;

public class Cross_Zero {
    public static int SIZE = 3 ;
    public static int DOTS_TO_WIN = 3 ;
    public static final char DOT_EMPTY = '•' ;
    public static final char DOT_X = 'X' ;
    public static final char DOT_O = 'O' ;
    public static char [][] map ;
    public static Scanner sc = new Scanner( System . in );
    public static Random rand = new Random();
    public static int ISMAPFULL = SIZE * SIZE ;
    public static int LAST_X;
    public static int LAST_Y;
    public static int KONTR_X;
    public static int KONTR_Y;
    public static char humanSymbol;
    public static char aiSymbol;

    public static void main ( String [] args ) {
        do {
            System.out.println("Начинаем игру да - 1 или нет  - 0 ");
            int begin = sc.nextInt();
            if (begin != 1){
                break;
            }
            initMap ();
            printMap ();
            int all = 0;
            if(chooseSymbol() == 0) {
                aiTurn();
                printMap();
                all++;
            }
                while(true){
                    humanTurn();
                    all++;
                    printMap();
                    if(checkWin(humanSymbol)){
                        System.out.println("Победил человек!");
                        break;
                    }
                    if (ISMAPFULL == all) {
                        System.out.println("Ничья");
                        break;
                    }
                    aiTurn();
                    all++;
                    printMap();
                    if(checkWin(aiSymbol)){
                        System.out.println("Победил Искуственный интеллект");
                        break;
                    }
                    if (ISMAPFULL == all) {
                        System.out.println("Ничья");
                        break;
                    }
                }
            System.out.println("Игра закончена");
        } while (true);
    }
    public static int chooseSymbol(){
        System.out.println("Выберите свой символ Х(1) или О(0)");
        int choice = sc.nextInt();
        if(choice == 1){
            humanSymbol = DOT_X;
            aiSymbol = DOT_O;
        }else {
            humanSymbol = DOT_O;
            aiSymbol = DOT_X;
        }
        return choice;
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
            System . out . println ( "Введите координаты в формате X Y" );
            x = sc . nextInt () - 1 ;
            y = sc . nextInt () - 1 ;
        } while (! isCellValid ( x , y )); // while(isCellValid(x, y) == false)
        LAST_Y = y;
        LAST_X = x;
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
}
