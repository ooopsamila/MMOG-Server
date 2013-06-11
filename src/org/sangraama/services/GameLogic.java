package org.sangraama.services;

import java.util.ArrayList;
import java.util.List;
import org.java_websocket.WebSocket;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class GameLogic {

	public void updateGameWorld(World world) {

		for (Player player : DataStore.playerMap.values()) {
			Body body = player.getBody();
			if (body != null) {
				Vec2 velocity = new Vec2(0f, 0f);
				IncomingMessage incomingMessage = player.getIncomingMessage();
				float v_x = incomingMessage.getV_x();
				float v_y = incomingMessage.getV_y();
				float angle = body.getAngle();
				if (v_x == 1) {
					velocity.x = 10f;
					angle = 0;
				} else if (v_x == -1) {
					velocity.x = -10f;
					angle = 180;
				}
				if (v_y == 1) {
					velocity.y = 10f;
					angle = 90;
				} else if (v_y == -1) {
					velocity.y = -10f;
					angle = 270;
				}
				body.setLinearVelocity(velocity);
				body.setTransform(body.getPosition(), angle);

			} else {
				player.setBody(world.createBody(player.getBodyDef()));
			}
		}

	}

	public void updateClients(World world) {
		List<OutgoingMessage> outgoingMessages = new ArrayList<>();
		List<WebSocket> playersToRemove = new ArrayList<>();
		for (Player player : DataStore.playerMap.values()) {
			Body body = player.getBody();
			OutgoingMessage outgoingMessage = new OutgoingMessage();
			outgoingMessage.setX(body.getPosition().x);
			outgoingMessage.setY(body.getPosition().y);
			outgoingMessage.setAngle(body.getAngle());
			outgoingMessage.setId(player.getId());
			if (isInServerRange(outgoingMessage)) {
				outgoingMessage.setType(1);
			} else {
				outgoingMessage.setType(2);
				world.destroyBody(body);
				playersToRemove.add(player.getWebSocket());
			}
			outgoingMessages.add(outgoingMessage);
		}
		for (Player player : DataStore.playerMap.values()) {
			player.getWebSocket().send(JsonUtil.listToJson(outgoingMessages));
		}
		for (WebSocket webSocket : playersToRemove) {
			DataStore.playerMap.remove(webSocket);
		}
	}

	private boolean isInServerRange(OutgoingMessage outgoingMessage) {
		float x = outgoingMessage.getX();
		float y = outgoingMessage.getY();
		float minX = CurrentServerData.minX;
		float maxX = CurrentServerData.maxX;
		float minY = CurrentServerData.minY;
		float maxY = CurrentServerData.maxY;

		if (minX <= x && x <= maxX && minY <= y && y <= maxY) {
			return true;
		} else {
			ServerData serverData = findServerInRange(x, y);
			IncomingMessage incomingMessage = new IncomingMessage();
			incomingMessage.setType(2);
			incomingMessage.setId(outgoingMessage.getId());
			incomingMessage.setV_x(outgoingMessage.getX());
			incomingMessage.setV_y(outgoingMessage.getY());
			incomingMessage.setV_a(outgoingMessage.getAngle());
			serverData.getClient().send(
					JsonUtil.inComingToString(incomingMessage));

			outgoingMessage.setUrl(serverData.getAddress());
			return false;
		}

	}

	private ServerData findServerInRange(float x, float y) {
		List<ServerData> serverList = DataStore.serverList;
		for (ServerData serverData : serverList) {
			float minX = serverData.getMinX();
			float maxX = serverData.getMaxX();
			float minY = serverData.getMinY();
			float maxY = serverData.getMaxY();
			if (minX <= x && x <= maxX && minY <= y && y <= maxY) {
				return serverData;
			}
		}
		return null;
	}
}
