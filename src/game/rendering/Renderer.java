package game.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.core.Game;
import game.loading.ResourceTexture;
import game.loading.textures.IGLTexture;
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
	public TexturedModel quad;
	
	private int vbo;//VBO for storing instances' data
	private static final int MAX_SPRITES_COUNT = 10000;
	private static final int DATA_PER_SPRITE = 16;//4x4 floats for transformation matrix and 4x4 for view 
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_SPRITES_COUNT*DATA_PER_SPRITE);
	private int pointer = 0;
	
	public Renderer(Game game){
		this.game=game;
		game.addResizeListener(this);
	}
	
	public void init(){
		this.vbo = game.loader.createVBO(MAX_SPRITES_COUNT*DATA_PER_SPRITE);
		Models.init(game.loader);
		this.quad = Models.dirt1;
		game.loader.addPerInstanceAttrs(quad.getVaoID(), this.vbo, 2, 4, DATA_PER_SPRITE, 0);
		game.loader.addPerInstanceAttrs(quad.getVaoID(), this.vbo, 3, 4, DATA_PER_SPRITE, 4);
		game.loader.addPerInstanceAttrs(quad.getVaoID(), this.vbo, 4, 4, DATA_PER_SPRITE, 8);
		game.loader.addPerInstanceAttrs(quad.getVaoID(), this.vbo, 5, 4, DATA_PER_SPRITE, 12);
		
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
		pointer = 0;
		float[] vboData = new float[1000];//TODO: Count amount of blocks
		world.iterateChunks(s,e,chunk->{
			for(int x=0;x<Chunk.CHUNK_SIZE;x++){
				for(int y=0;y<Chunk.CHUNK_SIZE;y++){
					Vec2i pos=new Vec2i(x, y);
					Block block=chunk.getBlock(pos);
					updateModelViewMatrix(new Vector3f(pos.getX(), pos.getY(), 0), 0, 1, camera.getTransform(), vboData);
				}
			}
		});
		this.game.loader.updateVBO(vbo, vboData, buffer);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount(), MAX_SPRITES_COUNT);
		finish();
	}
	private void storeMatrixData(Matrix4f matrix, float[] vboData){
		vboData[pointer++] = matrix.m00;
		vboData[pointer++] = matrix.m01;
		vboData[pointer++] = matrix.m02;
		vboData[pointer++] = matrix.m03;
		vboData[pointer++] = matrix.m10;
		vboData[pointer++] = matrix.m11;
		vboData[pointer++] = matrix.m12;
		vboData[pointer++] = matrix.m13;
		vboData[pointer++] = matrix.m20;
		vboData[pointer++] = matrix.m21;
		vboData[pointer++] = matrix.m22;
		vboData[pointer++] = matrix.m23;
		vboData[pointer++] = matrix.m30;
		vboData[pointer++] = matrix.m31;
		vboData[pointer++] = matrix.m32;
		vboData[pointer++] = matrix.m33;
	}
	private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix, float[] vboData){
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix,  modelMatrix, null);
		Matrix4f.rotate((float)Math.toRadians(rotation), new Vector3f(0, 0, 1), modelViewMatrix, modelViewMatrix);
		storeMatrixData(modelViewMatrix, vboData);
	}
	
	private void bindTexture(IGLTexture texture){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
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
		glBindVertexArray(quad.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
		glEnableVertexAttribArray(5);
		
		bindTexture(quad.getTexture());
	}
	public void finish(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
		glDisableVertexAttribArray(5);
	}
	public void render(BasicModel model){
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
	}
	
	public void render(TexturedModel model){
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		
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
