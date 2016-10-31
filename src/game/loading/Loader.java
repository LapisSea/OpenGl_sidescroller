package game.loading;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import game.loading.textures.IGLTexture;
import game.rendering.models.BasicModel;
import game.rendering.models.TexturedModel;
import game.util.LogUtil;

public class Loader {
	
	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();
	private static List<Integer> textures = new ArrayList<>();
	private static final ResourceTexture DEFAULT_IMG=new ResourceTexture("default");
	
	public static TexturedModel loadModel(float[] positions, float[] uvs, int[] indices, IGLTexture texture){
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3/*x,y,z*/, positions);
		storeDataInAttributeList(1, 2/*u,v*/, uvs);
		unbindVAO();
		return new TexturedModel(texture, vaoID, indices.length);
	};
	
	public static int loadTexture(ResourceTexture src){
		Texture texture=null;
		int textureId=0;
		try{
			texture=TextureLoader.getTexture("PNG", src.getStream());
			textureId=texture.getTextureID();
			textures.add(textureId);
			return textureId;
		}catch(FileNotFoundException e){
			LogUtil.println("Texture "+src.getFile()+" is not found!");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			texture=TextureLoader.getTexture("PNG", DEFAULT_IMG.getStream());
			textureId=texture.getTextureID();
			textures.add(textureId);
			return textureId;
		}catch(Exception e){
			LogUtil.println("Probably you have deleted default.png. Please bring it back or suck my dick, bitch.");
			e.printStackTrace();
		}
		System.exit(1);
		return textureId;
	}
	
	public void dispose(){
		for(int vao : vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture : textures){
			GL11.glDeleteTextures(texture);
		}
	}
	
	private static int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	};
	
	public static void updateVBO(int vbo, float[] vboData, FloatBuffer buffer){
		buffer.clear();
		buffer.put(vboData);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity(), GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	public static int createVBO(int maxSize){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, maxSize*4, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}
	
	public static void addPerInstanceAttrs(int vaoId, int vboId, int attribute, int dataSize, 
			int instanceSize, int offset){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL30.glBindVertexArray(vaoId);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, instanceSize*4, offset*4);
		GL33.glVertexAttribDivisor(attribute, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	private static void storeDataInAttributeList(int attribute, int cordinateSize, float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribute, cordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private static void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static void initModelForInstanced(BasicModel model, int vbo, int instanceSize){
		addPerInstanceAttrs(model.getVaoID(), vbo, 2, 4, instanceSize, 0);
		addPerInstanceAttrs(model.getVaoID(), vbo, 3, 4, instanceSize, 4);
		addPerInstanceAttrs(model.getVaoID(), vbo, 4, 4, instanceSize, 8);
		addPerInstanceAttrs(model.getVaoID(), vbo, 5, 4, instanceSize, 12);
	}
}
