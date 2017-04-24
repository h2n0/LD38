package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;

public class Rocket extends Building{

	private boolean takenOff;
	private int peons;
	private int countDown;
	
	public Rocket(Planet home) {
		super(home);
		this.countDown = 60 * 30;
	}
	
	
	public void render(Renderer r, int off) {
		int[] npos = this.home.getOffsets(this.pos);
		if (this.home.isShowing(this.pos.getIX() + off) || takenOff) {
			int o = -8;
			int state = 0;
			if (Math.abs(this.pos.y) > 30) {
				state += Math.signum(this.pos.y);
			}
			
			if(state == 0){
				o /= 2;
				r.renderSection(LD38.sp.getData(2, 3), npos[0] + o, npos[1] + o, 8);
			}else{
				state = -state;
				int s = state==1?3:2;
				o /= 2;
				r.renderSection(LD38.sp.getData(s, 6), npos[0] + o, npos[1] + o, 8);
				r.renderSection(LD38.sp.getData(s, 7), npos[0] + o, npos[1] + o + 8, 8);
			}
		}
	}
	
	public void update(){
		if(this.countDown > 0)this.countDown--;
		else{
			takenOff = true;
			this.pos.y -= 0.5f;
			
			if(this.pos.y < -LD38.h)this.remove = true;
		}
	}

	public void renderPlacement(Renderer r, int x, int y) {
		int o = -8;
		int state = 0;
		int dy = LD38.h / 2 - y;
		if (Math.abs(dy) > 30) {
			state += Math.signum(-dy);
		}
		
		if(state == 0){
			o /= 2;
			r.renderSection(LD38.sp.getData(2, 3), x + o, y + o, 8);
		}else{
			state = -state;
			int s = state==1?3:2;
			o /= 2;
			r.renderSection(LD38.sp.getData(s, 6), x + o, y + o, 8);
			r.renderSection(LD38.sp.getData(s, 7), x + o, y + o + 8, 8);
		}
	}
	
	public int getNumberOfPeons(){
		return this.peons;
	}
	
	public void addPeon(){
		if(takenOff)return;
		this.peons ++;
	}
	
	public boolean takenOff(){
		return this.takenOff;
	}

}
