package uk.fls.h2n0.main.entitys;

import java.util.ArrayList;
import java.util.List;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.buildings.Base;
import uk.fls.h2n0.main.entitys.buildings.Building;
import uk.fls.h2n0.main.entitys.buildings.Miner;
import uk.fls.h2n0.main.entitys.buildings.Water;

public class Planet {

	private int planetColor;
	private float rot;
	private List<Peon> people;
	private List<Building> buildings;
	public Base hb;
	public Point pos;

	private final int radius;
	private int worldTick;

	public Planet() {
		genNewPlanetColor();
		this.radius = 52;
		this.people = new ArrayList<Peon>();
		this.buildings = new ArrayList<Building>();
		this.rot = 0;
		this.pos = new Point(LD38.w / 2, LD38.h / 2);
		
		//addPerson();
		//addPerson(10,10);
		//addPerson(-10,-10);
		
		this.hb = new Base(this);
		this.buildings.add(hb);
		for(int i = 0; i < 3; i++){//Add 3 water 'buildings'
			this.buildings.add(new Water(this));
		}
		
		this.worldTick = 60 * 30;
		
		Point point = hb.getDropOffPoint();
		for(int i = -1; i < 2; i+=2)addPerson(point.getIX() + i * 4, point.getIY(), false);
	}

	public void render(Renderer r) {
		drawBase(r);
		
		
		int irot = this.getRot();
		for(int i = 0; i < this.people.size(); i++){
			if(this.people.get(i).remove)continue;
			this.people.get(i).render(r, irot);
		}
		
		for(int i = 0; i < this.buildings.size(); i++){
			if(this.buildings.get(i).remove)continue;
			this.buildings.get(i).render(r, irot);
		}
		
		//Font.renderString(r, ""+this.getRot(), 8, 8);
	}

	public void update() {
		for(int i = 0; i < this.people.size(); i++){
			this.people.get(i).update();
			if(this.people.get(i).remove){
				this.people.remove(i);
			}
		}
		
		for(int i = 0; i < this.buildings.size(); i++){
			this.buildings.get(i).update();
			if(this.buildings.get(i).remove){
				this.buildings.remove(i);
			}
		}
		
		rot(0.01f);
		
		if(this.worldTick-- == 0){
			this.worldTick = 60 * 15;
			resourceTick();
		}
	}
	
	public void addPerson(){
		this.people.add(new Peon(this, this.hb, 0, 0));
	}
	
	public void addPerson(int x, int y, boolean ill){
		Peon p = new Peon(this, this.hb, x, y);
		p.setIll(ill);
		this.people.add(p);
	}
	
	public void addBuilding(Building b){
		this.buildings.add(b);
	}
	
	public int getNumberOfBuilding(Class<? extends Building> b){
		int res = 0;
		for(int i = 0; i < this.buildings.size(); i++){
			if(this.buildings.get(i).getClass().equals(b))res++;
		}
		return res;
	}

	private void drawBase(Renderer rr) {
		int x = this.pos.getIX();
		int y = this.pos.getIY();
		int r = this.radius;

		for (int xx = -r; xx < r; xx++) {
			for (int yy = -r; yy < r; yy++) {
				int x2 = xx * xx;
				int y2 = yy * yy;
				if (x2 + y2 < r * r) {
					int col = this.planetColor;
					int edge = 12;
					if (x2 + y2 > (r * r) - edge * edge) col = rr.getShadedColor(this.planetColor, 0.5f);
					if(Math.abs(yy) > 40) col = rr.getShadedColor(col, 1.1f);
					rr.setPixel(x + xx, y + yy, col);
				}
			}
		}
	}

	public void drawRotCurve(Renderer rr, int x, int y) {
		int r = this.radius;
		if(getSignedRot() > -90  && getSignedRot() < 90){
			for(int yy = -r; yy < r; yy++){
				//float ox = (float) (Math.sin(getRad(getRealRot()))) * (float)(mr - Math.abs(yy/2f) + Math.sin(radRot/10f)); 
				rr.setPixel(x + getYOnRotCurve(yy, this.rot), y + yy, 0xFF0000);
			}
		}
	}

