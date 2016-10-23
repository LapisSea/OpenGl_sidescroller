package game.loading;

import java.io.File;

public class ResourceShader extends Resource{
	
	protected boolean isVertex;
	public ResourceShader(String shaderName){
		this(shaderName,false);
	}
	public ResourceShader(String shaderName,boolean isVertex){
		super("assets/shaders/"+shaderName);
		this.isVertex=isVertex;
	}
	
	@Override
	public File getFile(){
		return new File(localPath+(isVertex?".vs":".fs"));
	}

	public ResourceShader toFragment(){
		this.isVertex=false;
		return this;
	}
	public ResourceShader toVertex(){
		this.isVertex=true;
		return this;
	}
}
