package map;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;
import javax.imageio.*;


public class Map {
	
    public BufferedImage img_map; //image map
    public LinkedList<Obstacle> ListeObstacle=new LinkedList<>();
    public final int MAP_SIZE = 50;
    public int[][] infor_map=new int[MAP_SIZE][MAP_SIZE];
    public Graphics2D g2;

    public Map(){
        try{
            this.img_map=ImageIO.read(new File("ressources/background.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        g2=img_map.createGraphics();
        createinfor();
        createInforAMap();
        g2.drawImage(img_map,0,0,null);
    }

    // public void loadMap() {
    //     try {
    //         InputStream is = getClass().getResourceAsStream("/ressources/infor_map.txt");
    //         BufferedReader br = new BufferedReader(new InputStreamReader(is));
    //         int col = 0;
    //         int row = 0;
    //         while (col < 50 && row < 50) {
    //             String line = br.readLine();
    //             while (col < 50) {
    //                 String numbers[] = line.split("");
    //                 int num = Integer.parseInt(numbers[col]);
    //                 mapInfo[col][row] = num;
    //                 col++;
    //             }
    //             if (col == 50) {
    //                 col = 0;
    //                 row++;
    //             }
    //         }
    //         br.close();
    //     } catch (Exception e) {

    //     }
    // }

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
	        
	    }catch (IOException e1){
	        e1.printStackTrace();
	    	}
    }
    
    public void createInforAMap() {
    	for(int i=0;i<infor_map.length;i++) {
    		for(int j=0;j<infor_map[i].length;j++) {
    			if(infor_map[i][j]==1) {
    				int x[]={i*48,(i+1)*48,(i+1)*48,i*48,i*48};
    		        int y[]={j*48,j*48,(j+1)*48,(j+1)*48,j*48};
    		        Obstacle carre=new Obstacle(x,y);
    		        ListeObstacle.add(carre);
    		        carre.draw(g2);
    			}
    		}
    	}
    }

    public double distanceDeuxPoint(double x,double y,double x1,double y1) {
		double D=Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
		return D;
	}

    public double PointToLine(double x0,double y0,double x1,double y1,double x2,double y2) {
		double d;
		double a,b,c;
		a=distanceDeuxPoint(x1, y1, x2, y2);
	    b=distanceDeuxPoint(x1, y1, x0, y0);
	    c=distanceDeuxPoint(x2, y2, x0, y0);
	    	//cas 0
	      if (c <= 0.000001 || b <= 0.000001) {
	        d = 0;
	        return d;
	      }
	      //deux point superpose
	      if (a <= 0.000001) {
	        d = b;
	        return d;
	      }
	      //triangle rectangle en c ou b
	      if (c * c >= a * a + b * b) {
	        d = b;
	        return d;
	      }
	      if (b * b >= a * a + c * c) {
	        d = c;
	        return d;
	      }
	      //cas normal obtenir la hauteur
	      double p = (a + b + c) / 2;//demi perimetre
	      double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));//aire d'un triangle
	      d = 2 * s / a;//hauteur
	      return d;
	}
}
