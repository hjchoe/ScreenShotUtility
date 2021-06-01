package screenshotutil;

import java.awt.AWTException;

public class Main
{
	static Struct fr;
	
	public static void main(String[] args) throws AWTException
    {
    	fr = new Struct();
    }
	
	public static Struct getFrame()
	{
		return fr;
	}
}
