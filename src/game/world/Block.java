package game.world;

import game.rendering.models.TexturedModel;
import game.util.registry.IndexableRegisterObject;

public abstract class Block extends IndexableRegisterObject{
	
	public Block(){
		
	}
	
	
	public TexturedModel getModel(){
		return null;
	}
	
	public void onColided(){
		
	}
}
