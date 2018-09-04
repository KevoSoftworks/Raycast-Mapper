package com.kevosoftworks.raycastmapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;

import com.kevosoftworks.raycastmapper.art.Art;
import com.kevosoftworks.raycastmapper.art.Mipmap;
import com.kevosoftworks.raycastmapper.art.Texture;
import com.kevosoftworks.raycastmapper.vector.Vector2;
import com.kevosoftworks.raycastmapper.wall.PortalWall;
import com.kevosoftworks.raycastmapper.wall.Wall;

public class ConvexRoom{
	//Room properties
	private int id = -1;
	private ArrayList<Wall> walls;
	private float zHeight = 0f;
	
	//Render settings
	private boolean renderWalls = true;
	private boolean renderFloor = true;
	private boolean renderCeil = true;
	private boolean renderLight = true;
	private boolean isCulling = true;
	
	//Light properties
	private float lightDistanceDropoff = 16f;
	private float lightMaximumBrightness = 0.6f;
	
	//Floor properties
	private boolean hasFloor = false;
	private Texture floorTexture = null;
	private float floorTextureScale = 1f;
	
	//Ceiling properties
	private boolean hasCeil = false;
	private Texture ceilTexture = null;
	private float ceilTextureScale = 1f;
	
	
	public ConvexRoom(int id, ArrayList<Wall> walls){
		this.id = id;
		this.walls = walls;
	}
	
	public ConvexRoom(int id, ArrayList<Wall> walls, float zHeight){
		this.id = id;
		this.walls = walls;
		this.zHeight = zHeight;
	}
	
	public void setFloorProperties(boolean hasFloor){
		this.setFloorProperties(hasFloor, null, 1f);
	}
	
	public void setFloorProperties(boolean hasFloor, Texture floorTexture){
		this.setFloorProperties(hasFloor, floorTexture, 1f);
	}
	
	public void setFloorProperties(boolean hasFloor, Texture floorTexture, float textureScale){
		this.hasFloor = hasFloor;
		this.floorTexture = floorTexture;
		this.floorTextureScale = textureScale;
		if(textureScale < 0.1f) this.floorTextureScale = 0.1f;
	}
	
	public void setCeilingProperties(boolean hasCeil){
		this.setCeilingProperties(hasCeil, null, 1f);
	}
	
	public void setCeilingProperties(boolean hasCeil, Texture ceilTexture){
		this.setCeilingProperties(hasCeil, ceilTexture, 1f);
	}
	
	public void setCeilingProperties(boolean hasCeil, Texture ceilTexture, float textureScale){
		this.hasCeil = hasCeil;
		this.ceilTexture = ceilTexture;
		this.ceilTextureScale = textureScale;
		if(textureScale < 0.1f) this.ceilTextureScale = 0.1f;
	}
	
	public boolean hasFloor(){
		return this.hasFloor;
	}
	
	public Texture getFloorTexture(){
		return this.floorTexture;
	}
	
	public float getFloorTextureScale(){
		return this.floorTextureScale;
	}
	
	public boolean hasCeiling(){
		return this.hasCeil;
	}
	
	public Texture getCeilingTexture(){
		return this.ceilTexture;
	}
	
	public float getCeilingTextureScale(){
		return this.ceilTextureScale;
	}
	
	public int getId(){
		return this.id;
	}
	
	public Wall[] getWalls(){
		return this.walls.toArray(new Wall[walls.size()]);
	}
	
	public Wall getWall(int id){
		for(Wall w:walls){
			if(w.getId()==id) return w;
		}
		return null;
	}
	
	public int addWall(Wall w){
		this.walls.add(w);
		return w.getId();
	}
	
	public int getNewWallId(){
		if(this.walls.size() == 0) return 0;
		return this.walls.get(this.walls.size() - 1).getId() + 1;
	}
	
	public boolean removeWall(int id){
		for(Wall w:walls){
			if(w.getId() == id) return walls.remove(w);
		}
		return false;
	}
}
