package game.util.assetload;

import org.newdawn.slick.opengl.Texture;

public class ResourceTexture extends Resource{
	
	public ResourceTexture(String localPath){
		super("assets/images/"+localPath+".png");
		
	}
	
	public Texture load(){
		return null;//TODO:Loader.loadImg();
	}
	
	
}
