package com.kevosoftworks.raycastmapper.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kevosoftworks.raycastmapper.InputHandler;

public class Pointer{
	
	int x = 0;
	int y = 0;
	BufferedImage img;
	
	public Pointer(){
		img = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.PINK);
		g.drawLine(0, 0, 8, 8);
		g.drawLine(0, 0, 4, 0);
		g.drawLine(0, 0, 0, 4);
		g.dispose();
	}
	
	public void render(Graphics[] gA){
		gA[1].drawImage(img, x, y, null);
	}
	
	public void tick(InputHandler i){
		this.x = i.pointerX;
		this.y = i.pointerY;
		
		//System.out.println(x);
	}

}
