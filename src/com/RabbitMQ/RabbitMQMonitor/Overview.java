package com.RabbitMQ.RabbitMQMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Overview {
	
	private long Messages = 0;
	private long Messages_Ready = 0;
	private long Messages_Unack = 0;
	private double messRate = 0;
	private double redRate = 0;
	private double unRate = 0;
	
	private static final Logger log = Logger.getLogger(Overview.class.getName());
	
	public Overview(String server, String port, final String Username, final String Password) throws IOException, ParseException{
		
		log.fine("Going to make REST call for Overview");
		
		Authenticator.setDefault (new Authenticator() {
    	    protected PasswordAuthentication getPasswordAuthentication() {
    	        return new PasswordAuthentication (Username, Password.toCharArray());
    	    }
    	});
		JSONParser parser = new JSONParser();
		String rabbitURL = "http://" + server + ":" + port + "/api/overview";
		
		log.fine("REST URL for Overview : " + rabbitURL);
		
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
        
        log.fine("REST result for Overview : " + finalline);
        
        Object objover = parser.parse(finalline);
        JSONObject theover= (JSONObject) objover;
        Object temp = parser.parse(theover.get("queue_totals").toString());
        JSONObject queueover = (JSONObject) temp;
        temp = parser.parse(queueover.get("messages_details").toString());
        JSONObject messDetail = (JSONObject) temp;
        temp = parser.parse(queueover.get("messages_ready_details").toString());
        JSONObject redDetail = (JSONObject) temp;
        temp = parser.parse(queueover.get("messages_unacknowledged_details").toString());
        JSONObject unDetail = (JSONObject) temp;
        
        
        try
        {
        	Messages = Long.parseLong(queueover.get("messages").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }
        
        try{
        	
        	messRate = Double.parseDouble(messDetail.get("rate").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }
        
        try
        {
        	Messages_Ready = Long.parseLong(queueover.get("messages_ready").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }
        
        try{
        	redRate = Double.parseDouble(redDetail.get("rate").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }   
        
        try
        {
        	Messages_Unack = Long.parseLong(queueover.get("messages_unacknowledged").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }
        
        try
        {
        	unRate = Double.parseDouble(unDetail.get("rate").toString());
        }catch(Exception exp)
        {
        	log.log(Level.SEVERE, exp.getMessage(), exp);
        }        	
	}
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
	public double get_messRate()
	{
		return messRate;
	}
	public double get_redRate()
	{
		return redRate;
	}
	public double get_unRate()
	{
		return unRate;
	}
}