package game.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector2f;

import game.core.Game;
import game.loading.ResourceTexture;
import game.loading.textures.IGLTexture;
import game.rendering.models.BasicModel;
import game.rendering.models.TexturedModel;
import game.rendering.shaders.StaticShader;
import game.util.MatrixUtil;
import game.world.entity.Entity;

public class Renderer{
	
	private TexturedModel model;
	private IGLTexture texture;
	private StaticShader shader;
	private int windowWidth=0,windowHeight;
	private final Game game;
	private Entity en;
	
	public Renderer(Game game){
		this.game=game;
	}
	
	public void init(){
		float[] positions={
			-1F,  1F, 0,
			-1F, -1F, 0,
			 1F, -1F, 0,
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
		en=new Entity(model, new Vector2f(0.4F,0), new Vector2f(0.5F,0.5F), 0, shader);
	}
	
	public void renderScene(){
		prepare();
		en.rotation+=5;
		double time=System.currentTimeMillis()/100D;
		en.scale.x=(float)(0.25+Math.sin(time)*0.15);
		en.scale.y=(float)(0.25+Math.cos(time)*0.15);
		render(en);
		time*=1.2;
		en.scale.x=(float)(0.25+Math.cos(time)*0.15);
		en.scale.y=(float)(0.25+Math.sin(time)*0.15);
		en.rotation*=-1;
		en.pos.x*=-1;
		render(en);
		en.pos.x*=-1;
		en.rotation*=-1;
	}
	
	public void prepare(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		glClearColor(0, 0, 0, 1);
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
	public void render(Entity entity){
		TexturedModel model=entity.model;
		entity.shader.start();
		entity.shader.applyTransform(MatrixUtil.createTransformMat(entity.pos, entity.rotation, entity.scale));
		model.getTexture().bind();
		
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
