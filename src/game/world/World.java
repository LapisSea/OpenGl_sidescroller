package game.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import game.util.Noise;
import game.util.objs.vec.Vec2i;

public class World{
	
	private Map<Vec2i,Chunk> chunks=new HashMap<>();
	private static final Vec2i VEC2I=new Vec2i(0, 0);
	private long worldTime;
	public final Random rand=new Random(1);
	
	public World(){
		
	}
	
	public void update(){
		worldTime++;
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
		return getExact(VEC2I);
	}
	public Chunk getExact(Vec2i pos){

		VEC2I.set(pos.getX(),pos.getY());
		Chunk ch=chunks.get(VEC2I);
		if(ch==null){
			ch=populate(VEC2I);
			chunks.put(VEC2I, ch);
		}
		return ch;
	}
	
	public Chunk populate(Vec2i pos){
		pos=pos.copy();
		Chunk ch=new Chunk(pos);
		
		float[][] level=new float[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
		
		//gen random values
		for(int x=0;x<Chunk.CHUNK_SIZE;x++){
			for(int y=0;y<Chunk.CHUNK_SIZE;y++){
				level[x][y]=0.5F+Noise.Generate(x+pos.getX()*Chunk.CHUNK_SIZE, y+pos.getY()*Chunk.CHUNK_SIZE)/2;
			}
		}
		//create blur effect
		int blurRad=0;
		int blurDiv=(int)Math.pow(blurRad*2, 2);
		if(blurRad>0){
			for(int x=0;x<Chunk.CHUNK_SIZE;x++){
				for(int y=0;y<Chunk.CHUNK_SIZE;y++){
					float sum=0;
					for(int x1=Math.max(0, x-blurRad),x2=Math.min(Chunk.CHUNK_SIZE, x+blurRad);x1<x2;x1++){
						for(int y1=Math.max(0, y-blurRad),y2=Math.min(Chunk.CHUNK_SIZE, y+blurRad);y1<y2;y1++){
							sum+=level[x1][y1];
						}
					}
					level[x][y]=sum/blurDiv;
					
				}
			}
		}
		
		for(int x=0;x<Chunk.CHUNK_SIZE;x++){
			for(int y=0;y<Chunk.CHUNK_SIZE;y++){
				byte id=(byte)(level[x][y]*2);
				if(id==1&&rand.nextBoolean())id++;
				ch.data[x][y]=id;
			}
		}
		for(int x=0;x<Chunk.CHUNK_SIZE;x++){
			boolean lastAir=false;
			for(int y=Chunk.CHUNK_SIZE-1;y>=0;y--){
				int id=ch.data[x][y];
				if(id==0)lastAir=true;
				else if(id!=0){
					if(lastAir){
						lastAir=false;
						ch.data[x][y]=3;
					}
				}
//				ch.data[x][y]=(byte)RandUtil.rand.nextInt(Game.get().blocks.getSize());
			}
		}
		
		return ch;
	}
	
	public long getWorldTime(){
		return worldTime;
	}

	public void setWorldTime(long worldTime){
		this.worldTime = worldTime;
	}

	public void iterateChunks(Vec2i start, Vec2i end, Consumer<Chunk> hook){
		if(start.getX()>=end.getX()||start.getY()>=end.getY())return;
		
		for(int x=start.getX();x<=end.getX();x++){
			for(int y=start.getY();y<=end.getY();y++){
				VEC2I.set(x, y);
				hook.accept(getExact(VEC2I));
			}
		}
		
	}
	
}