	public float getSignedRot() {
		float r = this.rot;
		if (r > 180)
			r -= 360;
		return r;
	}
	
	public float getSignedRot(float amt){
		float r = amt;
		if (r > 180)
			r -= 360;
		return r;
	}

	private void genNewPlanetColor() {
		int min = 0xAAAAAAA;
		int max = 0xDDDDDDD;
		this.planetColor = (int) (min + ((max - min) * Math.random()));
	}

	public int getRot() {
		return (int) this.rot;
	}

	public void rot(float amt) {
		this.rot = clampValue(this.rot + amt);
	}
	
	public float clampValue(float amt){
		if(amt < 0){
			amt += 360;
		}else if(amt > 360){
			amt = amt % 360;
		}
		return amt;
	}
	
	public float rotX(Point pos, float amt){
		return clampValue(pos.x + amt);
	}
	
	public int getYOnRotCurve(int y, float rot){
		float ox = (float) (Math.sin(getRad(rot))) * (float)(this.radius - Math.abs(y));
		//float f3 = (float) Math.sin(getRad(rot) + y / this.radius) * -Math.abs(y) * 10f;
		return (int)ox;
	}

	public int[] getOffsets(Point pos) {
		int xx = (int)(pos.x + this.rot);
		float radRot = (float)(getRad(this.rot) / 180 * Math.PI);
		float xo = (float) (Math.sin(getRad(getSignedRot(xx)))) * (float)(this.radius - Math.abs(pos.y/2f) + Math.sin(radRot/10f));
		return new int[]{this.pos.getIX() + (int)xo,this.pos.getIY() + pos.getIY()};
	}
	
	public void setRot(int amt){
		this.rot = 0;
	}

	public boolean isShowing(int x) {
		int r = (int)clampValue(x);
		r = r>180?r-360:r;
		if (r > -90 && r < 90)return true;
		return false;
	}
	
	private float getRad(float deg){
		return (float)(deg / 180 * Math.PI);
	}
	
	public int getHealth(){
		float numPeople = (int)this.people.size();
		float ill = 0;
		for(int i = 0; i < this.people.size(); i++){
			if(this.people.get(i).isIll())ill += 1f;
		}
		
		return 100 - (int)((ill / numPeople) * 100f);
	}
	
	public int getNumberOfHouses(){
		int res = 0;
		/***for(int i = 0; i < this.buildings.size(); i++){
			if(buildings.get(i) instanceof House){
				res++;
			}
		}**/
		return res;
	}
	
	public Building[] getBuildings(){
		Building[] res = new Building[this.buildings.size()];
		for(int i = 0; i < res.length; i++){
			res[i] = this.buildings.get(i);
		}
		return res;
	}
	
	public int getColor(){
		return this.planetColor;
	}
	
	public void resourceTick(){
		int composite = 0;
		for(int i = 0; i < this.buildings.size(); i++){
			if(this.buildings.get(i) instanceof Miner){
				composite += 5;
			}
		}
		this.hb.giveResource(Resource.Composite, composite);
		
		if(!this.hb.takeResource(Resource.Water, this.people.size() * 5)){
			this.hb.decreaseHappiness();
			if(this.hb.getHappiness() < 50){// Sad get ill
				for(int i = 0; i < this.people.size(); i++){
					Peon p = this.people.get(i);
					if(p.isIll())continue;
					if(Math.random() > 0.7f){
						p.setIll(true);
						break;
					}
				}
			}else if(this.hb.getHappiness() >= 80){// Happy have kids
				if(Math.random() > 0.75f){
					for(int i = 0; i < 2; i++)addPerson();
				}else{
					this.addPerson();
				}
			}
		}else{
			this.hb.increaseHappiness();
		}
	}
}
