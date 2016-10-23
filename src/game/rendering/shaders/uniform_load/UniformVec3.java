package game.rendering.shaders.uniform_load;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

import game.rendering.shaders.ShaderProgram;

public class UniformVec3 extends UniformLoaderObj<Vector3f>{
	
	public UniformVec3(ShaderProgram parent, String name){
		super(parent, name);
	}
	
	@Override
	protected void send(Vector3f value){
		GL20.glUniform3f(pos, value.x, value.y, value.z);
	}
	
}
