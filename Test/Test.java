package Test;

import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
 
import java.awt.geom.AffineTransform;
 
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
 
public class Test extends JFrame implements KeyListener
{
     
    private BufferedImage img;
     
    JPanel frame;
     
    Graphics2D g2dbf;
 
    BufferedImage bgImage;
     
    AffineTransform identityTrans = new AffineTransform();
     
    AffineTransform af = new AffineTransform();
     
    Timer timer;
     
    int rotation,rotationTwo;
     
    int rotOneInc = 4,rotTwoInc = 4,cycleOne,cycleTwo;
     
    boolean timerStartFlag = true,rightRotationFlag = false,leftRotationFlag = false;
     
    Test()
    {
        setTitle("Test");
        setSize(840,680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
         
        bgImage = new BufferedImage(840,680,BufferedImage.TYPE_INT_RGB);
        g2dbf = bgImage.createGraphics();
         
         
        frame = new JPanel(){
             
            @Override
           public void paintComponent(Graphics g)
          {
              super.paintComponent(g);
            g2dbf.setPaint(Color.BLACK);
            g2dbf.fillRect(0,0,getWidth(),getHeight());
             
            g2dbf.setColor(Color.WHITE);
            g2dbf.drawString("Image #1 rotation: " + rotation , 5, 40);
            g2dbf.drawString("Image #2 rotation: " + rotationTwo, 5, 60);
            g2dbf.drawString("Image #1 full cycle/s: " + cycleOne , 5, 80);
            g2dbf.drawString("Image #2 full cycle/s: " + cycleTwo, 5, 100);
             
            g2dbf.setTransform(identityTrans);
            af.setToIdentity();
            af.translate(300,250);
            af.rotate(Math.toRadians(rotation),img.getWidth(this)/2, img.getHeight(this)/2);
            g2dbf.drawImage(img,af,this);
         
            g2dbf.setTransform(identityTrans);
            af.setToIdentity();
            af.translate(300,450);
            af.rotate(Math.toRadians(rotationTwo), img.getWidth(this)/2, img.getHeight(this)/2);
            g2dbf.drawImage(img,af,this);
         
            g.drawImage(bgImage,0,0,this);
         
          }
             
        };
         
        add(frame);
         
         
        try
        {
            img = ImageIO.read(new File("img/player_up.png"));
 
        }
        catch(IOException e){}
         
        addKeyListener(this);
         
         
         
        timer = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                rotationTwo += rotTwoInc;
                rotateImg(rightRotationFlag);
                rotateImg2(leftRotationFlag);
                frame.repaint();
                if(rotationTwo > 360) { rotationTwo = rotTwoInc; ++cycleTwo;}
            }
        });
         
         
    }
     
    //Sens Horaire
    void rotateImg(boolean canRotate)
    {
        if(canRotate)
        {
           rotation += rotOneInc;
 
           if(rotation > 360) { rotation = rotOneInc; ++cycleOne;}
        }
    }


    //Sens antihoraire
    void rotateImg2(boolean canRotate)
    {
        if(canRotate)
        {
           rotation -= rotOneInc;
 
           if(rotation == rotOneInc) { rotation = rotOneInc; --cycleOne;}
        }
    }
     
     
     
    public static void main(String[]args) 
    {
         
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                new Test();
            }
        });
         
    }
     
     
     
    @Override
    public void keyReleased(KeyEvent k) 
    { 
     
       int keyCode = k.getKeyCode();
        
       switch(keyCode)
       {
            
           case KeyEvent.VK_RIGHT:
           rightRotationFlag = false;
           break;

           case KeyEvent.VK_LEFT:
           leftRotationFlag = false;
           break;
            
       }
     
    }
    @Override
    public void keyTyped(KeyEvent k) { }
    @Override
    public void keyPressed(KeyEvent k) 
    {
         
        int keyCode = k.getKeyCode();
         
        switch(keyCode)
        {
             
            /*
            case KeyEvent.VK_A:
            rotation -= rotOneInc;
 
            if(rotation < 0) rotation = 360-rotOneInc;
            break;
            */
             
 
            case KeyEvent.VK_RIGHT:
            if(timerStartFlag){timerStartFlag = false; timer.start();}
            rightRotationFlag = true;
            break;

            case KeyEvent.VK_LEFT:
            if(timerStartFlag){timerStartFlag = false; timer.start();}
            leftRotationFlag = true;
            break;
             
        }
    }
     
}