package game.util.objs.vec;

import game.util.interf.Calculable;

public class Vec2f implements Calculable<Vec2f>{

	private float x,y;
	
	public Vec2f(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x=x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float y){
		this.y=y;
	}

	@Override
	public Vec2f add(float var){
		x+=var;
		y+=var;
		return this;
	}

	@Override
	public Vec2f add(Vec2f var){
		x+=var.x;
		y+=var.y;
		return this;
	}

	@Override
	public Vec2f div(float var){
		x/=var;
		y/=var;
		return this;
	}

	@Override
	public Vec2f div(Vec2f var){
		x/=var.x;
		y/=var.y;
		return this;
	}

	@Override
	public Vec2f mul(float var){
		x*=var;
		y*=var;
		return this;
	}

	@Override
	public Vec2f mul(Vec2f var){
		x*=var.x;
		y*=var.y;
		return this;
	}

	@Override
	public Vec2f sub(float var){
		x-=var;
		y-=var;
		return this;
	}

	@Override
	public Vec2f sub(Vec2f var){
		x-=var.x;
		y-=var.y;
		return this;
	}

	@Override
	public Vec2f copy(){
		return new Vec2f(getX(), getY());
	}
}
