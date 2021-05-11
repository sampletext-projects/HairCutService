package com.birdegop.haircutservice;

public class Main {

    public static void main(String[] args) {

        HairCutService service = new HairCutService(3);

        for (int i = 0; i < 4; i++) {
            service.getEntranceDoor().pass(new Client("Cl" + i));
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //service.getEntranceDoor().pass(new Client("Mike"));
    }
}
