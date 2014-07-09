package com.gqs.AndroidClient;

import java.io.IOException;

import android.os.Message;

public class Connect implements Runnable {
	@Override
	public void run() {
		try {
			while (g.socket.isConnected()) {
				String msg = g.br.readLine();
				System.out.println("Connect: "+msg);
				if (msg != null && !msg.equals("")) {
			
					Message message = new Message();
					message.obj = msg;
					g.handler.sendMessage(message);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				g.br.close();
				if(g.socket!=null)
				{
					g.socket.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
