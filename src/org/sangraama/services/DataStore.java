package org.sangraama.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.WebSocket;

public class DataStore {
	public static Map<WebSocket, Player> playerMap = new HashMap<>();
	public static Map<String, Player> playerFromServerMap = new HashMap<>();

	public static List<ServerData> serverList = new ArrayList<>();

	public static List<ServerData> getServerList() {
		return serverList;
	}

	public static void setServerList(List<ServerData> serverList) {
		DataStore.serverList = serverList;
	}

}
