package com.ucams.modules.ams.utils;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucams.common.config.Global;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年12月15日
 * socket 
 */
public class SocketIoUtils {
	
	
	/**
	 * 给多用户发送新消息
	 * @param userList 用户集合
	 */
	public static void sendNewsAsUsers(List<String> userList){
		try {
			Socket socket =  IO.socket(Global.getConfig("socket.io.nodejs.url"));
			socket.connect();
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for(String userId : userList){
				array.add(userId);
			}
			obj.put("u_id", array);
			socket.emit("usersNews", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向房间发送消息
	 * @param roomsList
	 * @param 房间ID集合(角色英文名)
	 */
	public static void sendNewsAsRooms(List<String> roomsList){
		try {
			Socket socket =  IO.socket(Global.getConfig("socket.io.nodejs.url"));
			socket.connect();
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for(String room : roomsList){
				array.add(room);
			}
			obj.put("roomName", array);
			socket.emit("roomNews", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		try {
//			Socket socket =  IO.socket(Global.getConfig("socket.io.nodejs.url"));
//			socket.connect();
//			JSONObject obj = new JSONObject();
//			JSONArray array = new JSONArray();
//			array.add("daguser");
//			obj.put("roomName", array);
//			socket.emit("roomNews", obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
