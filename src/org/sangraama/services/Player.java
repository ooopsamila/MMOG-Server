package org.sangraama.services;

import org.java_websocket.WebSocket;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public class Player {
	private String id;
	private BodyDef bodyDef;
	private Body body;
	private IncomingMessage incomingMessage;
	private WebSocket webSocket;

	public WebSocket getWebSocket() {
		return webSocket;
	}

	public void setWebSocket(WebSocket webSocket) {
		this.webSocket = webSocket;
	}

	public Player(String id, float x, float y, float angle) {
		this.id = id;
		this.bodyDef = this.createBodyDef(x, y, angle);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BodyDef getBodyDef() {
		return bodyDef;
	}

	public void setBodyDef(BodyDef bodyDef) {
		this.bodyDef = bodyDef;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public IncomingMessage getIncomingMessage() {
		return incomingMessage;
	}

	public void setIncomingMessage(IncomingMessage incomingMessage) {
		this.incomingMessage = incomingMessage;
	}

	public BodyDef createBodyDef(float x, float y, float angle) {
		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.angle = angle;
		bd.type = BodyType.DYNAMIC;
		return bd;
	}

}
