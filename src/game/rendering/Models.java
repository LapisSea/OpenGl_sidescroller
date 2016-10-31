package game.rendering;

import game.loading.Loader;
import game.loading.ResourceTexture;
import game.rendering.models.TexturedModel;

public class Models{
	
	public static TexturedModel stone1,stone2,dirt1;
	
	
	public static void init(){
		stone1=Loader.loadModel(new float[]{
				0,0,0,
				1,0,0,
				1,1,0,
				0,1,0
		}, new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		}, new int[]{
				0,1,3,
				3,1,2
		}, new ResourceTexture("Stone1").load());
		stone2=Loader.loadModel(new float[]{
				0,0,0,
				1,0,0,
				1,1,0,
				0,1,0
		}, new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		}, new int[]{
				0,1,3,
				3,1,2
		}, new ResourceTexture("Stone2").load());
		dirt1=Loader.loadModel(new float[]{
				0,0,0,
				1,0,0,
				1,1,0,
				0,1,0
		}, new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		}, new int[]{
				0,1,3,
				3,1,2
		}, new ResourceTexture("Dirt1").load());
	}
	
}
