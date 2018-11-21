package com.kevosoftworks.raycast.ui.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.kevosoftworks.raycast.InputHandler;
import com.kevosoftworks.raycast.ui.GUIInterpreter;
import com.kevosoftworks.raycast.ui.GUIRenderer;

public class GUIElement{
	
	BufferedImage image;
	ArrayList<GUIElement> elements;
	
	int x;
	int y;
	int width;
	int height;
	
	public GUIElement(){
		this.elements = new ArrayList<GUIElement>();
		
		GUIInterpreter gi = new GUIInterpreter();
		this.x = gi.getX();
		this.y = gi.getY();
		this.width = gi.getWidth();
		this.height = gi.getHeight();
		
		this.image = (new GUIRenderer(gi)).createBufferedImage();
	}
	
	public void tick(InputHandler i){
		for(GUIElement elem:this.elements){
			elem.tick(i);
		}
	}
	
	public void render(Graphics[] gA, BufferedImage[] bI){
		gA[1].drawImage(image, x, y, width, height, null);
		for(GUIElement elem:this.elements){
			elem.render(gA, bI);
		}
	}

}
