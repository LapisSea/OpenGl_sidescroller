package game.util.objs;

import game.util.PartialTickUtil;

public class PrevValue{
	
	public float value,prevValue;
	
	public PrevValue(float value){
		this.value=prevValue=value;
	}
	
	public void update(){
		prevValue=value;
	}
	public float calc(){
		return PartialTickUtil.calc(prevValue, value);
	}
	
}
