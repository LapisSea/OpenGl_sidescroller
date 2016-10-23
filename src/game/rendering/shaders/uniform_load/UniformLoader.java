package game.rendering.shaders.uniform_load;

import org.lwjgl.opengl.GL20;

import game.rendering.shaders.ShaderProgram;

public abstract class UniformLoader{

	protected final int pos;
	
	public UniformLoader(ShaderProgram parent,String name){
		this.pos=GL20.glGetUniformLocation(parent.getId(), name);
	}
	
	public abstract boolean load();
	
	protected abstract boolean shouldSend();
}
