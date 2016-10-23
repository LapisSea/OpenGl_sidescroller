package game.rendering.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import game.util.assetload.ResourceShader;

public abstract class ShaderProgram{
	
	private int id,vsId,fsId;
	
	
	public ShaderProgram(ResourceShader src){
		vsId=loadShader(src.toVertex(), GL20.GL_VERTEX_SHADER);
		fsId=loadShader(src.toFragment(), GL20.GL_FRAGMENT_SHADER);
		id=GL20.glCreateProgram();
		GL20.glAttachShader(id, vsId);
		GL20.glAttachShader(id, fsId);
		GL20.glLinkProgram(id);
		bindAttributes();
		GL20.glValidateProgram(id);
	}
	
	protected abstract void bindAttributes();
	
	private static int loadShader(ResourceShader src, int type){
		int shaderID=GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, src.read());
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
}
