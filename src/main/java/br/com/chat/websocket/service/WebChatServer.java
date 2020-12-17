package br.com.chat.websocket.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import br.com.chat.websocket.domain.Message;
import br.com.chat.websocket.domain.MessageDecoder;
import br.com.chat.websocket.domain.MessageEncoder;

@Component
@ServerEndpoint(value="/chat/{username}",
decoders = MessageDecoder.class, 
encoders = MessageEncoder.class)
public class WebChatServer {
	
	private static Set<Session> users = new CopyOnWriteArraySet<>();
	
	private static HashMap<String, String> onlineuser = new HashMap<>();
	
	/**
     * All chat sessions.
	 * @throws EncodeException 
	 * @throws IOException 
     */

    private static void sendMessageToAll(Message msg) throws IOException, EncodeException {
        for (Session session : users) {
			session.getBasicRemote().sendObject(msg);
		}
    }

    /**
     * Open connection, 1) add session, 2) add user.
     * @throws IOException 
     */
	
	@OnOpen
	public void onOpen(Session session,@PathParam("username") String username) throws IOException {
		onlineuser.put(session.getId(), username);
		users.add(session);
	}
	 

    /**
     * Send message, 1) get username and session, 2) send message to all.
     * @throws IOException 
     * @throws EncodeException 
     */
    @OnMessage
    public void onMessage(Session session,Message message) throws IOException, EncodeException {
    	message.setOnlineCount(onlineuser.size());
    	message.setType("SPEAK");
    	sendMessageToAll(message);
    }
    /**
     * Close connection, 1) remove session, 2) update user.
     * @throws IOException 
     */
    @OnClose
    public void onClose(Session session) throws IOException {
    	Session retu = users.stream().filter(s-> s.getId().equals(session.getId())).findFirst().orElse(null);
    	if(retu != null) {
    		retu.close();
    		session.close();
    		users.remove(session);
    		onlineuser.remove(session.getId());
    	}
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
