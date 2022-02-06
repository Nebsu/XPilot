package Xpilot;

public class Refresh extends Thread {
    public static final int time=20;
    GamePanel p;
    public Refresh(GamePanel p){
        this.p=p;
    }
    public void run(){
        while(true){
            p.repaint();
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
