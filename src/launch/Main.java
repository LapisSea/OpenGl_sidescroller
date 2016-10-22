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
		
		DisplayUtil.create();
		DisplayUtil.update();
		Game game=new Game();
		game.start();
		game.run();
		DisplayUtil.close();
	}
}
