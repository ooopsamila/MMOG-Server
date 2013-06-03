package org.sangraama.services;

public class OutgoingMessage {
	private String type;
	private float x;
	private float y;
	private float angle;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
