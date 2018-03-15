package ru.geekbrains.java2.dz.dz1.TymkivVitaly;

import java.util.ArrayList;

import static ru.geekbrains.java2.dz.dz1.TymkivVitaly.CourseEnum.*;

public class Course implements Courseble {
    public CourseEnum course[] = new CourseEnum[]{run, swim, go, go, jump, run};
    public ArrayList result = new ArrayList();

    public Course() {
//        this.course = new String[]{"r", "s", "g","g","j","r"};
    }

    public void dolt(Team team) {
        result.clear();
        result.add(team.nameTeam);
        result.add(team.name);
        for (int i = 0; i < course.length; i++) {
            switch (course[i]) {
                case swim:
                    result.add(team.swim((int) (Math.random() * 10 + 25)));
                    break;
                case go:
                    result.add(team.go((int) (Math.random() * 40 + 80)));
                    break;
                case run:
                    result.add(team.run((int) (Math.random() * 100 + 450)));
                    break;
                case jump:
                    result.add(team.jump((Math.random() * 1 + 1.5)));
                    break;
            }

        }
    }

    public void showResult() {
        System.out.println(result.toString());
    }
}