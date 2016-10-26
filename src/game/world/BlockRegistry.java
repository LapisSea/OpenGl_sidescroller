package game.world;

import game.util.registry.TypeRegistryIndexable;
import game.world.blocks.BlockAir;
import game.world.blocks.BlockDirt;
import game.world.blocks.BlockStone1;
import game.world.blocks.BlockStone2;

public class BlockRegistry extends TypeRegistryIndexable<Block>{
	
	
	public BlockRegistry(){
		register(new BlockAir());
		register(new BlockStone1());
		register(new BlockStone2());
		register(new BlockDirt());
		
	}
}
