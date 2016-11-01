package game.rendering.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import game.loading.ResourceShader;
import game.rendering.Camera;
import game.rendering.shaders.uniform_load.UniformLoader;
import game.rendering.shaders.uniform_load.UniformMat4;
import game.util.LogUtil;

public abstract class ShaderProgram{
	
	private int id,vsId,fsId;
	private final ResourceShader src;
	private UniformMat4 projectionMatLoader,cameraMatLoader;
	
	public ShaderProgram(ResourceShader src){
		this.src=src;
		init();
	}
	private void init(){
		vsId=loadShader(src.toVertex(), GL20.GL_VERTEX_SHADER);
		fsId=loadShader(src.toFragment(), GL20.GL_FRAGMENT_SHADER);
		id=GL20.glCreateProgram();
		GL20.glAttachShader(getId(), vsId);
		GL20.glAttachShader(getId(), fsId);
		GL20.glLinkProgram(getId());
		bindAttributes();
		GL20.glValidateProgram(getId());
		initUniforms();
	}

	protected void initUniforms(){
		projectionMatLoader=makeUniformLoader(UniformMat4.class, "projection");
		cameraMatLoader=makeUniformLoader(UniformMat4.class, "cameraMat");
	}
	
	
	
	protected <T extends UniformLoader>T makeUniformLoader(Class<T> type, String name){
		try{
			return type.getConstructor(ShaderProgram.class,String.class).newInstance(this,name);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public void start(){
		GL20.glUseProgram(getId());
	}
	public void stop(){
		GL20.glUseProgram(0);
	}
	public void dispose(){
		stop();
		GL20.glDetachShader(getId(), vsId);
		GL20.glDetachShader(getId(), fsId);
		GL20.glDeleteShader(vsId);
		GL20.glDeleteShader(fsId);
		GL20.glDeleteProgram(getId());
	}
	
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String name){
		GL20.glBindAttribLocation(getId(), attribute, name);
	}
	@Deprecated
	public void debugReload(){
		dispose();
		init();
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
	public int getId(){
		return id;
	}

	public void updateGlobalValues(Matrix4f aspectRatio, Camera camera){
		projectionMatLoader.setValue(aspectRatio);
		projectionMatLoader.load();
		cameraMatLoader.setValue(camera.getTransform());
		cameraMatLoader.load();
	}
}
