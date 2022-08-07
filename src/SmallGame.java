import java.awt.Button;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JTextField;

import ucigame.*;

public class SmallGame extends Ucigame
{
    Sprite maze;
    Sprite square;
    Sprite goal;
    int speed;
    boolean won;

    public void setup()
    {
		//initial settings
        window.size(300, 300);
        window.title("SmallGame");
        framerate(60);
        window.showFPS();
        canvas.background(0, 255, 0);
        won = false;
        maze = makeSprite(getImage("1.png", 255));
        square = makeSprite(getImage("2.png", 255));
        goal = makeSprite(getImage("3.png", 255));
        keyboard.typematicOn();
        speed = 2;
        maze.position(0, 0);
        square.position(0, 0);
        goal.position(137, 132);
    }

    public void draw()
    {
		//continuously redraw if the game has not been won
    	if (won)
    	{
    	}else
    	{
	        canvas.clear();
	        if (square.x() > 136 && square.x() < 164)
	        {
	        	if (square.y() > 124 && square.y() < 172)
	        	{
	        		win();
	        	}
	        }
	        goal.draw();
	        maze.draw();
	        square.draw();
    	}
    }

	//implement movement and other functions
    public void onKeyPress()
    {
    	if (!won)
    	{
	        if (keyboard.isDown(keyboard.UP) && square.y() > 0)
	        {
	            square.position(square.x(), square.y()-speed);
	            square.stopIfCollidesWith(maze, PIXELPERFECT);
	            if (square.collided())
	            {
	            	square.position(square.x(), square.y()+speed);
	            }
	        }
	        if (keyboard.isDown(keyboard.DOWN) && square.y() < 286)
	        {
	            square.position(square.x(), square.y()+speed);
	            square.stopIfCollidesWith(maze, PIXELPERFECT);
	            if (square.collided())
	            {
	            	square.position(square.x(), square.y()-speed);
	            }
	        }
	        if (keyboard.isDown(keyboard.LEFT) && square.x() > 0)
	        {
	        	square.position(square.x()-speed, square.y());
	        	square.stopIfCollidesWith(maze, PIXELPERFECT);
	            if (square.collided())
	            {
	            	square.position(square.x()+speed, square.y());
	            }
	        }
	        if (keyboard.isDown(keyboard.RIGHT) && square.x() < 286)
	        {
	        	square.position(square.x()+speed, square.y());
	        	square.stopIfCollidesWith(maze, PIXELPERFECT);
	            if (square.collided())
	            {
	            	square.position(square.x()-speed, square.y());
	            }
	        }
	        square.stopIfCollidesWith(maze, PIXELPERFECT);

			//adjust movement speed
	        if (keyboard.isDown(keyboard.PAGE_UP))
	        {
	            speed = 4;
	        }
	        if (keyboard.isDown(keyboard.PAGE_DOWN))
	        {
	            speed = 2;
	        }
    	}
    }

    //win method
    public void win()
    {
    	won = true;

    	final JDialog jd = new JDialog();
    	jd.setVisible(true);
    	jd.setPreferredSize(new Dimension(175, 50));
    	jd.setTitle("Congratulations!");

    	Button b = new Button("New Game");

    	b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				//restart game upon win
				boolean b = true;
    			while (b)
    			{
    				Random r = new Random();
    				square.position(r.nextInt(286), r.nextInt(286));
    				square.stopIfCollidesWith(maze, PIXELPERFECT);
    	            if (!square.collided())
    	            {
    	            	b = false;
    	            }
    			}
    			won = false;
    			jd.setVisible(false);
    			

			}});

    	jd.add(b);
    	jd.setResizable(false);

    	//adjust pop-out win box location to center of screen
    	final Toolkit toolkit = Toolkit.getDefaultToolkit();
    	final Dimension screen = toolkit.getScreenSize();
    	final int x = (screen.width - jd.getWidth()) / 2;
    	final int y = (screen.height - jd.getHeight()) / 2;
    	jd.setLocation(x, y);
    	jd.setVisible(true);
    	jd.pack();
    }
}
