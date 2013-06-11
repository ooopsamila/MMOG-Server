package org.sangraama.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.java_websocket.drafts.Draft_10;

public class Listner implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		int port1 = 8887;
		Server s1 = null;
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("server.properties"));
			s1 = new Server(port1);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		s1.start();
		System.out.println("Server1 started on port 8887");

		try {
			loadServerDetails(prop);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread thread = new Thread(new GameEngine());
		thread.start();
	}

	private void loadServerDetails(Properties prop) throws URISyntaxException {
		CurrentServerData.minX = Integer.parseInt(prop
				.getProperty("serverMinX"));
		CurrentServerData.maxX = Integer.parseInt(prop
				.getProperty("serverMaxX"));
		CurrentServerData.minY = Integer.parseInt(prop
				.getProperty("serverMinY"));
		CurrentServerData.maxY = Integer.parseInt(prop
				.getProperty("serverMaxY"));
		List<ServerData> serverList = new ArrayList<>();
		ServerData nServer = new ServerData();
		nServer.setAddress(prop.getProperty("rServerAddress"));
		nServer.setMinX(Integer.valueOf(prop.getProperty("rServerMinX")));
		nServer.setMaxX(Integer.valueOf(prop.getProperty("rServerMaxX")));
		nServer.setMinY(Integer.valueOf(prop.getProperty("rServerMinY")));
		nServer.setMaxY(Integer.valueOf(prop.getProperty("rServerMaxY")));
		Client nServerClient = new Client(new URI(nServer.getAddress()),
				new Draft_10());
		// nServerClient.connect();
		nServer.setClient(nServerClient);
		serverList.add(nServer);

		/*
		 * ServerData neServer = new ServerData();
		 * neServer.setAddress(prop.getProperty("neServerAddress"));
		 * neServer.setMinX(Integer.parseInt(prop.getProperty("neServerMinX")));
		 * neServer.setMaxX(Integer.parseInt(prop.getProperty("neServerMaxX")));
		 * neServer.setMinY(Integer.parseInt(prop.getProperty("neServerMinY")));
		 * neServer.setMaxY(Integer.parseInt(prop.getProperty("neServerMaxY")));
		 * serverList.add(neServer);
		 * 
		 * ServerData eServer = new ServerData();
		 * eServer.setAddress(prop.getProperty("eServerAddress"));
		 * eServer.setMinX(Integer.parseInt(prop.getProperty("eServerMinX")));
		 * eServer.setMaxX(Integer.parseInt(prop.getProperty("eServerMaxX")));
		 * eServer.setMinY(Integer.parseInt(prop.getProperty("eServerMinY")));
		 * eServer.setMaxY(Integer.parseInt(prop.getProperty("eServerMaxY")));
		 * serverList.add(eServer);
		 * 
		 * ServerData seServer = new ServerData();
		 * seServer.setAddress(prop.getProperty("seServerAddress"));
		 * seServer.setMinX(Integer.parseInt(prop.getProperty("seServerMinX")));
		 * seServer.setMaxX(Integer.parseInt(prop.getProperty("seServerMaxX")));
		 * seServer.setMinY(Integer.parseInt(prop.getProperty("seServerMinY")));
		 * seServer.setMaxY(Integer.parseInt(prop.getProperty("seServerMaxY")));
		 * serverList.add(seServer);
		 * 
		 * ServerData sServer = new ServerData();
		 * sServer.setAddress(prop.getProperty("sServerAddress"));
		 * sServer.setMinX(Integer.parseInt(prop.getProperty("sServerMinX")));
		 * sServer.setMaxX(Integer.parseInt(prop.getProperty("sServerMaxX")));
		 * sServer.setMinY(Integer.parseInt(prop.getProperty("sServerMinY")));
		 * sServer.setMaxY(Integer.parseInt(prop.getProperty("sServerMaxY")));
		 * serverList.add(sServer);
		 * 
		 * ServerData swServer = new ServerData();
		 * swServer.setAddress(prop.getProperty("swServerAddress"));
		 * swServer.setMinX(Integer.parseInt(prop.getProperty("swServerMinX")));
		 * swServer.setMaxX(Integer.parseInt(prop.getProperty("swServerMaxX")));
		 * swServer.setMinY(Integer.parseInt(prop.getProperty("swServerMinY")));
		 * swServer.setMaxY(Integer.parseInt(prop.getProperty("swServerMaxY")));
		 * serverList.add(swServer);
		 * 
		 * ServerData wServer = new ServerData();
		 * wServer.setAddress(prop.getProperty("wServerAddress"));
		 * wServer.setMinX(Integer.parseInt(prop.getProperty("wServerMinX")));
		 * wServer.setMaxX(Integer.parseInt(prop.getProperty("wServerMaxX")));
		 * wServer.setMinY(Integer.parseInt(prop.getProperty("wServerMinY")));
		 * wServer.setMaxY(Integer.parseInt(prop.getProperty("wServerMaxY")));
		 * serverList.add(wServer);
		 * 
		 * ServerData nwServer = new ServerData();
		 * nwServer.setAddress(prop.getProperty("nwServerAddress"));
		 * nwServer.setMinX(Integer.parseInt(prop.getProperty("nwServerMinX")));
		 * nwServer.setMaxX(Integer.parseInt(prop.getProperty("nwServerMaxX")));
		 * nwServer.setMinY(Integer.parseInt(prop.getProperty("nwServerMinY")));
		 * nwServer.setMaxY(Integer.parseInt(prop.getProperty("nwServerMaxY")));
		 * serverList.add(nwServer);
		 */

		DataStore.setServerList(serverList);

	}
}
