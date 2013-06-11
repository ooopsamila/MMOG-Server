package org.sangraama.services;

public class IncomingMessage {
	private int type;
	private String id;
	private float v_x;
	private float v_y;
	private float v_a;

	public float getV_a() {
		return v_a;
	}

	public void setV_a(float v_a) {
		this.v_a = v_a;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getV_x() {
		return v_x;
	}

	public void setV_x(float v_x) {
		this.v_x = v_x;
	}

	public float getV_y() {
		return v_y;
	}

	public void setV_y(float v_y) {
		this.v_y = v_y;
	}

}
