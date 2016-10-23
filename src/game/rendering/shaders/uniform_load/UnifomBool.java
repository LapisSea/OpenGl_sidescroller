package game.rendering.shaders.uniform_load;

import java.util.Objects;

import org.lwjgl.opengl.GL20;

import game.rendering.shaders.ShaderProgram;

public class UnifomBool extends UniformLoader{
	
	protected boolean lastValue,value;
	
	public UnifomBool(ShaderProgram parent, String name){
		super(parent, name);
	}
	
	public void setValue(boolean value){
		this.value=value;
	}
	
	@Override
	public boolean load(){
		if(shouldSend())return false;
		lastValue=value;
		
		GL20.glUniform1f(pos, value?1:0);
		return true;
	}
	
	@Override
	protected boolean shouldSend(){
		return Objects.equals(lastValue, value);
	}
	
}
