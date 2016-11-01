package game.rendering.shaders.uniform_load;

import game.rendering.shaders.ShaderProgram;

public abstract class UniformLoaderObj<T> extends UniformLoader{
	
	protected T lastValue,value;
	
	public UniformLoaderObj(ShaderProgram parent, String name){
		super(parent, name);
	}
	
	public void setValue(T value){
		this.value=value;
	}
	
	@Override
	protected boolean shouldSend(){
		return value!=null&&!value.equals(lastValue);
	}
	@Override
	public boolean load(){
		if(!shouldSend())return false;
		lastValue=value;
		send(value);
		return true;
	}
	protected abstract void send(T value);
	
}
