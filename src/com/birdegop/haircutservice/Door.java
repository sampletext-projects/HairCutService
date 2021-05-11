package com.birdegop.haircutservice;

public abstract class Door {
    protected abstract void onPass(Client client);

    protected HairCutService service;

    public Door(HairCutService service) {
        this.service = service;
    }

    public void pass(Client client) {
        onPass(client);
    }
}
