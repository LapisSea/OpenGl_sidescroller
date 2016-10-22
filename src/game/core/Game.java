package game.core;

import org.lwjgl.opengl.Display;

import game.loading.Loader;
import game.rendering.RawModel;
import game.rendering.Renderer;
import game.util.Util;

public class Game{
	
	private boolean paused=false;
	private Timer timer=new Timer(20, 60);
	private Loader loader = new Loader();
	private Renderer renderer=new Renderer();
	private float[] positions = {
		-0.5F, 0.5F, 0,
		-0.5F, -0.5F, 0,
		0.5F, -0.5F, 0,
		0.5F, 0.5F, 0};
	private int[] indices = {
			0, 1, 3,
			3, 1, 2
			};
	private RawModel model = loader.loadToVAO(positions, indices);
	public void start(){
		Runtime.getRuntime().addShutdownHook(new Thread(()->cleanup()));
	}
	
	public void run(){
		
		while(!Display.isCloseRequested()){
			timer.update();
			if(timer.shouldUpdate()){
				if(paused)gameUpdate();
				timer.updateFinish();
			}
			if(timer.shouldRender()){
				renderer.prepare();
				renderer.render(model);
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
	
}
