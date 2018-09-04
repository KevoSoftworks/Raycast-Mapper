package com.kevosoftworks.raycastmapper.wall;

import com.kevosoftworks.raycastmapper.Location;
import com.kevosoftworks.raycastmapper.art.Texture;
import com.kevosoftworks.raycastmapper.vector.Vector2;

public class SolidWall extends Wall{
	
	public SolidWall(int id, Location p1, Location p2, Vector2 norm, Texture tex){
		super(id, p1, p2, norm, tex);
	}
	
	public SolidWall(int id, Location p1, Location p2, Vector2 norm, Texture tex, float height){
		super(id, p1, p2, norm, tex, height);
	}

}
