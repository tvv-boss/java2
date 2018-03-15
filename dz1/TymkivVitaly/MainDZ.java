package ru.geekbrains.java2.dz.dz1.TymkivVitaly;

public class MainDZ {
//    public ArrayList result = new ArrayList();

    public static void main(String[] args) {
        Team[] team = new Team[4];
        Courseble c = new Course();
        team[0] = new Team("Black dragon", "Ivan");
        team[1] = new Team("Black dragon", "Vova");
        team[2] = new Team("Black dragon", "Bob");
        team[3] = new Team("Black dragon", "Rob");
        for (int i = 0; i < team.length; i++) {
            team[i].showSostav();
        }
        for (int i = 0; i < team.length; i++) {
            c.dolt(team[i]);
            c.showResult();
        }
    }

}
