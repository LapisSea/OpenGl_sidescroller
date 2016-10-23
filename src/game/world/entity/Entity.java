package game.world.entity;

import org.lwjgl.util.vector.Vector2f;

import game.rendering.models.TexturedModel;
import game.rendering.shaders.ShaderProgram;

public class Entity{
	
	public TexturedModel model;
	public Vector2f pos,scale;
	public float rotation;
	public ShaderProgram shader;
	
	public Entity(TexturedModel model, Vector2f pos, Vector2f scale, float rotation, ShaderProgram shader){
		this.model=model;
		this.pos=pos;
		this.scale=scale;
		this.rotation=rotation;
		this.shader=shader;
	}
	
	
}
