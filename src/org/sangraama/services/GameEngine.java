package org.sangraama.services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class GameEngine implements Runnable {
	private World world;
	private GameLogic gameLogic;

	GameEngine() {
		this.world = new World(new Vec2(0.0f, 0.0f), true);
		this.gameLogic = new GameLogic();
	}

	@Override
	public void run() {
		System.out.println("run");
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameLogic.updateGameWorld(world);
				world.step(Constants.timeStep, Constants.velocityIterations,
						Constants.positionIterations);
				gameLogic.updateClients(world);
			}

		});
		timer.start();
	}

}
