package uk.fls.h2n0.main.entitys.buildings;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.entitys.Entity;
import uk.fls.h2n0.main.entitys.Planet;
import uk.fls.h2n0.main.entitys.Resource;

public class Building extends Entity{

	public Building(Planet home){
		super(home);
	}
	
	public Resource getResource(){
		return null;
	}
	
	public void renderPlacement(Renderer r, int x, int y){
		
	}
	
	public void place(int x, int y){
		this.pos = new Point(-this.home.getRot() +  x, y);
		this.home.addBuilding(this);
	}
}
