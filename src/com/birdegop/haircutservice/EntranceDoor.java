package com.birdegop.haircutservice;

public class EntranceDoor extends Door {
    public EntranceDoor(HairCutService service) {
        super(service);
    }

    @Override
    protected void onPass(Client client) {
        System.out.println("Клиент {" + client.getName() + "} Заходит");
        service.enter(client);
    }
}
