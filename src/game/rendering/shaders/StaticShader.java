package game.rendering.shaders;

import game.loading.ResourceShader;

public class StaticShader extends ShaderProgram{

	public StaticShader(){
		super(new ResourceShader("StaticShader"));
	}

	@Override
	protected void bindAttributes(){
		bindAttribute(0, "positions");
	}

}
