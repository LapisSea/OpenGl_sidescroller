package game.rendering.shaders.uniform_load;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import game.rendering.shaders.ShaderProgram;
import game.util.MatrixUtil;

public class UniformMat4 extends UniformLoaderObj<Matrix4f>{
	
	private static final FloatBuffer MATRIX_BUFFER=BufferUtils.createFloatBuffer(16);
	
	public UniformMat4(ShaderProgram parent, String name){
		super(parent, name);
	}
	
	@Override
	protected synchronized void send(Matrix4f value){
		value.store(MATRIX_BUFFER);
		MATRIX_BUFFER.flip();
		GL20.glUniformMatrix4(pos, false, MATRIX_BUFFER);
	}
	@Override
	protected boolean shouldSend(){
		return !MatrixUtil.equals(value,lastValue);
	}
	
}
