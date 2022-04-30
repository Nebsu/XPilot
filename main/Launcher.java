package main;

import game.GameLoop;

import java.io.IOException;

public final class Launcher {

    public static void main(String[] args) throws IOException {
        new Thread(new GameLoop()).start();
    }

}