package game.loading;

import game.core.Game;
import game.util.assetload.textures.GLTexture;
import game.util.assetload.textures.IGLTexture;

public class ResourceTexture extends Resource{
	
	public ResourceTexture(String imageName){
		super("assets/images/"+imageName+".png");
		
	}
	
	public IGLTexture load(){
		return new GLTexture(Game.get().loader.loadTexture(this));
	}
	
}
