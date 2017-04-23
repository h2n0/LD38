package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;

public class Cloud extends Building {

	
	private int[] data;
	private float yoff;
	private boolean cos;
	public Cloud(Planet home, Point pos) {
		super(home);
		this.pos = new Point(pos.x, pos.y);
		this.data = LD38.sp.getData(Math.random()>0.5f?18:19);
		this.yoff = (float)Math.random();
		this.cos = Math.random() > 0.5f;
	}
	
	public void render(Renderer r,int off){
		int[] npos = this.home.getOffsets(this.pos);
		if(!this.home.isShowing(this.pos.getIX() + off))return;
		
		r.shadeElipse(npos[0] + 2, npos[1]+12, 3, 4);
		r.renderSection(data, npos[0], npos[1], 8);
	}
	
	public void update(){
		this.pos.x = this.home.rotX(this.pos, 0.25f);
		if(this.cos){
			this.pos.y = (float)Math.cos(Math.toRadians(yoff)) * 40;
		}else{
			this.pos.y = (float)Math.sin(Math.toRadians(yoff)) * 40;
		}
		yoff += 0.05f; //Math.max(0.05f, Math.random() * 0.75f);
	}
	
}
