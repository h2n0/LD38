package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;
import uk.fls.h2n0.main.entitys.Resource;

public class Water extends Building{
	
	private int amt = 0;
	private int fade;
	
	public Water(Planet home){
		super(home);
		this.amt = 5;
		this.fade = 60;
		
		int ax = (int)(Math.random() * 359);
		this.pos = new Point(ax,0);
	}
	
	public void render(Renderer r, int off){
		int[] npos = this.home.getOffsets(this.pos);
		if(this.home.isShowing(this.pos.getIX() + off)){
			int o = -8;
			int drawColor = hasWater()?0x0000CC:r.getShadedColor(this.home.getColor(), 0.5f);
			drawTile(r, LD38.sp.getData(2, 4), npos[0] + o, npos[1] + o, drawColor);
			drawTile(r, LD38.sp.getData(3, 4), npos[0] + o + 8, npos[1] + o, drawColor);
			drawTile(r, LD38.sp.getData(3, 5), npos[0] + o + 8, npos[1] + o + 8, drawColor);
			drawTile(r, LD38.sp.getData(2, 5), npos[0] + o, npos[1] + o + 8, drawColor);
		}
	}
	
	public void update(){
		if(!this.hasWater()){
			this.fade--;
			if(this.fade == 0){
				this.remove = true;
			}
		}
	}
	
	public void drawTile(Renderer r, int[] d,int x, int y,  int col){
		for(int i = 0; i < 8 * 8; i++){
			if(d[i] == -1)continue;
			int tx = i % 8;
			int ty = i / 8;
			r.setPixel(x + tx, y + ty, d[i] & col);
		}
	}
	
	public boolean hasWater(){
		return this.amt > 0;
	}
	
	public Resource getResource(){
		this.amt--;
		if(amt == 0)return null;
		return Resource.Water;
	}
}
