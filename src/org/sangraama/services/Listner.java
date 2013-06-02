package org.sangraama.services;

import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;

public class Listner implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		int port = 8887; // 843 flash policy port
		Server s = null;
		try {
			s = new Server(port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.start();
		Thread thread = new Thread(new GameEngine());
		thread.start();
	}
}
