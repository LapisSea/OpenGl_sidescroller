package game.util.assetload;

import java.io.File;

public class ResourceShader extends Resource{
	
	protected boolean isVertex;
	public ResourceShader(String localPath){
		this(localPath,false);
	}
	public ResourceShader(String localPath,boolean isVertex){
		super("assets/shaders/"+localPath);
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
		this.isVertex=false;
		return this;
	}
}
