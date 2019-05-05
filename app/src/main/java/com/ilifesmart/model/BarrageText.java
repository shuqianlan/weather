package com.ilifesmart.model;

public class BarrageText {
	public String text;
	private int x;
	private int y;
	private int color;
	private int fontSize;
	private int speed;

	public BarrageText setText(String str) {
		text = str;
		return this;
	}

	public BarrageText setX(int x) {
		this.x = x;
		return this;
	}

	public BarrageText setY(int y) {
		this.y = y;
		return this;
	}

	public BarrageText setTextColor(int color) {
		this.color = color;
		return this;
	}

	public BarrageText setTextSize(int fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	public BarrageText setSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	public String getText() {
		return text;
	}

	public int getX() {
		x -= speed;
		return x;
	}

	public int getY() {
		return y;
	}

	public int getColor() {
		return color;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getSpeed() {
		return speed;
	}
}
