package game.core;

import org.lwjgl.opengl.Display;

import game.loading.Loader;
import game.rendering.Renderer;
import game.util.LogUtil;
import game.util.Util;

public class Game{
	
	private static Game instance;
	public static Game get(){
		return instance;
	}
	
	private boolean paused=false;
	private Timer timer=new Timer(20, 60);
	public Loader loader = new Loader();
	private Renderer renderer=new Renderer();
	private boolean cleanedUp=false;
	
	
	public Game(){
		instance=this;
	}
	
	public void start(){
	}
	
	public void run(){
		
		while(!Display.isCloseRequested()){
			
			timer.update();
			
			
			if(timer.shouldUpdate()){
				
				if(paused)gameUpdate();
				timer.updateFinish();
			}
			
			if(timer.shouldRender()){
				
				renderer.renderScene(this);
				timer.renderFinish();
				
			}
			
			
			Util.sleep(1);
		}
		
	}
	
	
	private void gameUpdate(){
		
	}
	
	public void cleanup(){
		if(cleanedUp)return;
		cleanedUp=true;
		
		DisplayUtil.close();
		LogUtil.println("Cyka next time, please");
		
	}
	public float getPartialTick(){
		return timer.getPartialTick();
	}
}
