package uk.fls.h2n0.main.entitys;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.entitys.buildings.Base;
import uk.fls.h2n0.main.entitys.buildings.Building;
import uk.fls.h2n0.main.entitys.buildings.Rocket;
import uk.fls.h2n0.main.entitys.buildings.Water;

public class Peon extends Entity{
	
	private boolean ill;
	private Slot carry;
	private Point targetPos;
	private Building targetJob;
	private Base homeBase;
	private final float speed = 0.1f;
	
	private byte actionCool;
	
	public Peon(Planet home, Base homeBase, int x,int y){
		super(home);
		this.homeBase = homeBase;
		this.pos = new Point(x,y);
		this.ill = Math.random() > 0.5;
		this.carry = new Slot(1);
		this.actionCool = 60;
		
		this.targetJob = null;
		this.targetPos = null;
	}
	
	public void render(Renderer r, int off){
		int[] npos = this.home.getOffsets(this.pos);
		if(!this.home.isShowing(this.pos.getIX() + off))return;
		int col = 0xE88E38;
		int scol = 0x00EE00;
		
		for(int i = 0; i < 2; i++){//Body
			r.setPixel(npos[0], npos[1] + i, ill?scol:col);
		}
		
		for(int i = -1; i < 2; i+=2){//Arms
			r.setPixel(npos[0] + i, npos[1] - (this.carry.full?1:0), col);
		}
		
		if(this.carry.full){//Carrying
			r.setPixel(npos[0], npos[1]-1, this.carry.type.getColor());
		}
	}
	
	public void setIll(boolean ill){
		this.ill = ill;
	}
	
	public void update(){// AI things live here urgh...
		
		if(this.carry.full){// Need to head back to base because it is full
			if(getDist(this.homeBase.pos) < 18 * 18){// Drop off resource
				if(this.actionCool-- <= 0){
					if(this.homeBase.giveResource(this.carry.type, 1)){
						if(this.carry.removeResource(1)){
							if(!this.carry.full){
								this.targetJob = null;
								this.targetPos = null;
							}
							this.actionCool = 60;
						}
					}
				}
			}else{// Walk back to base
				float[] m = getMotionToTarget(this.homeBase.pos);
				this.pos.x += m[0];
				this.pos.y += m[1];
			}
		}else{// Go look for or be assigned what to look for
			if(this.targetPos != null){//Has job just needs to fill their inventory
				if(getDist(this.targetPos) < 18 * 18){// 17px or closer
					
					if(!(this.targetJob instanceof Rocket)){
						if(this.actionCool == 0){
							Resource res = this.targetJob.getResource();
							if(res == null){
								this.targetPos = null;
							}else{
								this.carry.addResource(res, 1);
								this.actionCool = 60;
							}
						}
						
						if(this.actionCool > 0){
							this.actionCool --;
						}
					}else{
						if(this.actionCool == 0){
							this.remove = true;
							((Rocket)this.targetJob).addPeon();
						}
					}
				}else{
					float[] m = getMotionToTarget(targetPos);
					this.pos.x += m[0];
					this.pos.y += m[1];
					
					if(this.targetJob instanceof Rocket){
						Rocket ro = (Rocket)this.targetJob;
						if(ro.takenOff()){
							this.targetJob = null;
							this.targetPos = null;
						}
					}
				}
			}else{// Looking for a new job
				Class<?> jobType = Math.random()>0.5?Water.class: null;
				if(this.ill){//Happiness is low so find water to make it better. Very urgent task
					jobType = Water.class;
					if(this.home.getNumberOfBuilding(Rocket.class) > 0){
						Building[] bbs = this.home.getBuildings();
						for(int i = 0; i < bbs.length; i++){
							if(bbs[i] instanceof Rocket){
								this.targetJob = bbs[i];
								this.targetPos = bbs[i].pos;
								return;
							}
						}
					}
				}
				Building[] bs = this.home.getBuildings();
				Building target = null;
				if(jobType == null)return;
				float lastPos = 999f;
				for(int i = 0; i < bs.length; i++){
					Building b = bs[i];
					if(b.getClass().equals(jobType)){
						if(target == null){
							
							if(jobType.equals(Water.class)){
								if(!((Water)b).hasWater())continue;
							}
							
							target = b;
							lastPos = getDistToX((int)pos.x, (int)b.pos.x);
						}else{
							
							if(jobType.equals(Water.class)){
								if(!((Water)b).hasWater())continue;
							}
							
							float dis = getDist(b.pos);
							if(dis < lastPos){
								target = b;
								lastPos = dis;
							}
						}
					}
				}
				if(target != null){
					this.targetPos = target.pos;
					this.targetJob = target;
				}
			}
		}
	}
	
	public float[] getMotionToTarget(Point p){
		float dy = p.y - pos.y;
		float dx = p.x - pos.x;
		float a = (float)Math.atan2(dy, dx);
		
		float mx = (float)Math.cos(a) * this.speed;
		float my = (float)Math.sin(a) * this.speed;
		
		return new float[]{mx,my};
	}
	
	public boolean isIll(){
		return this.ill;
	}
	
	//Returns a squared distance to make it quicker
	// X is a rot, just a reminder
	public float getDist(Point p){
		float dy = this.pos.y - p.y;
		float dx = this.pos.x - p.x;
		return (dy * dy) + (dx * dx);
	}
	
	public int getDistToX(int s, int t){
		int prev = 0;
		int os = s;
		while(os != t){
			os++;
			prev++;
			if(os > 360)os-=360;
		}
		
		os = s;
		int prev2 = 0;
		while(os != t){
			os --;
			prev2++;
			if(os < 0)os+=360;
		}
		
		return prev < prev2?prev:prev2;
		
	}
}
