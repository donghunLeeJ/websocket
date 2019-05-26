import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	@OnOpen
	public void onOpen(Session session){
		System.out.println(session.getId());
		
		System.out.println(session);
		System.out.println("Open Connection ...");
		clients.add(session);
	}

	@OnClose
	public void onClose(){
		System.out.println("Close Connection ...");
	}

	@OnMessage
	public void onMessage(String message,Session session) throws IOException{
		synchronized (clients) {
			for(Session client : clients) {
				if(!client.equals(session)) {
					
					client.getBasicRemote().sendText(session.getId()+"님의 메세지"+message);
				}
			}
		}
		
		
	}
	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	
}

