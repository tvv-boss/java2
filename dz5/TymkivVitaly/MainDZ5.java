package ru.geekbrains.java2.dz.dz5.TymkivVitaly;

public class MainDZ5 extends Thread{
    private static final int SIZE = 100;
    private static final int HALF_SIZE = SIZE / 2;

    @Override
    public void  run(){
        try {
            float[] arr = new float[SIZE];
            for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
            long begin1 = System.currentTimeMillis();
            fillarr3(arr);
            System.out.println("Four  thread  ------ : " + (System.currentTimeMillis() - begin1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public float[] fillarr(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.println(" Thread 1 " + i);
        }
        return arr;
    }
    public float[] fillarr1(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.println(" Thread 2-1 " + i);
        }
        return arr;
    }
    public float[] fillarr2(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.println(" Thread 2-2 " + i);
        }
        return arr;
    }
    public float[] fillarr3(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.println(" Thread 4 " + i);
        }
        return arr;
    }

    public void runOneThread(){
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        long begin1 = System.currentTimeMillis();
        fillarr(arr);
        System.out.println("One thread ---------- " + (System.currentTimeMillis() - begin1));
//        System.out.println("  " + arr[arr.length-1]);
    }

    public void runTwoThreads() {
        float[] arr = new float[SIZE];
        float[] a1 = new float[HALF_SIZE];
        float[] a2 = new float[HALF_SIZE];
        long begin3 = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;

        long begin2 = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, a2, 0, HALF_SIZE);


        new Thread(() -> {
//            fillarr1(a1);
            System.arraycopy(fillarr1(a1), 0, arr, 0, HALF_SIZE);
        }).start();

        new Thread(() -> {
//            fillarr2(a2);
            System.arraycopy(fillarr2(a2), 0, arr, HALF_SIZE, HALF_SIZE);
        }).start();

//        System.arraycopy(a1, 0, arr, 0, HALF_SIZE);
//        System.arraycopy(a2, 0, arr, HALF_SIZE, HALF_SIZE);
        System.out.println("Two threads end : " + (System.currentTimeMillis() - begin2));
        System.out.println("Two threads full end : " + (System.currentTimeMillis() - begin3));
//        System.out.println("  " + arr[arr.length-1]);
//        System.out.println("  " + a1[a1.length-1]);
//        System.out.println("  " + a2[a2.length-1]);
    }

    public static void main(String s[]) throws InterruptedException {
        MainDZ5 e1 = new MainDZ5();
        MainDZ5 e2 = new MainDZ5();
        Thread tr = new MainDZ5();
        long b = System.currentTimeMillis();
//        e2.runTwoThreads();
        new Thread(()->e1.runTwoThreads()).start();
        tr.start();
//        long a = System.currentTimeMillis();
        new Thread(()->e2.runOneThread()).start();

//        System.out.println("One threads end --------- : " + (System.currentTimeMillis() - a));
//        long b = System.currentTimeMillis();

//        e2.runTwoThreads();
//        new Thread(()->e2.runTwoThreads()).start();
        try {
            e1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Full threads end ----------: " + (System.currentTimeMillis() - b));
    }
}
