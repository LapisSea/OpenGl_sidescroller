package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayUtil{
	
	
	public static void create(){
		try{
			ContextAttribs atrb=new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(1000, 700));
			Display.create(new PixelFormat(), atrb);
		}catch(LWJGLException e){
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, 1000, 700);
	}
	
	public static void update(){
		Display.sync(60);
		Display.update();
	}
	
	public static void close(){
		Display.destroy();
	}
}
