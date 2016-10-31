package game.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import game.core.Game;
import game.loading.textures.IGLTexture;
import game.rendering.models.BasicModel;
import game.rendering.models.TexturedModel;
import game.rendering.shaders.ShaderProgram;
import game.rendering.shaders.StaticShader;
import game.util.MatrixUtil;
import game.util.interf.ResizeListener;
import game.world.World;
import game.world.entity.Entity;

public class Renderer implements ResizeListener{
	
	private int				windowWidth=0,windowHeight;
	private final Game		game;
	private StaticShader	shader;
	private Matrix4f		aspectRatioFixer=new Matrix4f();
	public final Camera camera=new Camera();
	
	private int vbo;//VBO for storing instances' data
	private static final int MAX_DATA_COUNT = 1024;
	private static final int DATA_PER_SPRITE = 16;//4x4 floats for transformation matrix and 4x4 for view 
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_DATA_COUNT*DATA_PER_SPRITE);
	public int pointer = 0;
	
	public Renderer(Game game){
		this.game=game;
		game.addResizeListener(this);
	}
	
	public void init(){
		this.vbo = game.loader.createVBO(MAX_DATA_COUNT*DATA_PER_SPRITE);
		Models.init(game.loader);
		game.loader.initModelForInstanced(Models.dirt1, vbo, DATA_PER_SPRITE);
		game.loader.initModelForInstanced(Models.stone1, vbo, DATA_PER_SPRITE);
		game.loader.initModelForInstanced(Models.stone2, vbo, DATA_PER_SPRITE);
		shader=new StaticShader();
	}
	
	public void renderScene(){
		prepare();
		
		camera.zoom=1F;
		camera.pos.set(-0, -0);
		shader.start();
		
		shader.updateGlobalValues(aspectRatioFixer,camera);
		
		World world=game.world;
		
		Matrix4f[] transform=new Matrix4f[50];
		
		double tim=System.currentTimeMillis()/1000D;
		
		for(int i=0;i<transform.length;i++){
			transform[i]=MatrixUtil.createTransformMat(
					new Vector2f((float)Math.sin((tim+i*2)%(Math.PI*2))*10,(float)Math.cos((tim*2+i/3.5F)%(Math.PI*2))*5), 
					i+(float)((tim*100)%360), 
					new Vector2f(1,1));
		}
		drawInstancedModel(Models.stone1, transform);
		
		
	}
	
	
	public void drawInstancedModel(TexturedModel model, Matrix4f[] transform){
		Matrix4f cam=camera.getTransform();
		float[] vboData=new float[transform.length*16];
		
		pointer = 0;
		for(int i=0;i<transform.length;i++)updateModelViewMatrix(transform[i], cam, vboData);
		this.game.loader.updateVBO(vbo, vboData, buffer);
		
		

		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
		glEnableVertexAttribArray(5);
		
		bindTexture(model.getTexture());
		
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, Models.dirt1.getVertexCount(), MAX_DATA_COUNT);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
		glDisableVertexAttribArray(5);
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
	private void updateModelViewMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix, float[] vboData){
		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix,  modelMatrix, null);
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
	}
	public void finish(){
		
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
