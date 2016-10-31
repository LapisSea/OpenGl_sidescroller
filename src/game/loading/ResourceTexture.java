package game.loading;

import game.loading.textures.GLTexture;
import game.loading.textures.IGLTexture;

public class ResourceTexture extends Resource{
	
	public ResourceTexture(String imageName){
		super("assets/images/"+imageName+".png");
		
	}
	
	public IGLTexture load(){
		return new GLTexture(Loader.loadTexture(this));
	}
	
}
