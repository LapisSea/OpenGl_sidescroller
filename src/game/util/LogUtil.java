package game.util;


public class LogUtil{
	

	public static void println(Object...objs){
		StringBuilder o=new StringBuilder();
		for(Object obj:objs){
			o.append(obj).append(' ');
		}
		println(o.toString());
	}
	public static void println(Object o){
		println(o.toString());
	}
	public static void println(String s){
		System.out.println(s);
	}
	
}
