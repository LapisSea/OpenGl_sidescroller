package game.world;

import game.util.objs.vec.Vec2i;

public class Chunk{
	
	public static final int CHUNK_SIZE=32;
	
	private final Vec2i pos;
	private final byte[] data=new byte[CHUNK_SIZE*CHUNK_SIZE];
	
	
	public Chunk(Vec2i pos){
		this.pos=pos;
	}
	
	public byte getBlock(Vec2i pos){
		if(pos.getX()<0||pos.getY()<0||pos.getX()>31||pos.getY()>31)throw new IndexOutOfBoundsException("Pos: "+pos+" is invalid");
		return data[pos.getX()+pos.getY()*CHUNK_SIZE];
	}
	
}
