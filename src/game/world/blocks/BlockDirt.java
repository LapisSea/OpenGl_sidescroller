package game.world.blocks;

import game.rendering.Models;
import game.rendering.models.TexturedModel;
import game.world.Block;

public class BlockDirt extends Block{
	
	
	@Override
	public TexturedModel getModel(){
		return Models.dirt1;
	}
	
}
