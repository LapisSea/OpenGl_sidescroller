package game.util;

import game.core.Game;

public class PartialTickUtil{
	
	private static float pt(){
		return Game.get().getPartialTick();
	}
	
	public static float calc(float prevValue, float value){
		return prevValue+(value-prevValue)*pt();
	}
}
