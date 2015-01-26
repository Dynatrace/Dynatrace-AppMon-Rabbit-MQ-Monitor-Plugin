package com.RabbitMQ.RabbitMQMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SingleNode {
	
	public String Name = "";
	public int Running = 0;
	public int Mem_Alarm = 0;
	public long Mem_Used = 0;
	public int Disk_Alarm = 0;
	public long Disk_Free = 0;
	
	public SingleNode(String Name2, int run, int mema, long memuse, int diska, long diskf) {
		Name = Name2;
		Running = run;
		Mem_Alarm = mema;
		Mem_Used = memuse;
		Disk_Alarm = diska;
		Disk_Free = diskf;
	}
}