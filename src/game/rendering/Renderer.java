package game.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import game.core.Game;
import game.rendering.Camera.Colider;
import game.rendering.models.BasicModel;
import game.rendering.models.TexturedModel;
import game.rendering.shaders.ShaderProgram;
import game.rendering.shaders.StaticShader;
import game.util.MatrixUtil;
import game.util.interf.ResizeListener;
import game.util.objs.vec.Vec2i;
import game.world.Block;
import game.world.Chunk;
import game.world.World;
import game.world.entity.Entity;

public class Renderer implements ResizeListener{
	
	private int				windowWidth=0,windowHeight;
	private final Game		game;
	private Entity			en;
	private Matrix4f		aspectRatioFixer=new Matrix4f();
	public final Camera camera=new Camera();
	
	
	public Renderer(Game game){
		this.game=game;
		game.addResizeListener(this);
	}
	
	public void init(){
		Models.init(game.loader);
		
		StaticShader	shader;
		
		shader=new StaticShader();
		en=new Entity(Models.stone1, new Vector2f(0, 0), new Vector2f(0.5F, 0.5F), 0, shader);
	}
	
	public void renderScene(){
		prepare();
		
		ShaderProgram shader=en.shader;

		camera.zoom=1F;
		camera.pos.set(-0, -0);
		shader.start();
		
		shader.updateGlobalValues(aspectRatioFixer,camera);
		
		World world=game.world;
		
		Colider c=camera.new Colider();
		Vec2i s=new Vec2i((int)Math.floor(c.rect.x/16), (int)Math.floor(c.rect.y/16)),e=new Vec2i((int)Math.ceil(c.rect.getMaxX()/16), (int)Math.ceil(c.rect.getMaxY()/16));
		world.iterateChunks(s,e,chunk->{
			for(int x=0;x<Chunk.CHUNK_SIZE;x++){
				for(int y=0;y<Chunk.CHUNK_SIZE;y++){
					Vec2i pos=new Vec2i(x, y);
					Block block=chunk.getBlock(pos);
					TexturedModel m=block.getModel();
					if(m!=null){
						shader.applyTransform(MatrixUtil.createTransformMat(new Vector2f(x, y), 0, new Vector2f(1, 1)));
						render(m);
					}
				}
			}
		});
		
		
	}
	
	public void prepare(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0, 0, 0, 1);
		if(Display.getWidth()!=windowWidth||Display.getHeight()!=windowHeight){
			windowWidth=Display.getWidth();
			windowHeight=Display.getHeight();
			game.onResize(windowWidth, windowHeight);
			glViewport(0, 0, windowWidth, windowHeight);
		}
	}
	
	public void render(BasicModel model){
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
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
	}
	
	public void render(Entity entity){
		TexturedModel model=entity.model;
		ShaderProgram shader=entity.shader;
		
		shader.start();
		
		shader.updateGlobalValues(aspectRatioFixer,camera);
		
		shader.applyTransform(MatrixUtil.createTransformMat(entity.pos, entity.rotation, entity.scale));
		model.getTexture().bind();
		
		render(model);
	}
	
	@Override
	public void onResize(int x, int y){
		aspectRatioFixer=new Matrix4f();
		
		float aspectRatio=x/(float)y;
		aspectRatioFixer.m00=1;
		aspectRatioFixer.m11=aspectRatio;
	}
}
