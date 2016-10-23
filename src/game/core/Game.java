package game.core;

import org.lwjgl.opengl.Display;

import game.loading.Loader;
import game.rendering.Renderer;
import game.util.Util;
import game.world.BlockRegistry;
import game.world.World;

public class Game{
	
	private static Game instance;
	public static Game get(){
		return instance;
	}
	
	private boolean paused=false;
	private Timer timer=new Timer(20, 60);
	public Loader loader = new Loader();
	private Renderer renderer=new Renderer(this);
	public final BlockRegistry blocks=new BlockRegistry();
	private boolean cleanedUp=false;
	
	public World world;
	
	public Game(){
		instance=this;
	}
	
	public void start(){
		world=new World();
	}
	
	public void run(){
		
		while(!Display.isCloseRequested()){
			
			timer.update();
			
			
			if(timer.shouldUpdate()){
				
				if(paused)gameUpdate();
				timer.updateFinish();
			}
			
			if(timer.shouldRender()){
				
				renderer.renderScene();
				timer.renderFinish();
				
			}
			
			
			Util.sleep(1);
		}
		
	}
	
	
	private void gameUpdate(){
		world.update();
	}
	
	public void cleanup(){
		if(cleanedUp)return;
		cleanedUp=true;
		
		DisplayUtil.close();
		
	}
	public float getPartialTick(){
		return timer.getPartialTick();
	}

	public void onResize(int windowWidth, int windowHeight){
		
	}
}
