/** The Launcher class launches the application and contains the main method. */

package src.main;

import src.game.GameLoop;

import java.io.IOException;

public final class Launcher {
    public static void main(String[] args) throws IOException {
        new Thread(new GameLoop()).start();
    }
}