package ru.Voloshinskiy.Java2.Lesson6.Server;

/**
 * Created by Alexander V on 19.01.2017.
 */
public class MessageSender implements Runnable {
    private String message;
    private Server server;

    public MessageSender(String message, Server server) {
        this.message = message;
        this.server = server;
    }

    @Override
    public void run() {
        server.sendAll(message);
    }
}
