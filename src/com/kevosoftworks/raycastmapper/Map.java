package com.kevosoftworks.raycastmapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import com.kevosoftworks.raycastmapper.art.Art;
import com.kevosoftworks.raycastmapper.matrix.Matrix2;
import com.kevosoftworks.raycastmapper.ui.Button;
import com.kevosoftworks.raycastmapper.vector.Vector2;
import com.kevosoftworks.raycastmapper.wall.PortalWall;
import com.kevosoftworks.raycastmapper.wall.SolidWall;
import com.kevosoftworks.raycastmapper.wall.Wall;

public class Map {
	
	Camera camera;
	Art art;
	ArrayList<ConvexRoom> rooms;
	ArrayList<Button> buttons;
	int curuuid = 1;
	
	boolean isTopDown = true;
	boolean renderMap = true;
	boolean renderDebugText = false;
	
	boolean renderFloor = true;
	
	float curLocX;
	float curLocY;
	
	static int curRoom = 0;
	static int curWall = 0;
	static boolean newRoom = false;
	static boolean grid = true;
	static boolean subGrid = true;
	static boolean delCurWall = false;
	static boolean toggleFloor = false;
	static boolean toggleCeil = false;
	static int dFloorTex = 0;
	static int dCeilTex = 0;
	static float dFloorHeight = 0f;
	static float dCeilHeight = 0f;
	static boolean toggleWallType = false;
	static float dWallHeight = 0f;
	static float dPortalStart = 0f;
	static float dPortalEnd = 0f;
	static int dPortalRoomId = 0;
	static int dWallTex = 0;
	
	boolean editTmpWall = false;
	Location tmpWallStart = null;
	Location tmpWallEnd = null;
	
	public Map(){
		art = new Art();
		camera = new Camera(this, new Location(0f,0f));
		rooms = new ArrayList<ConvexRoom>();
		buttons = new ArrayList<Button>();
		
		ArrayList<Wall>w1 = new ArrayList<Wall>();		
		ConvexRoom r1 = new ConvexRoom(0, w1);
		r1.setFloorProperties(true, art.getTexture(Art.TEXTURE_NONE));
		r1.setCeilingProperties(true, art.getTexture(Art.TEXTURE_NONE));
		//r1.setCeilingProperties(false);
		
		rooms.add(r1);
		
		Button b = new Button(0, 0, 6, 10, art.text("<"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.curRoom -= 1;
				Map.curWall = 0;
				return null;
			}
			
		});
		
