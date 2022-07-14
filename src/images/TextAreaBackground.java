package images;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TextAreaBackground extends JPanel{
	private BufferedImage image;
	String fileName;
	private int width,height;
	
    public TextAreaBackground(String fileName,int width,int height) throws IOException
    {
        this.setOpaque(false);
        this.fileName=fileName;
        this.width=width;
        this.height=height;
        image=ImageIO.read(new File(fileName));
        Dimension size=new Dimension(this.width,this.height);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        this.setSize(size);
        
        }
    
    @Override
    public void paintComponent(Graphics g)
    {
    	 super.paintComponent(g);
        g.drawImage(image,0,0,this.width,this.height,null);
       
       
    }


}
