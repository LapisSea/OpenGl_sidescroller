package game.world;

import game.core.Game;
import game.util.objs.vec.Vec2i;

public class Chunk{
	
	public static final int CHUNK_SIZE=10;
	
	private final Vec2i pos;
	public final byte[][] data=new byte[CHUNK_SIZE][CHUNK_SIZE];
	
	
	public Chunk(Vec2i pos){
		this.pos=pos;
	}

	public Block getBlock(Vec2i pos){
		if(pos.getX()<0||pos.getY()<0||pos.getX()>=CHUNK_SIZE||pos.getY()>=CHUNK_SIZE)throw new IndexOutOfBoundsException("Pos: "+pos+" is invalid");
		return Game.get().blocks.getByid(data[pos.getX()][pos.getY()]);
	}
	public int getX(){
		return pos.getX();
	}
	public int getY(){
		return pos.getY();
	}

	public void setBlock(Vec2i vec2i, byte id){
		data[pos.getX()][pos.getY()]=id;
	}
}
