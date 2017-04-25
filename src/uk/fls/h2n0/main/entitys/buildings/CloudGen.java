package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;

public class CloudGen extends Building {

	private int timer;
	
	public CloudGen(Planet home) {
		super(home);
		this.timer = 0;
	}

	public void render(Renderer r, int off) {
		int[] npos = this.home.getOffsets(this.pos);
		if (this.home.isShowing(this.pos.getIX() + off)) {
			int o = -8;
			int state = 0;
			if (Math.abs(this.pos.y) > 30) {
				state += Math.signum(this.pos.y);
			}
			r.renderSection(LD38.sp.getData(4, 4 + state * 2), npos[0] + o, npos[1] + o, 8);
			r.renderSection(LD38.sp.getData(5, 4 + state * 2), npos[0] + 8 + o, npos[1] + o, 8);
			r.renderSection(LD38.sp.getData(5, 5 + state * 2), npos[0] + 8 + o, npos[1] + 8 + o, 8);
			r.renderSection(LD38.sp.getData(4, 5 + state * 2), npos[0] + o, npos[1] + 8 + o, 8);
		}
	}
	
	public void update(){
		if(this.timer > 60 * 15){
			if( this.home.getNumberOfBuilding(Water.class) + this.home.getNumberOfBuilding(Cloud.class) < 5 +this.home.getNumberOfPeons()){
				this.home.addBuilding(new Cloud(this.home, this.pos));
				this.timer = 0;
			}
		}else{
			this.timer ++;
		}
	}

	public void renderPlacement(Renderer r, int x, int y) {
		int o = -8;
		int state = 0;
		int dy = LD38.h / 2 - y;
		if (Math.abs(dy) > 30) {
			state += Math.signum(-dy);
		}
		r.renderSection(LD38.sp.getData(4, 4 + state * 2), x + o, y + o, 8);
		r.renderSection(LD38.sp.getData(5, 4 + state * 2), x + 8 + o, y + o, 8);
		r.renderSection(LD38.sp.getData(5, 5 + state * 2), x + 8 + o, y + 8 + o, 8);
		r.renderSection(LD38.sp.getData(4, 5 + state * 2), x + o, y + 8 + o, 8);
	}

}