		buttons.add(b);
		b = new Button(8, 0, 6, 10, art.text(">"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.curRoom += 1;
				Map.curWall = 0;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(16, 0, 19, 10, art.text("New"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.newRoom = true;
				Map.curRoom += 1;
				Map.curWall = 0;
				return true;
			}
		});
		buttons.add(b);
		b = new Button(Main.RW - 64, 0, 63, 10, art.text("Toggle grid"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.grid = !Map.grid;
				if(!Map.grid) Map.subGrid = false;
				return true;
			}
		});
		buttons.add(b);
		b = new Button(Main.RW - 64, 12, 63, 10, art.text("Toggle subgrid"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.subGrid = !Map.subGrid;
				return true;
			}
		});
		buttons.add(b);
		
		b = new Button(0, 12, 6, 10, art.text("<"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.curWall--;
				return null;
			}
			
		});
		
		buttons.add(b);
		b = new Button(8, 12, 6, 10, art.text(">"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.curWall++;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(16, 12, 19, 10, art.text("Del"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.delCurWall = true;
				return true;
			}
		});
		buttons.add(b);
		
		b = new Button(2, 85, 37, 10, art.text("Tgl Floor"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.toggleFloor = true;
				return true;
			}
		});
		buttons.add(b);
		b = new Button(45, 85, 32, 10, art.text("Tgl Ceil"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.toggleCeil = true;
				return true;
			}
		});
		buttons.add(b);
		b = new Button(64, 44, 6, 10, art.text("<"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dFloorTex--;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(72, 44, 6, 10, art.text(">"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dFloorTex++;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(64, 66, 6, 10, art.text("<"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dCeilTex--;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(72, 66, 6, 10, art.text(">"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dCeilTex++;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(50, 44, 6, 10, art.text("-"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dFloorHeight -= 0.1f;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(58, 44, 6, 10, art.text("+"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dFloorHeight += 0.1f;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(50, 66, 6, 10, art.text("-"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dCeilHeight -= 0.1f;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(58, 66, 6, 10, art.text("+"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dCeilHeight += 0.1f;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(50, 120, 6, 10, art.text("-"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dWallHeight -= 0.1f;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(56, 120, 6, 10, art.text("+"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dWallHeight += 0.1f;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(34, 132, 6, 10, art.text("-"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dPortalStart -= 0.1f;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(40, 132, 6, 10, art.text("+"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dPortalStart += 0.1f;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(84, 132, 6, 10, art.text("-"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dPortalEnd -= 0.1f;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(90, 132, 6, 10, art.text("+"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dPortalEnd += 0.1f;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(60, 142, 6, 10, art.text("-"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dPortalRoomId--;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(66, 142, 6, 10, art.text("+"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dPortalRoomId++;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(65, 110, 14, 10, art.text("Tgl"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.toggleWallType = true;
				return null;
			}
		});
		buttons.add(b);
		
		b = new Button(64, 120, 6, 10, art.text("<"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dWallTex--;
				return null;
			}
		});
		buttons.add(b);
		b = new Button(72, 120, 6, 10, art.text(">"), new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception{
				Map.dWallTex++;
				return null;
			}
		});
		buttons.add(b);
	}
	
	public void tick(InputHandler input){
		if(newRoom){
			newRoom = false;
			rooms.add(new ConvexRoom(rooms.size(), new ArrayList<Wall>()));
			curRoom = rooms.size() - 1;
		}
		
		
		camera.tick(input);
		curLocX = camera.getLocation().getX() + (input.pointerX - Main.RW / 2) / ((float)Main.RH/16f);
		curLocY = camera.getLocation().getY() + (input.pointerY - Main.RH / 2) / ((float)Main.RH/16f);
		
		//System.out.println("Min: " + camera.minAngle() + "; Max: " + camera.maxAngle());
		if(input.renderFloor){
			input.renderFloor = false;
			renderFloor = !renderFloor;
		}
		
		for(Button b:buttons){
			b.tick(input);
		}
		
		if(curRoom < 0) curRoom = 0;
		if(curRoom > rooms.size()-1) curRoom = rooms.size()-1;
		
		if(delCurWall){
			delCurWall = false;
			this.getRoom(curRoom).removeWall(curWall);
		}
		
		ConvexRoom r = this.getRoom(curRoom);
		if(toggleFloor){
			toggleFloor = false;
			if(r.hasFloor()){
				r.setFloorProperties(false);
			} else {
				r.setFloorProperties(true, art.getTexture(Art.TEXTURE_NONE), 1f);
			}
		}
		
		if(toggleCeil){
			toggleCeil = false;
			if(r.hasCeiling()){
				r.setCeilingProperties(false);
			} else {
				r.setCeilingProperties(true, art.getTexture(Art.TEXTURE_NONE), 1f);
			}
		}
		
		if(dFloorTex != 0 && r.hasFloor()){
			r.setFloorProperties(true, art.getTexture(r.getFloorTexture().getNumber() + dFloorTex), r.getFloorTextureScale());
			dFloorTex = 0;
		}
		
		if(dCeilTex != 0 && r.hasCeiling()){
			r.setCeilingProperties(true, art.getTexture(r.getCeilingTexture().getNumber() + dCeilTex), r.getCeilingTextureScale());
			dCeilTex = 0;
		}
		
		if(dFloorHeight != 0f && r.hasFloor()){
			r.setFloorProperties(true, r.getFloorTexture(), roundToOne(r.getFloorTextureScale() + dFloorHeight));
			dFloorHeight = 0f;
		}
		
		if(dCeilHeight != 0f && r.hasCeiling()){
			r.setCeilingProperties(true, r.getCeilingTexture(), roundToOne(r.getCeilingTextureScale() + dCeilHeight));
			dCeilHeight = 0f;
		}
		
		if(this.getRoom(curRoom).getWall(curWall) instanceof PortalWall){
			PortalWall pw = (PortalWall)this.getRoom(curRoom).getWall(curWall);
			if(dPortalStart != 0f){
				pw.setPortalStart(roundToOne(pw.getPortalStart() + dPortalStart));
			}
			
			if(dPortalEnd != 0f){
				pw.setPortalStop(roundToOne(pw.getPortalStop() + dPortalEnd));
			}
			
			if(dPortalRoomId != 0){
				pw.setRoomId(pw.getRoomId() + dPortalRoomId);
			}
		}
		dPortalStart = 0f;
		dPortalEnd = 0f;
		dPortalRoomId = 0;
		
		Wall w = this.getRoom(curRoom).getWall(curWall);
		if(w != null){
			if(dWallHeight != 0f){
				w.setHeight(roundToOne(w.getHeight() + dWallHeight));
			}
			if(dWallTex != 0){
				w.setTexture(art.getTexture(w.getTexture().getNumber() + dWallTex));
			}
		}
		dWallHeight = 0f;
		dWallTex = 0;
		
		if(toggleWallType && w != null){
			toggleWallType = false;
			Wall tmpW = this.getRoom(curRoom).getWall(curWall);
			this.getRoom(curRoom).removeWall(tmpW.getId());
			Wall newW;
			if(tmpW instanceof SolidWall){
				newW = new PortalWall(this.getRoom(curRoom).getNewWallId(), tmpW.getLocation1(), tmpW.getLocation2(), tmpW.getNormal(), tmpW.getTexture(), 0f, 1f, tmpW.getHeight(), 0);
			} else {
				newW = new SolidWall(this.getRoom(curRoom).getNewWallId(), tmpW.getLocation1(), tmpW.getLocation2(), tmpW.getNormal(), tmpW.getTexture(), tmpW.getHeight());
			}
			this.getRoom(curRoom).addWall(newW);
			curWall = newW.getId();
		}
		
		tmpWallEnd = new Location(roundToGrid(curLocX), roundToGrid(curLocY));
		if(input.mouseClick){
			input.mouseClick = false;
			if(!editTmpWall){
				editTmpWall = true;
				tmpWallStart = new Location(roundToGrid(curLocX), roundToGrid(curLocY));
			} else {
				editTmpWall = false;
				curWall = this.getRoom(curRoom).addWall(new SolidWall(
							this.getRoom(curRoom).getNewWallId(), //Wall ID
							new Location(tmpWallStart.getX(), tmpWallStart.getY()), //X pos 
							new Location(tmpWallEnd.getX(), tmpWallEnd.getY()), //Y pos
							new Vector2(1f, 0f), //Normal
							art.getTexture(Art.TEXTURE_NONE), //Texture
							1f //Height
						));
			}
		}
		
		renderMap = input.renderMap;
		renderDebugText = input.renderDebugText;
	}
	
	public void render(Graphics[] gA){
		if(renderMap){
			//render grid
			Location lGrid = new Location(Math.round(camera.getLocation().getX()), Math.round(camera.getLocation().getY()));
			Point2D pGrid = camera.getPoint2D(lGrid);
			for(int i = -28; i < 28; i++){
				if(subGrid){
					gA[1].setColor(new Color(0.1f, 0.1f, 0.1f));
					gA[1].drawLine((int)(pGrid.getX() + (i+0.5) * ((float)Main.RH/16f)), 0, (int)(pGrid.getX() + (i+0.5) * ((float)Main.RH/16f)), Main.RH);
					gA[1].drawLine(0, (int)(pGrid.getY() + (i+0.5) * ((float)Main.RH/16f)), Main.RW, (int)(pGrid.getY() + (i+0.5) * ((float)Main.RH/16f)));
				}

				if(grid){
					gA[1].setColor(new Color(0.2f, 0.2f, 0.2f));
					gA[1].drawLine((int)(pGrid.getX() + i * ((float)Main.RH/16f)), 0, (int)(pGrid.getX() + i * ((float)Main.RH/16f)), Main.RH);
					gA[1].drawLine(0, (int)(pGrid.getY() + i * ((float)Main.RH/16f)), Main.RW, (int)(pGrid.getY() + i * ((float)Main.RH/16f)));
				}
			}
			Point2D spawn = camera.getPoint2D(new Location(0,0));
			gA[1].setColor(new Color(0.3f, 0.4f, 0.8f));
			gA[1].drawLine((int)spawn.getX() - 5, (int)spawn.getY(), (int)spawn.getX() + 5, (int)spawn.getY());
			gA[1].drawLine((int)spawn.getX(), (int)spawn.getY() - 5, (int)spawn.getX(), (int)spawn.getY() + 5);
			
			for(ConvexRoom cr:rooms){
				Wall[] walls = cr.getWalls();
				for(Wall w:walls){
					Location[] l = w.getLocations();
					Point2D[] p = camera.getPoints2D(l);
					//We assume the screen to have 16 units in the y coordinate
					if(cr.getId() == curRoom){
						if(w.getId() == curWall){
							gA[1].setColor(Color.BLACK);
							gA[1].fillRect(0, 100, 96, 52);
							gA[1].drawImage(w.getTexture().getBufferedImage(), 80, 110, 96, 126, 0, 0, 16, 16, null);
							gA[1].drawImage(art.text("--Wall Properties--"), 5, 100, null);
							gA[1].drawImage(art.text("Type: " + w.getClass().getSimpleName()), 1, 110, null);
							gA[1].drawImage(art.text("Height: " + w.getHeight()), 1, 120, null);
							if(w instanceof PortalWall){
								PortalWall w2 = (PortalWall)w;
								gA[1].drawImage(art.text("PS: " + w2.getPortalStart()), 2, 132, null);
								gA[1].drawImage(art.text("PE: " + w2.getPortalStop()), 55, 132, null);
								gA[1].drawImage(art.text("P room id: " + w2.getRoomId()), 2, 142, null);
							}
						}
						gA[1].setColor(Color.WHITE);
						if(w instanceof PortalWall) gA[1].setColor(Color.YELLOW);
						if(w.getId() == curWall) gA[1].setColor(Color.RED);
						
					} else {
						gA[1].setColor(new Color(0f, 0f, 1f, 0.5f));
					}
					gA[1].drawLine((int)p[0].getX(), (int)p[0].getY(), (int)p[1].getX(), (int)p[1].getY());
				}
			}
			
			if(this.editTmpWall){
				Point2D ws = camera.getPoint2D(tmpWallStart);
				Point2D we = camera.getPoint2D(tmpWallEnd);
				gA[1].setColor(Color.GREEN);
				gA[1].drawLine((int)ws.getX(), (int)ws.getY(), (int)we.getX(), (int)we.getY());
			}

		}
		
		gA[1].drawImage(art.text("Cur room: " + curRoom), 37, 0, null);
		gA[1].drawImage(art.text("Cur wall: " + curWall), 37, 12, null);
		gA[1].drawImage(art.text("x: " + String.format("%.1f", curLocX) + "; y: " + String.format("%.1f", curLocY) + ";"), 2, Main.RH - 12, null);
		//gA[1].drawImage(art.text("Move: WASD; Look: Mouse; Sprint: SHIFT; Map: Q; Debug: F1;", Color.CYAN), Main.RW / 2 - art.text("Move: WASD; Look: Mouse; Sprint: SHIFT; Map: Q; Debug: F1;").getWidth() / 2, Main.RH - 12, null);
		
		ConvexRoom r = this.getRoom(curRoom);
		gA[1].setColor(Color.BLACK);
		gA[1].fillRect(0, 30, 96, 52);
		gA[1].drawImage(art.text("--Room Properties--"), 5, 32, null);
		gA[1].drawImage(art.text("F: " + r.hasFloor() + ", " + r.getFloorTextureScale()), 1, 44, null);
		if(r.hasFloor())gA[1].drawImage(r.getFloorTexture().getBufferedImage(), 80, 42, 96, 58, 0, 0, 16, 16, null);
		
		gA[1].drawImage(art.text("C: " + r.hasCeiling() + ", " + r.getCeilingTextureScale()), 1, 66, null);
		if(r.hasCeiling())gA[1].drawImage(r.getCeilingTexture().getBufferedImage(), 80, 64, 96, 80, 0, 0, 16, 16, null);
		
		for(Button b:buttons){
			b.render(gA);
		}
	}
    
    public Location getIntersectionLocation(Location ray, Location[] wall){
    	Location camera = this.camera.getLocation();
    	
    	float dyRay = ray.getY() - camera.getY();
    	float dxRay = ray.getX() - camera.getX();
    	
    	float dyWall = wall[1].getY() - wall[0].getY();
    	float dxWall = wall[1].getX() - wall[0].getX();
    	
    	float xIntersect;
    	float yIntersect;
    	boolean yForce = false;
    	
    	if(dxRay == 0f){
    		if(dxWall == 0f) return null;
    		float slopeWall = dyWall / dxWall;
    		float offsetWall = wall[1].getY() - (wall[1].getX() * slopeWall);
    		
    		xIntersect = ray.getX();
    		yIntersect = slopeWall * xIntersect + offsetWall;
    	} else if(dxWall == 0f){
    		float slopeRay = dyRay / dxRay;
	    	float offsetRay = ray.getY() - (ray.getX() * slopeRay);
	    	
	    	xIntersect = wall[1].getX();
	    	yIntersect = slopeRay * xIntersect + offsetRay;
    	} else {
	    	float slopeRay = dyRay / dxRay;
	    	float offsetRay = ray.getY() - (ray.getX() * slopeRay);
	    	float slopeWall = dyWall / dxWall;
	    	float offsetWall = wall[1].getY() - (wall[1].getX() * slopeWall);
	    	
	    	if(slopeRay == slopeWall) return null;
	    	xIntersect = (offsetWall - offsetRay) / (slopeRay - slopeWall);
	    	yIntersect = slopeRay * xIntersect + offsetRay;
    	}
    	
    	if(dyWall == 0) yForce = true;
    	
    	Location r = new Location(xIntersect, yIntersect);
    	if(xIntersect >= Math.min(wall[1].getX(), wall[0].getX()) && xIntersect <= Math.max(wall[1].getX(), wall[0].getX())){
    		if((yIntersect >= Math.min(wall[1].getY(), wall[0].getY()) && yIntersect <= Math.max(wall[1].getY(), wall[0].getY())) || yForce){
    			if(ray.getY() < camera.getY()){
    				//Up direction
    				if(yIntersect < camera.getY()){
    					if(ray.getX() < camera.getX()){
    						//left
    						if(xIntersect < camera.getX()) return r;
    					} else {
    						//right
    						if(xIntersect >= camera.getX()) return r;
    					}
    				}
    			} else {
    				//Down direction
    				if(yIntersect >= camera.getY()){
    					if(ray.getX() < camera.getX()){
    						//left
    						if(xIntersect < camera.getX()) return r;
    					} else {
    						//right
    						if(xIntersect >= camera.getX()) return r;
    					}
    				}
    			}
    		}
    	}
    	return null;
    }
    
    public ConvexRoom getCurrentRoom(){
    	return this.getRoom(this.curuuid);
    }
    
    public ConvexRoom getRoom(int uuid){
    	for(ConvexRoom cr:rooms){
    		if(cr.getId() == uuid) return cr;
    	}
    	return null;
    }
    
    public float roundToGrid(float pos){
    	if(!subGrid) return Math.round(pos);
    	return Math.round(pos * 2f) / 2f;
    }
    
    public float roundToOne(float num){
    	return Math.round(num * 10f) / 10f;
    }

}
