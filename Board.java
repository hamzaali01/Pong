import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;


public class Board extends JPanel implements ActionListener
{
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private final int DELAY = 1;
    private int w = 1024;
    private int h = 768;	
    private Timer timer;
    private int count = 0;
    public int score=0;
    private int score2=0;
    public static final Color LIGHT_BLUE  = new Color(51,153,255);
    public static final Color GREEN  = new Color(0,204,0);
    public boolean inGame;

    int ballx = w/2; int bally = h/2; int dx = 5; int dy = 5;
    int playerY = 0; int playerSpeed = 0; boolean playerCanMove=false;
    int player2Y = h-150; boolean player2CanMove=false;
    Rectangle player1Rect; Rectangle player2Rect; Rectangle ballRect;
	
       
    public Board() 
    {    	
        initBoard();
    }
    
    private void initBoard() //Initializes all the game objects
    {	
        //setBackground(Color.DARK_GRAY);
        addKeyListener(new TAdapter());
        setFocusable(true);

        inGame = true;
	
        setPreferredSize(new Dimension((int)w, (int)h));   //Set the size of Window     
        timer = new Timer(DELAY, this); //Timer with 10 ms delay 
        timer.start();
    }
    
    
    @Override
    public void paintComponent(Graphics g) //Draws all the components on screen
    {
    	g.setColor(getBackground());		//get the background color
        g.clearRect(0 , 0, (int)w, (int)h);	//clear the entire window
    	Dimension size = getSize();  //get the current window size  	
        w = (int)size.getWidth();
        h = (int)size.getHeight();

        g.setColor(Color.BLACK); //background	
        g.fillRect(0, 0, w, h);

        g.setColor(Color.WHITE);    //player1
        g.fillRect(0, playerY, 25, 80);
        player1Rect = new Rectangle(0,playerY,25,80);

        g.setColor(Color.WHITE);  //player2
        g.fillRect(w-25,player2Y,25,80);
        player2Rect = new Rectangle(w-25,player2Y,25,80);

        g.setColor(Color.WHITE);    //middleLine
        g.drawLine(w/2, 0, w/2, h);

        g.setColor(Color.WHITE);     //Ball
        g.fillOval(ballx, bally, 20, 20);
        ballRect = new Rectangle(ballx, bally, 20, 20);

        Graphics2D g2d = (Graphics2D) g;      //player1 Score
        var font = new Font("Dialog", Font.PLAIN, 65);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString ("" + score, w/2 - 100, 70);

        font = new Font("Dialog", Font.PLAIN, 65); //player2 Score
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString ("" + score2, w/2 + 40, 70);


        Toolkit.getDefaultToolkit().sync();
    }
    
    
    public void actionPerformed(ActionEvent e) {
        step();
        count++;
        //if(count%13==0)
        //score++;
    }
    public void step(){
     if(inGame==true){
        playerMove();
        //player2Y = bally; if wanna play against computer
        ballMove();
        updateScore();
        repaint();
     }
    }
    private class TAdapter extends KeyAdapter 
    {

        @Override
        public void keyReleased(KeyEvent e) 
        {
           if(e.getKeyCode()==KeyEvent.VK_W)
           playerCanMove=false;
           if(e.getKeyCode()==KeyEvent.VK_S)
           playerCanMove=false;

           if(e.getKeyCode()==KeyEvent.VK_UP)
           player2CanMove=false;
           if(e.getKeyCode()==KeyEvent.VK_DOWN)
           player2CanMove=false;
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
           int key = e.getKeyCode();

           if(key == KeyEvent.VK_S){
           playerCanMove=true;
           playerSpeed= 7;
           }
           if(key == KeyEvent.VK_W){
           playerCanMove=true;
           playerSpeed= -7;
           }

           if(key == KeyEvent.VK_DOWN){
            player2CanMove=true;
            playerSpeed= 7;
            }
           if(key == KeyEvent.VK_UP){
            player2CanMove=true;
            playerSpeed= -7;
            }
        }
    }

    private void playerMove(){
     if(playerCanMove==true){
         playerY += playerSpeed; 
         if(playerY<0)
         playerY=0;
         if(playerY>h-80)
         playerY = h-80;
     }

     if(player2CanMove==true){
         player2Y +=playerSpeed;
         if(player2Y<0)
         player2Y=0;
         if(player2Y>h-80)
         player2Y = h-80;
     }
    }

    private void ballMove(){
        ballx +=dx;
        bally +=dy;
        
        //if(ballx>w-40 || ballx<0)
        //dx = -dx;
        if(bally>h-40 || bally<0)
        dy = -dy;  

        if(player1Rect!=null && player1Rect.intersects(ballRect)){
            ballx = ballx + 10;
            dx = -dx;
        }
        if(player2Rect!=null && player2Rect.intersects(ballRect)){
           ballx = ballx - 10;
           dx = -dx;
        }          
    }
    private void updateScore(){
        if(ballx>w+300){
         score +=10; 
         ballx = w/2;
         bally = h/2;
        }
         if(ballx<-300){
         score2 +=10;
         ballx = w/2;
         bally = h/2;
         }

     }
}
