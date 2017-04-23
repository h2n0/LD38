package uk.fls.h2n0.main.util;

public class Vec2 {
	
	public float x,y;
	
	public Vec2(){
		this(0,0);
	}
	
	public Vec2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vec2 rot(Vec2 p, float rot){
		float rrot = (float)(rot / 180 * Math.PI);
		
		float x = this.x - p.x;
		float y = this.y - p.y;
		
		float nx = (float)(x * Math.cos(rrot) - y * Math.sin(rrot));
		float ny = (float)(x * Math.sin(rrot) + y * Math.cos(rrot));
		
		nx += p.x;
		ny += p.y;
		
		return new Vec2(nx, ny);
	}
}
