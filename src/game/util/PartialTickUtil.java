package game.util;

import game.core.Game;
import game.util.interf.Calculable;

public class PartialTickUtil{
	
	private static float pt(){
		return Game.get().getPartialTick();
	}

	public static float calc(float prevValue, float value){
		return prevValue+(value-prevValue)*pt();
	}
	public static <T extends Calculable<T>>T calc(T prevValue, T value){
		return prevValue.copy().add(value.copy().sub(prevValue).mul(pt()));
	}
}
