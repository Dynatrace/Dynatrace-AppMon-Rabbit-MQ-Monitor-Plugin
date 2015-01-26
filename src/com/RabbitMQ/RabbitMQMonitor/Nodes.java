package com.RabbitMQ.RabbitMQMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Nodes {

	private int Running = 0;
	private int Mem_Alarm = 0;
	private long Mem_Used = 0;
	private int Disk_Alarm = 0;
	private long Disk_Free = 0;
	private SingleNode[] holder;

	private static final Logger log = Logger.getLogger(Nodes.class.getName());

	public Nodes(String server, String port, final String Username,
			final String Password) throws IOException, ParseException {

		log.fine("Going to make REST call for Nodes");

		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Username, Password
						.toCharArray());
			}
		});
		JSONParser parser = new JSONParser();
		String rabbitURL = "http://" + server + ":" + port + "/api/nodes";

		log.fine("REST URL for Nodes : " + rabbitURL);

		URL rabbitMQ = new URL(rabbitURL);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				rabbitMQ.openStream()));
		String finalline = "";
		String inputLine = "";
		while ((inputLine = in.readLine()) != null) {
			// System.out.println(inputLine);
			finalline += inputLine;
		}
		in.close();

		log.fine("Response for Nodes : " + finalline);

		Object obj = parser.parse(finalline);
		JSONArray array = (JSONArray) obj;
		holder = new SingleNode[array.size()];
		for (int q = 0; q < array.size(); q++) {
			JSONObject obj2 = (JSONObject) array.get(q);
			String NodeName = obj2.get("name").toString();
			Running = 0;
			Mem_Alarm = 0;
			Mem_Used = 0;
			Disk_Alarm = 0;
			Disk_Free = 0;
			if (obj2.get("running").toString().equals("true")) {
				Running = 1;
			}
			if (obj2.get("mem_alarm").toString().equals("true")) {
				Mem_Alarm = 1;
			}
			if (obj2.get("disk_free_alarm").toString().equals("true")) {
				Disk_Alarm = 1;
			}
			Disk_Free = Long.parseLong(obj2.get("disk_free").toString());
			Mem_Used = Long.parseLong(obj2.get("mem_used").toString());
			holder[q] = new SingleNode(NodeName, Running, Mem_Alarm, Mem_Used,
					Disk_Alarm, Disk_Free);
		}
	}

	public int getRunning(String check) {
		for (int x = 0; x < holder.length; x++) {
			if (holder[x].Name.equals(check)) {
				return holder[x].Running;
			}
		}
		return 0;
	}

	public int getMem_Alarm(String check) {
		for (int x = 0; x < holder.length; x++) {
			if (holder[x].Name.equals(check)) {
				return holder[x].Mem_Alarm;
			}
		}
		return 0;
	}

	public long getMem_Used(String check) {
		for (int x = 0; x < holder.length; x++) {
			if (holder[x].Name.equals(check)) {
				return holder[x].Mem_Used;
			}
		}
		return 0;
	}

	public int getDisk_Alarm(String check) {
		for (int x = 0; x < holder.length; x++) {
			if (holder[x].Name.equals(check)) {
				return holder[x].Disk_Alarm;
			}
		}
		return 0;
	}

	public long getDisk_Free(String check) {
		for (int x = 0; x < holder.length; x++) {
			if (holder[x].Name.equals(check)) {
				return holder[x].Disk_Free;
			}
		}
		return 0;
	}
}