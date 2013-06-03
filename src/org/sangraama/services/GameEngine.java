package org.sangraama.services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class GameEngine implements Runnable {
	private World world;

	GameEngine() {
		this.world = new World(new Vec2(0.0f, 0.0f), true);
	}

	@Override
	public void run() {
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateGameWorld();
				world.step(Constants.timeStep, Constants.velocityIterations,
						Constants.positionIterations);
				updateClients();
			}

		});
		timer.start();
	}

	private void updateGameWorld() {
		for (Player player : DataStore.playerMap.values()) {
			Body body = player.getBody();
			if (body != null) {
				Vec2 velocity = new Vec2(0f, 0f);
				IncomingMessage incomingMessage = player.getIncomingMessage();
				int v_x = incomingMessage.getV_x();
				int v_y = incomingMessage.getV_y();
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

	private void updateClients() {
		List<OutgoingMessage> outgoingMessages = new ArrayList<>();
		String id = null;
		for (Player player : DataStore.playerMap.values()) {
			Body body = player.getBody();
			OutgoingMessage outgoingMessage = new OutgoingMessage();
			outgoingMessage.setX(body.getPosition().x);
			outgoingMessage.setY(body.getPosition().y);
			outgoingMessage.setAngle(body.getAngle());
			if (outgoingMessage.getX() == 100) {
				outgoingMessage.setType("CHANGE");
				world.destroyBody(body);
				id = player.getId();
			} else {
				outgoingMessage.setType("UPDATE");
			}
			outgoingMessages.add(outgoingMessage);
		}
		for (Player player : DataStore.playerMap.values()) {
			player.getWebSocket().send(JsonUtil.listToJson(outgoingMessages));
		}
		if (id != null) {
			DataStore.playerMap.remove(id);
		}
	}
}
