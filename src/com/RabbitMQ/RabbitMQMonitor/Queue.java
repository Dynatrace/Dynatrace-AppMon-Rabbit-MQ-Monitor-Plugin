package com.RabbitMQ.RabbitMQMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Queue {
	
	private int Consumers = 0;
	//private int Active_Consumers = 0;
	private long Messages = 0;
	private long Messages_Ready = 0;
	private long Messages_Unack = 0;
	
	private static final Logger log = Logger.getLogger(Queue.class.getName());
	
	public Queue(String server, String port, final String Username, final String Password, String Vhost, String Queue) throws IOException, ParseException{
		
		log.fine("Going to make REST call for Queue");
		
		Authenticator.setDefault (new Authenticator() {
    	    protected PasswordAuthentication getPasswordAuthentication() {
    	        return new PasswordAuthentication (Username, Password.toCharArray());
    	    }
    	});
		JSONParser parser = new JSONParser();
		String rabbitURL = "http://" + server + ":" + port + "/api/queues/" + Vhost + "/" + Queue;
		
		log.fine("REST URL for Queue : " + rabbitURL);
		
        URL rabbitMQ = new URL(rabbitURL);
        BufferedReader in = new BufferedReader(new InputStreamReader(rabbitMQ.openStream()));
        String finalline = "";
        String inputLine = "";
        while ((inputLine = in.readLine()) != null)
        {
                //System.out.println(inputLine);
                finalline += inputLine;
        }
        in.close();
        
        log.fine("REST response for Queue : " + finalline);
        
        Object tempq = parser.parse(finalline);
        JSONObject objqueue = (JSONObject) tempq;
        
        try
        {
        	Consumers = Integer.parseInt(objqueue.get("consumers").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }        	
        //Active_Consumers = Integer.parseInt(objqueue.get("active_consumers").toString());
        
        try
        {
        	Messages = Long.parseLong(objqueue.get("messages").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }
        
        try
        {
        	Messages_Ready = Long.parseLong(objqueue.get("messages_ready").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }
        
        try
        {
        	Messages_Unack = Long.parseLong(objqueue.get("messages_unacknowledged").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }        	
	}
	public int getConsumers()
	{
		return Consumers;
	}
/*	public int getActive_Consumers()
	{
		return Consumers;
	}*/
	public long getMessages()
	{
		return Messages;
	}
	public long getMessages_Ready()
	{
		return Messages_Ready;
	}
	public long getMessages_Unack()
	{
		return Messages_Unack;
	}
}