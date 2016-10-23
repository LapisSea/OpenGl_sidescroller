package game.world;

import java.util.HashMap;
import java.util.Map;

import game.core.Game;
import game.util.objs.vec.Vec2i;

public class World{
	
	private Map<Vec2i,Chunk> chunks=new HashMap<>();
	private static final Vec2i VEC2I=new Vec2i(0, 0);
	
	public World(){
		
	}

	public Block getBlock(Vec2i pos){
		Chunk ch=getChunkAt(pos);
		
		VEC2I.set(pos.getX()-ch.getX()*Chunk.CHUNK_SIZE, pos.getY()-ch.getY()*Chunk.CHUNK_SIZE);
		
		return ch.getBlock(VEC2I);
	}
	public void setBlock(Vec2i pos, Block block){
		Chunk ch=getChunkAt(pos);
		
		VEC2I.set(pos.getX()-ch.getX()*Chunk.CHUNK_SIZE, pos.getY()-ch.getY()*Chunk.CHUNK_SIZE);
		
		ch.setBlock(VEC2I,(byte)block.getId());
	}
	
	public Chunk getChunkAt(Vec2i pos){
		VEC2I.set(pos.getX()/Chunk.CHUNK_SIZE,pos.getY()/Chunk.CHUNK_SIZE);
		Chunk ch=chunks.get(VEC2I);
		if(ch==null){
			ch=populate(VEC2I);
			chunks.put(VEC2I, ch);
		}
		return ch;
	}
	
	public Chunk populate(Vec2i pos){
		pos=pos.copy();
		Chunk ch=new Chunk(VEC2I.copy());
		
		for(int x=0;x<Chunk.CHUNK_SIZE;x++){
			for(int y=0;y<Chunk.CHUNK_SIZE;y++){
				ch.setBlock(new Vec2i(x, y), (byte)Math.round((Game.get().blocks.getSize()-1)*Math.random()));
			}
		}
		
		return ch;
	}
	
}
