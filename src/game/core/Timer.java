package game.core;

import game.util.LogUtil;

public class Timer{
	
	private long lastTimeUpdated,lastTimeRendered,/**update period*/up,/**frame period*/fp,lastSec;
	private static final int SECOND=1000000000;
	
	private int update,updates,frames,updatesCount,framesCount;
	private boolean render;
	
	public Timer(int ups, int fps){
		setUPS(ups);
		setFPS(fps);
	}
	
	public void update(){
		long time=time();
		if(time-lastTimeUpdated>=up){
			lastTimeUpdated=time;
			if(update<100)update++;
		}
		time=time();
		if(time-lastTimeRendered>=fp){
			lastTimeRendered=time;
			render=true;
		}
		time=time();
		if(time-lastSec>=SECOND){
			lastSec=time;
			updatesCount=0;
			framesCount=0;
			LogUtil.println("UPS:",updatesCount,"FPS:",framesCount);
		}
	}
	
	private long time(){
		return System.nanoTime();
	}
	
	public void setUPS(int ups){
		up=SECOND/ups;
	}
	public void setFPS(int fps){
		fp=SECOND/fps;
	}
	
	public boolean shouldRender(){
		if(render){
			render=false;
			return true;
		}
		return false;
	}
	public boolean shouldUpdate(){
		if(update>0){
			update--;
			return true;
		}
		return false;
	}
	public void renderFinish(){
		framesCount++;
	}
	public void updateFinish(){
		updatesCount++;
	}
	public int getFps(){
		return frames+1;
	}
	public int getUps(){
		return updates;
	}
}
