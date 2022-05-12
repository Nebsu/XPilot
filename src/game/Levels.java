package src.game;

public enum Levels {
    LEVEL1("ressources/map/level1.txt"),
    LEVEL2("ressources/map/level2.txt"),
    LEVEL3("ressources/map/level3.txt");

    String pathname;
    Levels(String pathname){
        this.pathname = pathname;
    }
}
