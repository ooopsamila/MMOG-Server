package org.sangraama.services;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {
//	private Player player;

	public Server(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public Server(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onClose(WebSocket webSocket, int arg1, String arg2, boolean arg3) {
		System.out.println("On close");
		String id = webSocket.toString().substring(
				webSocket.toString().indexOf("@"));
		System.out.println("close id" + id);
		DataStore.playerMap.remove(id);
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
		System.out.println("On message" + msg);
		IncomingMessage incomingMessage = JsonUtil.jsonToObject(msg);
		String id = webSocket.toString().substring(
				webSocket.toString().indexOf("@"));
		DataStore.playerMap.get(id).setIncomingMessage(incomingMessage);
		System.out.println("open id" + id);

	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake arg1) {

		System.out.println("Connection opened");
		System.out.println(arg1.getResourceDescriptor());
		String id = webSocket.toString().substring(
				webSocket.toString().indexOf("@"));
		System.out.println("open id" + id);
		Player player = new Player(id);
		IncomingMessage incomingMessage = new IncomingMessage();
		incomingMessage.setV_x(0);
		incomingMessage.setV_y(0);
		player.setIncomingMessage(incomingMessage);
		player.setWebSocket(webSocket);
		DataStore.playerMap.put(id, player);

	}

}
