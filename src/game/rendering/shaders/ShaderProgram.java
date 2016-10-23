package game.rendering.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import game.loading.ResourceShader;
import game.util.LogUtil;

public abstract class ShaderProgram{
	
	private int id,vsId,fsId;
	private final ResourceShader src;
	
	public ShaderProgram(ResourceShader src){
		this.src=src;
		init();
	}
	private void init(){
		vsId=loadShader(src.toVertex(), GL20.GL_VERTEX_SHADER);
		fsId=loadShader(src.toFragment(), GL20.GL_FRAGMENT_SHADER);
		id=GL20.glCreateProgram();
		GL20.glAttachShader(id, vsId);
		GL20.glAttachShader(id, fsId);
		GL20.glLinkProgram(id);
		bindAttributes();
		GL20.glValidateProgram(id);		
	}
	
	@Deprecated
	public void debugReload(){
		dispose();
		init();
	}
	
	public void start(){
		GL20.glUseProgram(id);
	}
	public void stop(){
		GL20.glUseProgram(0);
	}
	public void dispose(){
		stop();
		GL20.glDetachShader(id, vsId);
		GL20.glDetachShader(id, fsId);
		GL20.glDeleteShader(vsId);
		GL20.glDeleteShader(fsId);
		GL20.glDeleteProgram(id);
	}
	
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String name){
		GL20.glBindAttribLocation(id, attribute, name);
	}
	
	private static int loadShader(ResourceShader src, int type){
		int shaderID=GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, src.read());
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE){
			LogUtil.println(GL20.glGetShaderInfoLog(shaderID, 500));
			LogUtil.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
}
