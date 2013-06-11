package org.sangraama.services;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {
	// private Player player;

	public Server(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public Server(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onClose(WebSocket webSocket, int arg1, String arg2, boolean arg3) {
		System.out.println("On close" + arg3);
		String id = webSocket.toString().substring(
				webSocket.toString().indexOf("@"));
		System.out.println("close id" + id);
		DataStore.playerMap.remove(webSocket);
		System.out.println(DataStore.playerMap.size());
		/*
		 * connections().remove(webSocket);
		 * 
		 * String id = webSocket.toString().substring(
		 * webSocket.toString().indexOf("@")); DataStore.playerMap.remove(id);
		 */

	}

	@Override
	public void onError(WebSocket webSocket, Exception arg1) {
		System.out.println("On error");

	}

	@Override
	public void onMessage(WebSocket webSocket, String msg) {
		System.out.println("On message" + msg + webSocket.toString());
		System.out.println(webSocket);
		IncomingMessage incomingMessage = JsonUtil.jsonToObject(msg);
		String id = incomingMessage.getId();
		if (incomingMessage.getType() == 1) {
			if (DataStore.playerMap.get(webSocket) == null) {
				if (DataStore.playerFromServerMap.get(id) == null) {
					Player player = new Player(id, 50, 50, 90);
					player.setIncomingMessage(incomingMessage);
					player.setWebSocket(webSocket);
					DataStore.playerMap.put(webSocket, player);
				} else {
					Player player = DataStore.playerFromServerMap.get(id);
					player.setWebSocket(webSocket);
					player.setIncomingMessage(incomingMessage);
					DataStore.playerMap.put(webSocket, player);
					DataStore.playerFromServerMap.remove(id);
				}
			} else {
				DataStore.playerMap.get(webSocket).setIncomingMessage(
						incomingMessage);
			}
		} else if (incomingMessage.getType() == 2) {
			Player player = new Player(id, incomingMessage.getV_x(),
					incomingMessage.getV_y(), incomingMessage.getV_a());

			DataStore.playerFromServerMap.put(id, player);
		}

	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake arg1) {
		System.out.println("Connection opened" + webSocket.toString());
		for (ServerData serverData : DataStore.getServerList()) {
			Client client = serverData.getClient();
			if (client.getConnection() == null) {
				client.connect();
			}
		}

	}

}
