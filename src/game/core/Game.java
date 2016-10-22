package game.core;

import game.rendering.Renderer;
import game.util.Util;

public class Game{
	
	private static Game instance;
	public static Game get(){
		return instance;
	}
	
	private boolean paused=false;
	private Timer timer=new Timer(20, 60);
	private Renderer renderer=new Renderer();
	
	
	public Game(){
		instance=this;
	}
	
	public void start(){
		Runtime.getRuntime().addShutdownHook(new Thread(()->cleanup()));
	}
	
	public void run(){
		
		while(true){
			timer.update();
			if(timer.shouldUpdate()){
				if(paused)gameUpdate();
				timer.updateFinish();
			}
			if(timer.shouldRender()){
				renderer.render();
				DisplayUtil.update();
				timer.renderFinish();
			}
			Util.sleep(0,500000);
		}
		
	}
	
	
	private void gameUpdate(){
		
	}
	
	private void cleanup(){
		
	}
	public float getPartialTick(){
		return timer.getPartialTick();
	}
}
