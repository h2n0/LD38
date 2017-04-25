package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;
import uk.fls.h2n0.main.entitys.Resource;

public class Base extends Building{
	
	private int water;
	private int composite;
	private int happiness;
	
	public Base(Planet home){
		super(home);
		float y = (float)(Math.random() * 40);
		y = Math.random()>0.5?-y:y;
		this.pos = new Point(0,y);
		
		this.water = 100;
		this.composite = 300;
		this.happiness = 50;
	}

	
	public void render(Renderer r, int off){
		int[] npos = this.home.getOffsets(this.pos);
		if(this.home.isShowing(this.pos.getIX() + off)){
			int o = -8;
			int state = 1;
			if(Math.abs(this.pos.y) > 35){
				state += Math.signum(this.pos.y);
			}
			r.renderSection(LD38.sp.getData(0, 2 + state * 2), npos[0] + o, npos[1] + o, 8);
			r.renderSection(LD38.sp.getData(1, 2 + state * 2), npos[0] + 8 + o, npos[1] + o, 8);
			r.renderSection(LD38.sp.getData(1, 3 + state * 2), npos[0] + 8 + o, npos[1] + 8 + o, 8);
			r.renderSection(LD38.sp.getData(0, 3 + state * 2), npos[0] + o, npos[1] + 8 + o, 8); 
		}
	}
	
	public void update(){
		
	}
	
	public Point getDropOffPoint(){
		return new Point(this.pos.getIX(), this.pos.getIY() + (this.pos.y > 20 ?-12: 12));
	}
	
	public boolean giveResource(Resource w, int amt){
		if(w == Resource.Water){
			this.water += amt * 10;
			return true;
		}else if(w == Resource.Composite){
			this.composite += amt * 5;
			return true;
		}
		return false;
	}
	
	public boolean takeResource(Resource w, int amt){
		if(w == Resource.Water){
			if(this.water - amt >= 0){
				this.water -= amt;
				return true;
			}
		}else if(w == Resource.Composite){
			if(this.composite - amt >= 0){
				this.composite -= amt;
				return true;
			}
		}
		return false;
	}
	
	public int getWater(){
		return this.water;
	}
	
	public int getComposite(){
		return this.composite;
	}
	
	public int getHappiness(){
		return this.happiness;
	}
	
	public void decreaseHappiness(int healthPerc){
		if(this.happiness == 0)return;
		this.happiness -= (int)(5 + (5 * (100f - (float)healthPerc)));
		if(this.happiness < 0)this.happiness = 0;
	}
	
	public void increaseHappiness(){
		if(this.happiness == 100)return;
		this.happiness += 1 + (this.home.getNumberOfPeons() / 4);
		if(this.happiness > 100)this.happiness = 100;
	}
	
	public void removeAllWater(){
		this.water = 0;
	}
}
