package game.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import game.core.Game;
import game.loading.ResourceTexture;
import game.loading.textures.IGLTexture;
import game.rendering.models.BasicModel;
import game.rendering.models.TexturedModel;
import game.rendering.shaders.StaticShader;

public class Renderer{
	
	private TexturedModel model;
	private IGLTexture texture;
	private StaticShader shader;
	private int windowWidth=0,windowHeight;
	private final Game game;
	
	public Renderer(Game game){
		this.game=game;
	}
	
	public void renderScene(){
		if(model==null){
			
			float[] positions={
				-1F,  0.5F, 0,
				-0F, -1F, 0,
				 0.5F, -1F, 0,
				 1F,  1F, 0
			},uvs={
				0,0,
				0,1,
				1,1,
				1,0,
			};
			int[] indices={
				0, 1, 3,
				3, 1, 2
			};
			texture=new ResourceTexture("YourMum").load();
			try{
				model=game.loader.loadToVAO(positions, uvs, indices,texture);
			}catch(Exception e){
				e.printStackTrace();
			}
			shader=new StaticShader();
		}
		prepare();
//		if(game.world.getWorldTime()%10==0)shader.debugReload();
		shader.start();
		texture.bind();
//		texture.bind();
		render(model);
		shader.stop();
	}
	
	public void prepare(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		glClearColor(0, 0, 1, 1);
		if(Display.getWidth()!=windowWidth||Display.getHeight()!=windowHeight){
			windowWidth=Display.getWidth();
			windowHeight=Display.getHeight();
			game.onResize(windowWidth,windowHeight);
			glViewport(0, 0, windowWidth, windowHeight);
		}
	}

	public void render(BasicModel model){
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	public void render(TexturedModel model){
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
}
