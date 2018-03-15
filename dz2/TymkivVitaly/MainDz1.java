package ru.geekbrains.java2.dz.dz1.TymkivVitaly;

//Домашние задание №2 Тымкив Виталий Исключения
    public class MainDz1 {
        public static String[][] map = {
                {"0", "1", "2", "3"},
                {"4", "5", "6", "7"},
                {"0", "1", "2", "3"},
                {"4", "5", "6", "7"}
        };
        public static String[][] map1 = {
                {"0", "1", "2", "3"},
                {"4", "5", "6", "7"},
                {"0", "g", "2", "3"},
                {"4", "5", "6", "b"}
        };
        public static String[][] map2 = {
                {"0", "1", "2", "3", "3"},
                {"4", "5", "6", "7", "3"},
                {"0", "g", "2", "3", "3"},
                {"0", "g", "2", "3", "3"},
                {"4", "5", "6", "b", "3"}
        };
        public static String[][] map3 = {
                {"0", "1", "2"},
                {"4", "5", "6"},
                {"0", "1", "2"},
                {"4", "5", "6"}
        };

        public static void main(String[] args) {

            homework43r(map);
            homework43r(map3);
            homework43r(map2);
            homework44r(map);
            homework44r(map1);

            try {
                homework45r(map);
                homework45r(map3);
            } catch (MyArraySizeException e){
//                e.printStackTrace();
                System.out.println(e);
            }
            try {
                homework46r(map);
                homework46r(map1);
            }catch (MyArrayDataException e){
//                e.printStackTrace();
                System.out.println(e);
            }
            try {
                homework47r(map);
                homework47r(map3);
            } catch (ArrayIndexOutOfBoundsException e){
//                e.printStackTrace();
                System.out.println(e);
            }
            try {
                homework48r(map);
                homework48r(map1);
            } catch (NumberFormatException e){
//                e.printStackTrace();
                System.out.println(e);
            }
        }

        //реализация обработки исключения в методе по индексу
        public static void homework43r(String[][] varmap) {
            String[][] arr = new String[4][4];
            int indexArr = arr.length;
            if (indexArr < varmap.length) indexArr = varmap.length;
            try {
                for (int i = 0; i < indexArr; i++) {
                    for (int j = 0; j < indexArr; j++) {
                        arr[i][j] = varmap[i][j];
                        System.out.print(arr[i][j]);
                    }
                    System.out.println();
                }
                System.out.println();
            } catch (ArrayIndexOutOfBoundsException e) {
//                e.printStackTrace();
                System.out.println(" Error ArrayIndexOutOfBoundsException" + "  " + e);
            } finally {
                System.out.println("finally # 1 ---");
//                Arrays.stream(varmap).forEach(System.out::println);
            }
        }
        //реализация обработки исключения в методе преобразованию
        public static void homework44r(String[][] varmap) {
            int[][] arr = new int[4][4];
            int itog = 0;
            int ii = 0;
            int jj = 0;
            try {
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr.length; j++) {
                        ii = i; jj = j;
                        arr[i][j] = Integer.parseInt(varmap[i][j]);
                        itog += arr[i][j];
                        System.out.print(arr[i][j]);
                    }
                    System.out.println();
                }
                System.out.println(" Itog  - " + itog);
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                System.out.println(" Error NumberFormatException " + " Index " + ii + " - " + jj + " value " + varmap[ii][jj] + " stack " + e);
            }finally {
                System.out.println("finally # 2");
            }
        }
        //реализация своего исключения проброс по индексу
            public static void homework45r(String[][] varmap){
            String[][] arr = new String[4][4];
            int indexArr = arr.length;
            if (indexArr < varmap.length) indexArr = varmap.length;
            int ii = 0;
            int jj = 0;
                for (int i = 0; i < indexArr; i++) {
                    for (int j = 0; j < indexArr; j++) {
                        ii = i; jj = j;
                        arr[i][j] = varmap[i][j];
                        System.out.print(arr[i][j]);
                    }
                    System.out.println();
                }
                System.out.println();
            throw new MyArraySizeException(" Error MyArraySizeException" + " value " + varmap[ii][jj]);
        }
        //реализация своего исключения проброс по преобразованию
            public static void homework46r(String[][] varmap){
            int[][] arr = new int[4][4];
            int itog = 0;
            int ii = 0;
            int jj = 0;
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr.length; j++) {
                        ii = i; jj = j;
                        arr[i][j] = Integer.parseInt(varmap[i][j]);
                        itog += arr[i][j];
                        System.out.print(arr[i][j]);
                    }
                    System.out.println();
                }
                System.out.println(" Itog  - " + itog);
                throw new MyArrayDataException("Error MyArrayDataException Index " + ii + " - " +jj +" value "+ varmap[ii][jj]);
        }
        //реализация исключения пробросом стандартное исключение по индексу
        public static void homework47r(String[][] varmap)  throws ArrayIndexOutOfBoundsException {
            String[][] arr = new String[4][4];
            int indexArr = arr.length;
            if (indexArr < varmap.length) indexArr = varmap.length;
            System.out.println(" ------------ #1");
            for (int i = 0; i < indexArr; i++) {
                for (int j = 0; j < indexArr; j++) {
                    arr[i][j] = varmap[i][j];
                    System.out.print(arr[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
        //реализация исключения пробросом стандартное исключение по преобразованию
        public static void homework48r(String[][] varmap) throws NumberFormatException{
            int[][] arr = new int[4][4];
            System.out.println(" ------------ #2");
            int itog = 0;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length; j++) {
                    arr[i][j] = Integer.parseInt(varmap[i][j]);
                    itog += arr[i][j];
                    System.out.print(arr[i][j]);
                }
                System.out.println();
            }
            System.out.println(" Itog  - " + itog);
        }
    }
