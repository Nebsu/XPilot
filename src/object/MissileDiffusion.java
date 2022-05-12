/**
 * The missile diffusion class is a missile that has 5 missiles that are fired in a circular pattern
 */

package src.object;

public final class MissileDiffusion extends Missile {

    private final int MISSILE_SPEED = 30;
    private int direction;
    private MissileNormal[] Diffusion;

    public MissileDiffusion(double x, double y, int rotation, int shooter) {
        super(x, y, shooter);
        direction = rotation-8*2;
        Diffusion=new MissileNormal[5];
        for(int i=0;i<5;i++){
            Diffusion[i]=new MissileNormal(x, y, direction+8*i, shooter);
        }
    }

    @Override
    public final void move() {
        for(MissileNormal a:Diffusion){
            a.x += MISSILE_SPEED * Math.cos(Math.toRadians(a.getDirection()));
            a.y += MISSILE_SPEED * Math.sin(Math.toRadians(a.getDirection()));
        }
    }

    @Override
    public int getDirection() {
        return direction;
    }

    public final MissileNormal[] getDiffusion() {return this.Diffusion;}

}