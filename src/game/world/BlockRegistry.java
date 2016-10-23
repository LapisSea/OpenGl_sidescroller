package game.world;

import game.util.registry.TypeRegistryIndexable;
import game.world.blocks.BlockAir;
import game.world.blocks.BlockBasic;

public class BlockRegistry extends TypeRegistryIndexable<Block>{
	
	
	public BlockRegistry(){
		register(new BlockAir());
		register(new BlockBasic());
		
	}
}
