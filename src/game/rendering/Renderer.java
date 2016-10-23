package game.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.Display;

import game.core.Game;
import game.loading.ResourceTexture;
import game.rendering.shaders.StaticShader;
import game.util.assetload.textures.IGLTexture;

public class Renderer{
	
	private RawModel model;
	private IGLTexture texture;
	private StaticShader shader;
	
	public void renderScene(Game game){
		if(model==null){
			
			float[] positions={
				-1F,  1F, 0,
				-1F, -1F, 0,
				 1F, -1F, 0,
				 1F,  1F, 0
			};
			int[] indices={
				0, 1, 3,
				3, 1, 2
			};
			try{
				model=game.loader.loadToVAO(positions, indices);
			}catch(Exception e){
				e.printStackTrace();
			}
			texture=new ResourceTexture("YourMum").load();
			shader=new StaticShader();
		}
		prepare();
		if(game.world.getWorldTime()%10==0)shader.debugReload();
		shader.start();
//		texture.bind();
		render(model);
		shader.stop();
	}
	
	public void prepare(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glClearColor(0, 0, 1, 1);
	}
	
	public void render(RawModel model){
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
}
