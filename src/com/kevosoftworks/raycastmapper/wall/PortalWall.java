package com.kevosoftworks.raycastmapper.wall;

import com.kevosoftworks.raycastmapper.Location;
import com.kevosoftworks.raycastmapper.art.Texture;
import com.kevosoftworks.raycastmapper.vector.Vector2;

public class PortalWall extends Wall{
	
	int rid;
	
	float portalHeight;
	
	float pStart;
	float pStop;
	
	public PortalWall(int id, Location p1, Location p2, Vector2 norm, Texture tex, float pStart, float pStop, float height, int rid){
		super(id, p1, p2, norm, tex, height);
		this.rid = rid;
		this.pStart = pStart;
		this.pStop = pStop;
	}
	
	public int getRoomId(){
		return this.rid;
	}
	
	public float getPortalHeight(){
		return this.pStop - this.pStart;
	}
	
	public float getPortalStart(){
		return this.pStart;
	}
	
	public float getPortalStop(){
		return this.pStop;
	}
	
	public void setPortalStart(float start){
		this.pStart = start;
	}
	
	public void setPortalStop(float stop){
		this.pStop = stop;
	}
	
	public void setRoomId(int rid){
		this.rid = rid;
	}

}
