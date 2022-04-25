package main;

import java.io.IOException;

import game.GameLoop;

public class Launcher {

    public static void main(String[] args) throws IOException {
        new Thread(new GameLoop()).start();
    }

}