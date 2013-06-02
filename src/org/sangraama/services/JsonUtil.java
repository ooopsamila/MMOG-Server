package org.sangraama.services;

import java.util.List;

import com.google.gson.Gson;

public class JsonUtil {
	private static Gson gson = new Gson();

	public static String toJson(OutgoingMessage outgoingMessage) {
		return gson.toJson(outgoingMessage);
	}

	public static IncomingMessage jsonToObject(String str) {
		return gson.fromJson(str, IncomingMessage.class);
	}

	public static String listToJson(List<OutgoingMessage> outgoingMessages) {
		return gson.toJson(outgoingMessages);
	}
}
