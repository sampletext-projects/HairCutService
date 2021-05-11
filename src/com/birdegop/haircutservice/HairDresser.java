package com.birdegop.haircutservice;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HairDresser {

    boolean shouldContinue;
    HairCutService service;
    Thread thread;
    Lock wakeupLock;
    boolean wakeup = false;

    public HairDresser(HairCutService service) {
        this.service = service;
        shouldContinue = true;
        thread = new Thread(this::job);
        wakeupLock = new ReentrantLock(true);
        thread.start();
    }

    public void terminate() {
        shouldContinue = false;
    }

    public void notifyClient() {
        try {
            wakeupLock.lock();
            wakeup = true;
            wakeupLock.unlock();
        } catch (Exception ignored) {

        }
    }

    public void job() {
        boolean lastStatus = wakeup;
        while (shouldContinue) {
            wakeupLock.lock();
            if (wakeup) {
                if (lastStatus != wakeup) {
                    System.out.println("Парикмахер проснулся");
                    lastStatus = wakeup;
                }
                Queue<Client> clients = service.getClients();
                if (!clients.isEmpty()) {
                    Lock queueLock = service.getQueueLock();
                    queueLock.lock();
                    Client client = clients.poll();
                    queueLock.unlock();

                    make(client);
                }
                if (clients.isEmpty()) {
                    wakeup = false;
                    System.out.println("Парикмахер уснул");
                }
            }
            wakeupLock.unlock();
        }
    }

    public void make(Client client) {
        System.out.println("Парикмахер обслуживает клиента {" + client.getName() + "}");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        releaseClient(client);
    }

    private void releaseClient(Client client) {
        service.exit(client);
    }
}
