package game.rendering.shaders;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import game.loading.Loader;
import game.rendering.models.BasicModel;
import game.util.LogUtil;

public class SmartVbo{
	
	private static final int MIN_COUNT=512;
	
	private int id,size,instanceMaximum,dataPerInstance,lesserCount,smallerMax;
	private final List<BasicModel> boundModels=new ArrayList<>();
	private FloatBuffer buffer;
	
	
	public SmartVbo(int dataPerInstance){
		instanceMaximum=MIN_COUNT*2;
		this.dataPerInstance=dataPerInstance;
		resize();
	}
	
	public void checkSize(int required){
		if(required>instanceMaximum){
			do{
				instanceMaximum*=2;
			}while(required>instanceMaximum);
			resize();
		}
		else if(instanceMaximum!=MIN_COUNT&&required<=smallerMax){//not the smallest and has ability to shrink
			lesserCount++;
			if(lesserCount>500)resize();
		}
		
	}
	private void resize(){
		int newSize=dataPerInstance*instanceMaximum;
		if(newSize==size)return;
		smallerMax=Math.max(MIN_COUNT,(size=newSize)/2);
		
		lesserCount=0;
		id=Loader.createVBO(size);
		buffer=BufferUtils.createFloatBuffer(size);
		boundModels.forEach(m->Loader.initModelForInstanced(m, getId(), dataPerInstance));
		LogUtil.println("Smart VBO",getId(),"changed size to:",instanceMaximum);
	}
	
	public int getId(){
		return id;
	}
	
	public void bind(BasicModel... models){
		for(int i=0;i<models.length;i++){
			BasicModel model=models[i];
			boundModels.add(model);
			Loader.initModelForInstanced(model, getId(), dataPerInstance);
		}
	}
	
	public void update(float[] data){
		checkSize(data.length);
		Loader.updateVBO(getId(), data, buffer);
	}
	
}
