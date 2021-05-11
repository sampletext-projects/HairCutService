package com.birdegop.haircutservice;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HairCutService {

    private final Door entranceDoor;
    private final Door exitDoor;

    private final HairDresser hairDresser;

    private final Lock queueLock;

    private final Queue<Client> clients;

    private int maxQueueSize;

    public HairCutService(int maxQueueSize) {
        entranceDoor = new EntranceDoor(this);
        exitDoor = new ExitDoor(this);
        hairDresser = new HairDresser(this);
        clients = new ArrayDeque<>();
        queueLock = new ReentrantLock();
        this.maxQueueSize = maxQueueSize;
    }

    public Lock getQueueLock() {
        return queueLock;
    }

    public Queue<Client> getClients() {
        return clients;
    }

    public void enter(Client client) {
        if (clients.size() == maxQueueSize) {
            System.out.println("Больше нет свободных мест");
            exit(client);
        } else {
            clients.add(client);
            hairDresser.notifyClient();
        }
    }

    public void exit(Client client) {
        exitDoor.pass(client);
    }

    public Door getEntranceDoor() {
        return entranceDoor;
    }

    public Door getExitDoor() {
        return exitDoor;
    }
}
