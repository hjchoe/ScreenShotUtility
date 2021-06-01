package screenshotutil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Struct extends JFrame
{
	public static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	private JMenuBar mb;
	private JMenu m;
	private JMenuItem m1, m2, mExit;
	public Robot r = new Robot();

	public Struct() throws AWTException
	{
	    initUI();
	}
	
	private void initUI() throws AWTException
	{
		getContentPane().setLayout(null);
	
	    setTitle("Screenshot Utility");
        setPreferredSize(new Dimension(d));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultLookAndFeelDecorated(true);
    	setBackground(new Color(0, 0, 0, 0));
    	
    	createMenu();
        setJMenuBar(mb);
        setupActionMenu();
        
        pack();
        setVisible(true);
	}
	
	public void refresh()
	{
		validate();
        repaint();
	}
	
    private void createMenu()
    {
    	this.mb = new JMenuBar();
    	this.m = new JMenu("Menu");
    	this.m1 = new JMenuItem("Whole Screen");
    	this.m2 = new JMenuItem("Select Part of Screen");
    	this.mExit = new JMenuItem("Quit");
        this.m.add(this.m1);
        this.m.add(this.m2);
        this.m.add(this.mExit);
        this.mb.add(m);
    }
    
    private void setupActionMenu()
    {
    	this.m1.addActionListener(new ActionListener()
    	{  
			public void actionPerformed(ActionEvent e)
			{  
	            wholeScreenshot();
			}  
		});
    	
    	this.m2.addActionListener(new ActionListener()
		{
    		public void actionPerformed(ActionEvent e)
    		{
    			selectScreenshot sS = new selectScreenshot();
    		}
		});
    	
    	this.mExit.addActionListener(new ActionListener()
		{
    		public void actionPerformed(ActionEvent e)
    		{
    			System.exit(0);
    		}
		});
    }
    
    private void wholeScreenshot()
    {     	  
        this.setVisible(false);
    	BufferedImage image = r.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
    	this.setVisible(true);
    	
    	previewSave(image);
    }
    
    public void previewSave(BufferedImage img)
    {
    	JPanel previewFrame = new JPanel();
    	previewFrame.setOpaque(true);
    	
        JLabel preview = new JLabel();
        
        int w = img.getWidth();
    	int h = img.getHeight();
        BufferedImage small = new BufferedImage(w / 4, h / 4, BufferedImage.TYPE_INT_RGB);      
        
        Graphics2D g = small.createGraphics();
        g.scale(.25, .25);
        g.drawImage(img, 0, 0, preview);
        
    	preview.setIcon(new ImageIcon(small));
        preview.setSize(w / 4, h / 4);
        preview.setLocation((int) (d.getWidth()/2)-(w/8), (int) (d.getHeight()/2)-(w/8));
        preview.setOpaque(true);
        
        previewFrame.setSize((int) d.getWidth()/4, (int) d.getHeight()/4);
        previewFrame.setLocation((int) (d.getWidth()/2-d.getWidth()/8), (int) (d.getHeight()/2-d.getHeight()/8));
        previewFrame.add(preview);
        
        JButton save = new JButton("Save");
        save.setBackground(Color.BLUE);
        JButton cancel = new JButton("Cancel");
        cancel.setBackground(Color.GRAY);
        
        previewFrame.add(save);
        previewFrame.add(cancel);
        
        this.add(previewFrame);
        refresh();
        
        save.addActionListener(new ActionListener()
    	{  
			public void actionPerformed(ActionEvent e)
			{  
				try
		        {
					int num = 1;
					String save = "Screenshot";
					String date = java.time.LocalDate.now().toString();
					String time = java.time.LocalTime.now().toString().split("\\.")[0].replace(":", ".");
					
					save += " " + date + " at " + time + ".jpg";
					File file = new File(save);
					
					while(file.exists())
					{
					    save += (num++) +".jpg";
					    file = new File(save); 
					}
					ImageIO.write(img, "jpg", file);
				}
		        catch (IOException e1)
				{
					e1.printStackTrace();
				}
		        remove(previewFrame);
		        refresh();
			}  
		});
    	
    	cancel.addActionListener(new ActionListener()
		{
    		public void actionPerformed(ActionEvent e)
    		{
    			remove(previewFrame);
    			refresh();
    		}
		});
    }
}
