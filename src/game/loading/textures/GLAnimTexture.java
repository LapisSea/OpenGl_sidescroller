package game.loading.textures;

import org.lwjgl.opengl.GL11;

import game.world.World;

public class GLAnimTexture implements IGLTexture{
	
	protected final int ids[];
	protected final World world;
	public GLAnimTexture(World world,int...ids){
		this.ids=ids;
		this.world=world;
	}
	
	@Override
	public int getId(){
		int id=(int)(world.getWorldTime()%ids.length);
		return ids[id];
	}
	
	@Override
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getId());
	}
	
	@Override
	public void dispose(){
		for(int id:ids)GL11.glDeleteTextures(id);
	}
	
}
