package game.loading.textures;

import org.lwjgl.opengl.GL11;

public class GLTexture implements IGLTexture{
	
	protected final int id;
	
	public GLTexture(int id){
		this.id=id;
	}
	
	@Override
	public int getId(){
		return id;
	}
	
	@Override
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getId());
	}
	
	@Override
	public void dispose(){
		GL11.glDeleteTextures(id);
	}
	
}
