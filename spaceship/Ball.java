package spaceship;
public class Ball extends Sprite {
    private boolean taken = false;
    
    public Ball(double x, double y) {
        super(x, y);
        initBall();
    }

    private void initBall() {
        loadImage("ressources/ball.png");
        getImageDimensions();
    }

    public void move(double x, double y){
        if(taken){
            this.x = x;
            this.y = y + 40;
        }
    }

    public void take(){
        this.taken = true;
    }

    public boolean isTaken(){
        return taken;
    }

}