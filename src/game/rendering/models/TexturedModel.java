package game.rendering.models;

import game.loading.textures.IGLTexture;

public class TexturedModel extends BasicModel{
	
	private IGLTexture texture;
	
	public TexturedModel(IGLTexture texture, int vaoId, int vertexCount){
		super(vaoId, vertexCount);
		this.setTexture(texture);
	}

	public IGLTexture getTexture(){
		return texture;
	}

	public void setTexture(IGLTexture texture){
		this.texture = texture;
	}
	
	
}
