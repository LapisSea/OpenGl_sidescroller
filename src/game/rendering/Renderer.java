package game.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import game.core.Game;

public class Renderer{
	
	private RawModel model;
	
	public void renderScene(Game game){
		if(model==null){
			
			float[] positions={
				-0.5F,  0.5F, 0,
				-0.5F, -0.5F, 0,
				 0.5F, -0.5F, 0,
				 0.5F,  0.5F, 0
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
		}
		prepare();
		render(model);
	}
	
	public void prepare(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
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
