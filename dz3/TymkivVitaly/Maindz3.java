package ru.geekbrains.java2.dz.dz3.TymkivVitaly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Maindz3 {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Passenger> passengers = null;
        Map<Integer, List<Passenger>> passengerMap = new TreeMap<>();
        int totalplane = 11;
        //для проверки сортировки создаем самолеты в обратном порядке
        for (int i = totalplane; i > 0; i--) {
            passengers = new ArrayList<>();
            passengerMap.put(i, passengers);
        }
        System.out.println(" Read data is file and concole   Y/N :");
        String answYN = scanner.next();
        if (answYN.equals("y") ||answYN.equals("Y")) {
            File myfile = new File("C:/soft/JAVA/java2/java2/src/main/java/ru/geekbrains/java2/dz/dz3/TymkivVitaly/1.txt");
            FileReader fr = new FileReader(myfile);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            String nameP = null;
            String documentP = null;
            int i = 1, flightNumberP;
            while ((line = reader.readLine()) != null) {
                if (i == 1) {
                    nameP = line;
                    i++;
                } else if (i == 2) {
                    documentP = line;
                    i++;
                } else {
                    i = 1;
                    flightNumberP = Integer.parseInt(line);
                    if (passengerMap.containsKey(flightNumberP)) {
                        passengerMap.get(flightNumberP).add(new Passenger(nameP, documentP, flightNumberP));
                    } else  System.out.println(" Wrong flight Number : " +nameP + "  " + documentP + "  " + flightNumberP);
                }
            }
            reader.close();
        } else {
        do {
            System.out.println(" Imput Name :");
            String name = scanner.next();
            if (name.equals("exit")) break;
            System.out.println(" Imput document :");
            String document = scanner.next();
            if (document.equals("exit")) break;
            System.out.println(" Imput flightNumber :");
            try {
                int flightNumber = scanner.nextInt();
                if (flightNumber == 0) break;
                if (passengerMap.containsKey(flightNumber)) {
                    passengerMap.get(flightNumber).add(new Passenger(name, document, flightNumber));
                } else  System.out.println(" Wrong flight Number : " +name + "  " + document + "  " + flightNumber);
            } catch (Exception e){
                System.out.println(" Wrong flightNumber");
            }
        }while (true);
        }

        for (Map.Entry e : passengerMap.entrySet()) {
            System.out.println("Flight Number " + e.getKey() + " passengers:");
            passengers = (List<Passenger>) e.getValue();
            Comparator<Passenger> c = (name1, name2) -> name1.getName().compareToIgnoreCase(name2.getName());
            passengers.sort(c);
            for (Passenger pp : (ArrayList<Passenger>)e.getValue()) {
                System.out.println("  " + pp.getName() + " " + pp.getDocument());
            }
            System.out.println("Total passengers : " + ((ArrayList<Passenger>) e.getValue()).size() + "\n");
        }
    }
}
