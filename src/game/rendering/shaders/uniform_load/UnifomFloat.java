package game.rendering.shaders.uniform_load;

import java.util.Objects;

import org.lwjgl.opengl.GL20;

import game.rendering.shaders.ShaderProgram;

public class UnifomFloat extends UniformLoader{
	
	protected float lastValue,value;
	
	public UnifomFloat(ShaderProgram parent, String name){
		super(parent, name);
	}
	
	public void setValue(float value){
		this.value=value;
	}
	
	@Override
	public boolean load(){
		if(shouldSend())return false;
		lastValue=value;
		
		GL20.glUniform1f(pos, value);
		return true;
	}
	@Override
	protected boolean shouldSend(){
		return Objects.equals(lastValue, value);
	}
	
	
}
