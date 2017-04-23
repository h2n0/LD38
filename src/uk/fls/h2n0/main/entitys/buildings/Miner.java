package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;

public class Miner extends Building {

	public Miner(Planet home) {
		super(home);
	}

	public void render(Renderer r, int off) {
		int[] npos = this.home.getOffsets(this.pos);
		if (this.home.isShowing(this.pos.getIX() + off)) {
			int o = -8;
			int state = 0;
			if (Math.abs(this.pos.y) > 30) {
				state += Math.signum(this.pos.y);
			}
			r.renderSection(LD38.sp.getData(6, 4 + state * 2), npos[0] + o, npos[1] + o, 8);
			r.renderSection(LD38.sp.getData(7, 4 + state * 2), npos[0] + 8 + o, npos[1] + o, 8);
			r.renderSection(LD38.sp.getData(7, 5 + state * 2), npos[0] + 8 + o, npos[1] + 8 + o, 8);
			r.renderSection(LD38.sp.getData(6, 5 + state * 2), npos[0] + o, npos[1] + 8 + o, 8);
		}
	}

	public void renderPlacement(Renderer r, int x, int y) {
		int o = -8;
		int state = 0;
		int dy = LD38.h / 2 - y;
		if (Math.abs(dy) > 30) {
			state += Math.signum(-dy);
		}
		r.renderSection(LD38.sp.getData(6, 4 + state * 2), x + o, y + o, 8);
		r.renderSection(LD38.sp.getData(7, 4 + state * 2), x + 8 + o, y + o, 8);
		r.renderSection(LD38.sp.getData(7, 5 + state * 2), x + 8 + o, y + 8 + o, 8);
		r.renderSection(LD38.sp.getData(6, 5 + state * 2), x + o, y + 8 + o, 8);
	}

}
