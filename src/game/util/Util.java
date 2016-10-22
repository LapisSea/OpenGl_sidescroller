package game.util;


public class Util{

	public static void sleep(long millis){
		try{
			Thread.sleep(millis);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public static void sleep(long millis, int nano){
		try{
			Thread.sleep(millis,nano);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
}
