package ru.geekbrains.java2.dz.dz1.TymkivVitaly;

public class Team implements Swim, Jump, Go, Run {
    public String nameTeam;
    public String name;


//    public Team(String nameTeam) {
//        this.nameTeam = nameTeam;
//    }

    public Team(String nameTeam, String name) {
        this.nameTeam = nameTeam;
        this.name = name;
    }
//    public void showResult() {
//
////        for (int i = 0; i < finaly.size(); i++) {
////            System.out.print(" " + finaly.get(i));
////        }
//        System.out.println();
//    }

    public void showSostav() {
        System.out.println(this.nameTeam + "  " + this.name);
    }
    @Override
    public String swim(int dist) {

        if(dist > 30) {
            System.out.println(" water ok");
        }else {
            System.out.println(" water Lol " +dist);
        }
        return " water " + dist;
    }

    @Override
    public String jump(double height) {
        if(height > 1.5d) {
            System.out.println(" jump ok");
        }else {
            System.out.println(" jump Lol " +height);
        }
        return " jump " + height;
    }
    @Override
    public String go(int dist) {
        if(dist > 100) {
            System.out.println(" go ok");
        }else {
            System.out.println(" go Lol " +dist);
        }
        return " go " + dist;
    }

    @Override
    public String run(int dist) {
        if(dist > 500) {
            System.out.println(" run ok");
        }else {
            System.out.println(" run Lol " +dist);
        }
        return " run " + dist;
    }
}
