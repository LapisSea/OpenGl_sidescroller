package launch;

import game.DisplayUtil;

public class Main{
	
	public static void main(String[] args){
		DisplayUtil.create();
		DisplayUtil.update();
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		DisplayUtil.close();
	}
	
}
