package ru.Voloshinskiy.Java2.Lesson6.Client;

import java.io.IOException;

/**
 * Created by Alexander V on 15.01.2017.
 */
public class Client {
    public static final int SERVER_PORT = 8189;
    public static final String SERVER_HOST = "127.0.0.1";
    public static void main(String[] args){
        System.out.println("Client app starts");
        try {
            Window wind =  new Window(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client app ends");
    }

}
