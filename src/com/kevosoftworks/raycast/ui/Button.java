package com.kevosoftworks.raycast.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

import com.kevosoftworks.raycast.InputHandler;
import com.kevosoftworks.raycast.Main;

public class Button{
	
	int x;
	int y;
	int width;
	int height;
	BufferedImage text;
	Callable onClick;
	Rectangle2D rect;
	
	public Button(int x, int y, int width, int height, BufferedImage text, Callable onClick){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.onClick = onClick;
		this.rect = new Rectangle(x,y,width,height);
	}
	
	public void tick(InputHandler i){
		if(i.mouseClick){
			if(this.rect.contains(i.pointerX, i.pointerY)){
				i.mouseClick = false;
				try{
					onClick.call();
				} catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void render(Graphics[] gA){
		gA[1].setColor(Color.RED);
		gA[1].drawRect(x, y, width, height);
		gA[1].drawImage(text, x+2, y, null);
	}

}
