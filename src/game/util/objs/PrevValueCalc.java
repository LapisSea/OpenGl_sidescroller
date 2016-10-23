package game.util.objs;

import game.util.PartialTickUtil;
import game.util.interf.Calculable;

public class PrevValueCalc<T extends Calculable<T>>{
	
	public T value,prevValue;
	
	public PrevValueCalc(T value){
		this.value=prevValue=value;
	}
	
	public void update(){
		prevValue=value;
	}
	public T calc(){
		return PartialTickUtil.calc(prevValue, value);
	}
}
