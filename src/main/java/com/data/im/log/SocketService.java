package com.data.im.log;

import java.io.IOException;


import org.springframework.stereotype.Service;

import com.data.websocket.WebSocketServer;

@Service
public class SocketService {
//	@PostConstruct
	public void set(){
		try {
			while (true) {
				WebSocketServer.sendInfo(LogContainer.getLog());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
