/**
 * The missile diffusion class is a missile that has 5 missiles that are fired in a circular pattern
 */
package object;

public class MissileDiffusion extends Missile{

    private final int MISSILE_SPEED = 30;
    private int direction;
    private MissileNormale[] Diffusion;
    public int shooter;


    public MissileDiffusion(double x, double y, int rotation, int shooter) {
        super(x, y, shooter);
        direction = rotation-8*2;
        Diffusion=new MissileNormale[5];
        for(int i=0;i<5;i++){
            Diffusion[i]=new MissileNormale(x, y, direction+8*i, shooter);
        }
    }

    public void move() {
        for(MissileNormale a:Diffusion){
            a.x += MISSILE_SPEED * Math.cos(Math.toRadians(a.getdirection()));
            a.y += MISSILE_SPEED * Math.sin(Math.toRadians(a.getdirection()));
        }
    }

    public MissileNormale[] getDiffusion(){return this.Diffusion;}

    @Override
    public int getdirection() {
        return direction;
    }
    
}