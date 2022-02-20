package titlemap;

import main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class TitleMap {

    // position :
    private double x;
    private double y;

    // bounds :
    private int xmin, ymin;
    private int xmax, ymax;
    
    // smooth scrolling :
    private double tween;

    // map :
    private int[][] map;
    private int titlesize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    // titleset :
    private BufferedImage titleset;
    private int numTitlesAcross;
    private Tile[][] tiles;

    // drawing :
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    // constructor :
    public TitleMap(int titlesize) {
        this.titlesize = titlesize;
        this.numRowsToDraw = GamePanel.HEIGHT/titlesize+2;
        this.numColsToDraw = GamePanel.WIDTH/titlesize+2;
        this.tween = 0.07;
    }

    // guetters :
    public int getTitlesize() {return titlesize;}

    public int getX() {return (int) x;}

    public int getY() {return (int) y;}

    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc/numTitlesAcross;
        int c = rc%numTitlesAcross;
        return tiles[r][c].getType();
    }

    public void setPosition(double x, double y) {
        this.x += (x-this.x)*tween;
        this.y += (y-this.y)*tween;
        fixBounds();
        colOffset = (int) -this.x/titlesize;
        rowOffset = (int) -this.y/titlesize;
    }

    private void fixBounds() {
        if (x<xmin) x = xmin;
        if (y<ymin) y = ymin;
        if (x>xmax) x = xmax;
        if (y>ymax) y = ymax;
    }

    public void loadTiles(String s) {
        try {
            this.titleset = ImageIO.read(getClass().getResourceAsStream(s));
            this.numTitlesAcross = titleset.getWidth()/titlesize;
            this.tiles = new Tile[2][numTitlesAcross];
            BufferedImage subImage;
            for (int col=0; col<numTitlesAcross; col++) {
                subImage = titleset.getSubimage(col*titlesize, 0, titlesize, titlesize);
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = titleset.getSubimage(col*titlesize, titlesize, titlesize, titlesize);
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String s) {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            this.numCols = Integer.parseInt(br.readLine());
            this.numRows = Integer.parseInt(br.readLine());
            this.map = new int[numRows][numCols];
            this.width = numCols * titlesize;
            this.height = numRows * titlesize;
            String delims = "\\s+";
            for (int row=0; row<numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col=0; col<numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        for (int row=rowOffset; row<rowOffset+numRowsToDraw; row++) {
            if (row>=numRows) break;
            for (int col=colOffset; col<colOffset+numColsToDraw; col++) {
                if (col>=numCols) break;
                if (map[row][col]==0) continue;
                int rc = map[row][col];
                int r = rc/numTitlesAcross;
                int c = rc%numTitlesAcross;
                g.drawImage(tiles[r][c].getImage(), (int) x+col*titlesize, (int) y+row*titlesize, null);
            }
        }
    }
    
}