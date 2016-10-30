package game.rendering.shaders;

import game.loading.ResourceShader;

public class StaticShader extends ShaderProgram{
	protected int uTransform;
	
	public StaticShader(){
		super(new ResourceShader("StaticShader"));
	}

	@Override
	protected void bindAttributes(){
		bindAttribute(0, "position");
		bindAttribute(1, "uvIn");
		bindAttribute(2, "viewMat");
		bindAttribute(6, "transform");
	}
	
	@Override
	protected void initUniforms(){
		super.initUniforms();
	}

}
