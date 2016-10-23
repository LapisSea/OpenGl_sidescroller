package game.util.objs.vec;

import game.util.interf.Calculable;

public class Vec2i implements Calculable<Vec2i>{
	
	private int x,y;
	
	public Vec2i(int x, int y){
		this.set(x, y);
	}
	
	public int getX(){
		return x;
	}

	public void set(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	public void setX(int x){
		this.x=x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	@Override
	public Vec2i add(float var){
		x+=var;
		y+=var;
		return this;
	}
	
	@Override
	public Vec2i add(Vec2i var){
		x+=var.x;
		y+=var.y;
		return this;
	}
	
	@Override
	public Vec2i div(float var){
		x/=var;
		y/=var;
		return this;
	}
	
	@Override
	public Vec2i div(Vec2i var){
		x/=var.x;
		y/=var.y;
		return this;
	}
	
	@Override
	public Vec2i mul(float var){
		x*=var;
		y*=var;
		return this;
	}
	
	@Override
	public Vec2i mul(Vec2i var){
		x*=var.x;
		y*=var.y;
		return this;
	}
	
	@Override
	public Vec2i sub(float var){
		x-=var;
		y-=var;
		return this;
	}
	
	@Override
	public Vec2i sub(Vec2i var){
		x-=var.x;
		y-=var.y;
		return this;
	}
	
	@Override
	public Vec2i copy(){
		return new Vec2i(getX(), getY());
	}
	
	@Override
	public boolean equals(Object obj){
		return obj instanceof Vec2i&&equals((Vec2i)obj);
	}
	
	public boolean equals(Vec2i obj){
		return obj.x==x&&obj.y==y;
	}
	
	@Override
	public int hashCode(){
		return 31*(31+x)+y;
	}
	
	@Override
	public String toString(){
		return "["+x+", "+y+"]";
	}
}
