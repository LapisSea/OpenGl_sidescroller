package game.world.blocks;

import game.rendering.Models;
import game.rendering.models.TexturedModel;
import game.world.Block;

public class BlockStone1 extends Block{
	
	
	@Override
	public TexturedModel getModel(){
		return Models.stone2;
	}
	
}
