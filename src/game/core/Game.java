package game.core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import game.loading.Loader;
import game.rendering.Renderer;
import game.util.Util;
import game.util.interf.ResizeListener;
import game.world.BlockRegistry;
import game.world.World;

public class Game{
	
	private static Game instance;
	public static Game get(){
		return instance;
	}
	
	private boolean					paused		=false;
	private Timer					timer		=new Timer(20, 60);
	public Loader					loader		=new Loader();
	private List<ResizeListener>	resizeables	=new ArrayList<>();
	private Renderer				renderer	=new Renderer(this);
	public final BlockRegistry		blocks		=new BlockRegistry();
	private boolean					cleanedUp	=false;
	
	
	
	public World world;
	
	public Game(){
		instance=this;
	}
	
	public void start(){
		world=new World();
		renderer.init();
	}
	
	public void run(){
		
		while(!Display.isCloseRequested()){
			
			timer.update();
			
			
			if(timer.shouldUpdate()){
				
				if(!paused)gameUpdate();
				timer.updateFinish();
			}
			
			if(timer.shouldRender()){
				
				renderer.renderScene();
				timer.renderFinish();
				
			}
			
			
			Util.sleep(1);
		}
		
	}
	public void dispose(){
		if(cleanedUp)return;
		cleanedUp=true;
		
		DisplayUtil.close();
	}
	
	public void addResizeListener(ResizeListener listener){
		resizeables.add(listener);
	}
	
	public void onResize(int x, int y){
		resizeables.forEach(r->r.onResize(x, y));
	}
	
	
	private void gameUpdate(){
		world.update();
	}
	
	public float getPartialTick(){
		return timer.getPartialTick();
	}

}
