package object;
public class MissileDiffusion extends Missile{

    private final int MISSILE_SPEED = 30;
    private int direction;
    private MissileNormale[] Diffusion;


    public MissileDiffusion(double x, double y, int rotation) {
        super(x, y);
        direction = rotation-8*2;
        Diffusion=new MissileNormale[5];
        for(int i=0;i<5;i++){
            Diffusion[i]=new MissileNormale(x, y, direction+8*i);
        }
    }

    public void move() {
        // if(rebounce%2==0){
            for(MissileNormale a:Diffusion){
                a.x += MISSILE_SPEED * Math.cos(Math.toRadians(a.getdirection()));
                a.y += MISSILE_SPEED * Math.sin(Math.toRadians(a.getdirection()));
            }
        // }else{
        //     x -= MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        //     y -= MISSILE_SPEED * Math.sin(Math.toRadians(direction));
        // }
    }

    public MissileNormale[] getDiffusion(){return this.Diffusion;}
    
}