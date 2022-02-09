package spaceship;
public class Ball extends Sprite {
    
    public Ball(double x, double y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {
        loadImage("ressources/ball.png");
        getImageDimensions();
    }

}