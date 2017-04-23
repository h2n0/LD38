package uk.fls.h2n0.main.util;

public class PlanetGen {

	
	
	private final int w,h;
	private Vec2[] vals;
	
	public float[] n;
	
	public PlanetGen(int w, int h){
		this.w = w;
		this.h = h;
		
		this.vals = new Vec2[w * h];
		
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				int rot = (int)(Math.random() * 359);
				Vec2 a = new Vec2(0,0);
				this.vals[x + y * w] = a.rot(new Vec2(0, -1), rot);
			}
		}
		
		this.n = new float[w * h];
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				float n = noise((float)x,(float)y);
				System.out.println(n);
				this.n[x + y * this.w] = n;
			}
		}
	}
	
	public float noise(float x, float y){
		int gx0 = (int)x;
		int gy0 = (int)y;
		int gx1 = gx0 + 1;
		int gy1 = gy0 + 1;
		
		Vec2 g00 = g(gx0, gy0);
		Vec2 g10 = g(gx1, gy0);
		Vec2 g11 = g(gx1, gy1);
		Vec2 g01 = g(gx0, gy1);
		
		Vec2 d00 = new Vec2(x - gx0, y - gy0);
		Vec2 d01 = new Vec2(x - gx1, y - gy0);
		Vec2 d11 = new Vec2(x - gx1, y - gy1);
		Vec2 d10 = new Vec2(x - gx0, y - gy1);
		
		float s = dot(g00, new Vec2(d00.x, d00.y));//Top left
		float t = dot(g10, new Vec2(d10.x, d10.y));//Top right
		float u = dot(g11, new Vec2(d11.x, d11.y));//Bottom right
		float v = dot(g01, new Vec2(d01.x, d01.y));//Bottom left
		
		float sx = weight(d00.x);
		float sy = weight(d00.y);
		
		float a = lerp(sy, s, v);
		float b = lerp(sy, t, u);
		float h = lerp(sx, a, b);
		
		h *= 4;
		
		if(h > 1)h = 1;
		if(h < -1)h = -1;
		return h;
	}
	
	
	//Linear interolation function
	private float lerp(float a, float b, float perc){
		return a + perc * (b - a);
	}
	
	private float weight(float x){
		return 3 * (x * x) - 2 * (x * x);
	}
	
	private float dot(Vec2 a, Vec2 b){
		return (a.x * b.x) + (a.y * b.y);
	}
	
	private Vec2 g(int x, int y){
		if(x < 0)x = 0;
		if(y < 0)y = 0;
		if(x > this.w-1)x = this.w-1;
		if(y > this.w-1)y = this.h-1;
		return this.vals[x + y * this.w];
	}
}
