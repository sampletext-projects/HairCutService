package com.birdegop.haircutservice;

public class ExitDoor extends Door {
    public ExitDoor(HairCutService service) {
        super(service);
    }

    @Override
    protected void onPass(Client client) {
        System.out.println("Клиент {" + client.getName() + "} Выходит");
    }
}
