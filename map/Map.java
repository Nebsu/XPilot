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
	private ArrayList<Bonus> bonusList = new ArrayList<>();
    private final int MAP_SIZE = 50;
    private final int[][] infor_map=new int[MAP_SIZE][MAP_SIZE];
    private Graphics2D g2;
	private BallHolder ball;
	private long lastTime;
	private LinkedList<Enemy> enemies = new LinkedList<>();

    public Map(){
        try{
            this.img_map=ImageIO.read(new File("ressources/images/background2.png"));
        }catch(IOException e){
            e.printStackTrace();
			System.out.println(e);
			System.exit(1);
        }
        g2=img_map.createGraphics();
        createinfor();
        createInforAMap();
    }

	/** Read the file "ressources/map/infor_map2.txt" 
	 *  and store the information in the array infor_map */
	private final void createinfor(){
    	try{
	        File fil = new File("ressources/map/infor_map2.txt");
	        FileReader inputFil = new FileReader(fil);
	        BufferedReader in = new BufferedReader(inputFil);
	        String line;
	        int row=0;
	        while ((line=in.readLine())!=null){
	        	for(int i=0;i<MAP_SIZE;i++) {
	        		char c=line.charAt(i);
	        		int num=Character.getNumericValue(c);
	        		this.infor_map[i][row]=num;
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
    			if(infor_map[i][j]==1) {
    				int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
    		        Obstacle carre=new Obstacle(x,y);
    		        ListeObstacle.add(carre);
    		        carre.draw(g2);
    			}else if(infor_map[i][j]==2){
					int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
    		        Goal carre=new Goal(x,y);
    		        ListeObstacle.add(carre);
    		        carre.draw(g2);
				}else if(infor_map[i][j]==3){
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
				}else if(infor_map[i][j]==4){
					Enemy e = null;
					try {
						e = new Enemy(i*48, j*48);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
					enemies.add(e);
				}
    		}
    	}
    }

	// Getters :
	public final BufferedImage getImage() {return img_map;}
	public final LinkedList<Obstacle> getObstacles() {return ListeObstacle;}
	public final ArrayList<Bonus> getBonuses() {return bonusList;}
	public final int[][] getInforMap() {return infor_map;}
	public final Graphics2D getG2() {return g2;}
	public final BallHolder getBall() {return ball;}
	public final long getLastTime() {return lastTime;}
	public final LinkedList<Enemy> getEnemies() {return enemies;}

	/** Add a bonus to the map */
	public final void addBonus(){
		lastTime = System.currentTimeMillis();
        Random r1 = new Random();
		Random r2 = new Random();
        while(true){
			int i = r1.nextInt(MAP_SIZE);
			int j = r2.nextInt(MAP_SIZE);
            if(infor_map[i][j] == 0){
				int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
				int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
				Bonus bonus = new Bonus(x,y,i*48, j*48);
				bonusList.add(bonus);
				return;
            }
        }
    }

}