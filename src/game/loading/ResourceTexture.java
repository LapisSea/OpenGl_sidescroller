package game.loading;

import game.core.Game;
import game.loading.textures.GLTexture;
import game.loading.textures.IGLTexture;

public class ResourceTexture extends Resource{
	
	public ResourceTexture(String imageName){
		super("assets/images/"+imageName+".png");
		
	}
	
	public IGLTexture load(){
		return new GLTexture(Game.get().loader.loadTexture(this));
	}
	
}
