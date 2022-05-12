/** The Map class is used to create the map and the obstacles on it*/

package map;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.*;

public final class Map {
	
    private BufferedImage img_map;
    private LinkedList<Obstacle> ListeObstacle=new LinkedList<>();
	private LinkedList<BreakableWall> ListeBreakableWall=new LinkedList<>();
	private ArrayList<Bonus> bonusList = new ArrayList<>();
    private final int MAP_SIZE = 50;
    private final char[][] infor_map=new char[MAP_SIZE][MAP_SIZE];
    private Graphics2D g2;
	private BallHolder ball;
	private long lastTime;
	private LinkedList<Enemy> enemies = new LinkedList<>();
	private double shipX;
	private double shipY;

    public Map(String pathname){
        try{
            this.img_map=ImageIO.read(new File("ressources/images/background2.png"));
        }catch(IOException e){
            e.printStackTrace();
			System.out.println(e);
			System.exit(1);
        }
        g2=img_map.createGraphics();
        createinfor(pathname);
        createInforAMap();
    }

	/** Read the file "ressources/map/infor_map2.txt" 
	 *  and store the information in the array infor_map */
	private final void createinfor(String pathname){
    	try{
	        File fil = new File(pathname);
	        FileReader inputFil = new FileReader(fil);
	        BufferedReader in = new BufferedReader(inputFil);
	        String line;
	        int row=0;
	        while ((line=in.readLine())!=null){
	        	for(int i=0;i<MAP_SIZE;i++) {
	        		char c=line.charAt(i);
	        		this.infor_map[i][row]=c;
	        	}
	        	row++;
	        }
	        in.close();
	    }catch (IOException e1){
	        e1.printStackTrace();
	    }
    }
    
	/** It creates the obstacles and the goal in the map. */
    private final void createInforAMap(){
    	for(int i=0;i<MAP_SIZE;i++) {
    		for(int j=0;j<infor_map[i].length;j++) {
    			if(infor_map[i][j]=='W') {
    				int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
    		        Obstacle carre=new Obstacle(x,y);
    		        ListeObstacle.add(carre);
    		        carre.draw(g2);
    			}else if(infor_map[i][j]=='G'){
					int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
    		        Goal carre=new Goal(x,y);
    		        ListeObstacle.add(carre);
    		        carre.draw(g2);
				}else if(infor_map[i][j]=='B'){
					int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
					BallHolder ball= null;
					try {
						ball = new BallHolder(x,y);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					this.ball = ball;
    		        ListeObstacle.add(ball);
					ball.draw(g2);
				}else if(infor_map[i][j]=='X'){
					try {
						Enemy e = new Enemy(i*48, j*48);
						enemies.add(e);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}else if(infor_map[i][j]== 'S'){
					this.shipX = i*48;
					this.shipY = j*48;
				}else if(infor_map[i][j]== 'O'){
					int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
					BreakableWall wall=new BreakableWall(x,y);
					ListeBreakableWall.add(wall);
				}
    		}
    	}
    }

	// Getters :
	public final BufferedImage getImage() {return img_map;}
	public final LinkedList<Obstacle> getObstacles() {return ListeObstacle;}
	public final ArrayList<Bonus> getBonuses() {return bonusList;}
	public final char[][] getInforMap() {return infor_map;}
	public final Graphics2D getG2() {return g2;}
	public final BallHolder getBall() {return ball;}
	public final long getLastTime() {return lastTime;}
	public final LinkedList<Enemy> getEnemies() {return enemies;}
	public final double getShipX() {return shipX;}
	public final double getShipY() {return shipY;}
	public final LinkedList<BreakableWall> getListeBreakableWall() {return ListeBreakableWall;}

	/** Add a bonus to the map */
	public final void addBonus(){
		lastTime = System.currentTimeMillis();
        Random r1 = new Random();
		Random r2 = new Random();
        while(true){
			int i = r1.nextInt(MAP_SIZE);
			int j = r2.nextInt(MAP_SIZE);
            if(infor_map[i][j] == '-'){
				int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
				int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
				Bonus bonus = new Bonus(x,y,i*48, j*48);
				bonusList.add(bonus);
				return;
            }
        }
    }

}