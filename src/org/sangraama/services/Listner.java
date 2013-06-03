package org.sangraama.services;

import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;

public class Listner implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		int port1 = 8887; // 843 flash policy port
		int port2 = 8888;
		Server s1 = null;
		Server s2 = null;
		try {
			s1 = new Server(port1);
			s2 = new Server(port2);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s1.start();
		System.out.println("Server1 started on port 8887");
		s2.start();
		System.out.println("Server2 started on port 8888");
		Thread thread = new Thread(new GameEngine());
		thread.start();
	}
}
