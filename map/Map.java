package map;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.*;

public class Map {
	
    public BufferedImage img_map; //image map
    public LinkedList<Obstacle> ListeObstacle=new LinkedList<>();
	public ArrayList<Bonus> bonusList = new ArrayList<>();
    public final int MAP_SIZE = 50;
    public int[][] infor_map=new int[MAP_SIZE][MAP_SIZE];
    public Graphics2D g2;
	public Ball ball;
	public long lastTime;

    public Map() throws IOException{
        try{
            this.img_map=ImageIO.read(new File("ressources/background2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        g2=img_map.createGraphics();
        createinfor();
        createInforAMap();
    }

	public void addBonus(){
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

 	public void createinfor(){
    	try{
	        File fil = new File("ressources/infor_map2.txt");
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
    
    public void createInforAMap() throws IOException {
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
    		        Ball ball=new Ball(x,y);
					this.ball = ball;
    		        ListeObstacle.add(ball);
					ball.draw(g2);
				}
    		}
    	}
    }

}