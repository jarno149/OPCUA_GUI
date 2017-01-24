/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import com.google.gson.Gson;
import dy.fi.maja.DatabaseContentChangedListener.DatabaseContent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Jarno
 */
@Configuration
@EnableWebSocket
public class WebSocketController extends TextWebSocketHandler implements WebSocketConfigurer
{
    private static List<WebSocketSession> existingSessions = new ArrayList<WebSocketSession>();

    public static int SendMessageToAll(String message)
    {
        TextMessage msg = new TextMessage(message);
        int errorCounter = 0;
        
        for(WebSocketSession sess : existingSessions)
        {
            try
            {
                sess.sendMessage(msg);
            }
            catch(Exception e)
            {
                errorCounter += 1;
            }
        }
        return errorCounter;
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
    {
        System.out.println("New websocket connection lost with id: " + session.getId());
        if(existingSessions.contains(session))
            existingSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception
    {
        System.out.println("Not used, Incoming message from " + session.getRemoteAddress().getHostName());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        System.out.println("New websocket connection established with id: " + session.getId());
        if(!existingSessions.contains(session))
            existingSessions.add(session);
        
        // Send data to new session
        sendExistingDataToNewSession();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry wshr)
    {
        System.out.println("Initializing WebSocketHandler. Mapped to: /ws");
        wshr.addHandler(this, "/ws");
    }
    
    private static void sendExistingDataToNewSession()
    {
        Gson g = new Gson();
        String highbayRackJson = g.toJson(DatabaseContent.highBayRacks);
        SendMessageToAll(highbayRackJson);
        
        String pcbTrayJson = g.toJson(DatabaseContent.pCBBoxes);
        SendMessageToAll(pcbTrayJson);
    }
}
