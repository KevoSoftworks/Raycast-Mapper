package com.kevosoftworks.raycastmapper.savable;

import java.util.ArrayList;

import com.kevosoftworks.raycastmapper.ConvexRoom;
import com.kevosoftworks.raycastmapper.Map;

public class SavableMap extends Savable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SavableConvexRoom> rooms;
	
	public SavableMap(Map m){
		rooms = new ArrayList<SavableConvexRoom>();
		for(ConvexRoom r:m.getRooms()){
			rooms.add(new SavableConvexRoom(r));
		}
	}
	
	public SavableConvexRoom[] getRooms(){
		return this.rooms.toArray(new SavableConvexRoom[1]);
	}

}
