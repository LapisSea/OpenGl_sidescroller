package launch;

import java.util.HashMap;
import java.util.Map;

import game.core.DisplayUtil;
import game.core.Game;
import game.util.LogUtil;

public class Main{
	
	public static void main(String[] argsRaw){
		
		Map<String,String> args=new HashMap<>();
		for(String argRaw:argsRaw){
			String[] arg=argRaw.split("=");
			args.put(arg[0], arg.length<2?"":arg[1]);
		}
		launch(args);
	}
	
	private static void launch(Map<String,String> args){
		LogUtil.println("Initialization of display");
		DisplayUtil.create();
		LogUtil.println("Initialization of game");
		Game game=new Game();
		game.start();
		LogUtil.println("Game has been successfully initialized");
		game.run();
		game.dispose();
		LogUtil.println("Cyka next time, please");
	}
}
