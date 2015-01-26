package com.RabbitMQ.RabbitMQMonitor;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dynatrace.diagnostics.pdk.Monitor;
import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.diagnostics.pdk.Status;

public class RabbitMQMonitor implements Monitor {

	private static final String METRIC_GROUP_OVERVIEW = "RabbitMQ Overview";
	private static final String METRIC_GROUP_NODE = "RabbitMQ Nodes";
	private static final String METRIC_GROUP_QUEUE = "RabbitMQ Queue";
	private static final String MSR_OVER_MSG = "Messages";
	private static final String MSR_OVER_MSGRAT = "Messages Rate";
	private static final String MSR_OVER_MSGREADY = "Messages Ready";
	private static final String MSR_OVER_MSGREDRAT = "Messages Ready Rate";
	private static final String MSR_OVER_MSGUNACK = "Messages Unacknowledged";
	private static final String MSR_OVER_MSGUNRAT = "Messages Unacknowledged Rate";
	private static final String MSR_NODE_DALARAM = "Disk Alarm";
	private static final String MSR_NODE_DFREE = "Disk Free";
	private static final String MSR_NODE_MEMALARM = "Memory Alarm";
	private static final String MSR_NODE_MEMUSED = "Memory Used";
	private static final String MSR_NODE_RUN = "Node Running";
	private static final String MSR_QUEUE_MSG = "Messages";
	private static final String MSR_QUEUE_MSGREADY = "Messages Ready";
	private static final String MSR_QUEUE_MSGUNACK = "Messages Unacknowledged";
	private static final String MSR_QUEUE_CONSUMER = "Consumers";

	private static final Logger log = Logger.getLogger(RabbitMQMonitor.class
			.getName());

	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		// TODO
		return new Status(Status.StatusCode.Success);
	}

	@Override
	public Status execute(MonitorEnvironment env) throws Exception {

		try {

			log.fine("Staring execute...");

			String Server = env.getHost().getAddress();

			log.fine("Server : " + Server);

			String Port = env.getConfigString("Port");

			log.fine("Port : " + Port);

			String User = env.getConfigString("Username");

			log.fine("User : " + User);

			String Pass = env.getConfigPassword("Password");

			Overview temp = new Overview(Server, Port, User, Pass);
			Nodes temp2 = new Nodes(Server, Port, User, Pass);
			Collection<MonitorMeasure> measures;
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_OVERVIEW,
					MSR_OVER_MSGRAT)) != null) {
				for (MonitorMeasure measure : measures) {
					measure.setValue(temp.get_messRate());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_OVERVIEW,
					MSR_OVER_MSGREDRAT)) != null) {
				for (MonitorMeasure measure : measures) {
					measure.setValue(temp.get_redRate());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_OVERVIEW,
					MSR_OVER_MSGUNRAT)) != null) {
				for (MonitorMeasure measure : measures) {
					measure.setValue(temp.get_unRate());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_OVERVIEW,
					MSR_OVER_MSG)) != null) {
				for (MonitorMeasure measure : measures) {
					measure.setValue(temp.getMessages());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_OVERVIEW,
					MSR_OVER_MSGREADY)) != null) {
				for (MonitorMeasure measure : measures) {
					measure.setValue(temp.getMessages_Ready());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_OVERVIEW,
					MSR_OVER_MSGUNACK)) != null) {
				for (MonitorMeasure measure : measures) {
					measure.setValue(temp.getMessages_Unack());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_NODE,
					MSR_NODE_RUN)) != null) {
				for (MonitorMeasure measure : measures) {
					String Name = measure.getParameter("Name");
					measure.setValue(temp2.getRunning(Name));
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_NODE,
					MSR_NODE_DALARAM)) != null) {
				for (MonitorMeasure measure : measures) {
					String Name = measure.getParameter("Name");
					measure.setValue(temp2.getDisk_Alarm(Name));
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_NODE,
					MSR_NODE_DFREE)) != null) {
				for (MonitorMeasure measure : measures) {
					String Name = measure.getParameter("Name");
					measure.setValue(temp2.getDisk_Free(Name));
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_NODE,
					MSR_NODE_MEMALARM)) != null) {
				for (MonitorMeasure measure : measures) {
					String Name = measure.getParameter("Name");
					measure.setValue(temp2.getMem_Alarm(Name));
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_NODE,
					MSR_NODE_MEMUSED)) != null) {
				for (MonitorMeasure measure : measures) {
					String Name = measure.getParameter("Name");
					measure.setValue(temp2.getMem_Used(Name));
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_QUEUE,
					MSR_QUEUE_CONSUMER)) != null) {
				for (MonitorMeasure measure : measures) {
					String VHost = measure.getParameter("Vhost");
					String Queue = measure.getParameter("Queue");
					Queue temp3 = new Queue(Server, Port, User, Pass, VHost,
							Queue);
					measure.setValue(temp3.getConsumers());
				}
			}
			/*
			 * if((measures =
			 * env.getMonitorMeasures(METRIC_GROUP_QUEUE,MSR_QUEUE_ACTCONSUMER))
			 * != null) { for (MonitorMeasure measure : measures) { String VHost
			 * = measure.getParameter("Vhost"); String Queue =
			 * measure.getParameter("Queue"); Queue temp3 = new
			 * Queue(Server,Port,User,Pass,VHost,Queue);
			 * measure.setValue(temp3.getActive_Consumers()); } }
			 */
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_QUEUE,
					MSR_QUEUE_MSG)) != null) {
				for (MonitorMeasure measure : measures) {
					String VHost = measure.getParameter("Vhost");
					String Queue = measure.getParameter("Queue");
					Queue temp3 = new Queue(Server, Port, User, Pass, VHost,
							Queue);
					measure.setValue(temp3.getMessages());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_QUEUE,
					MSR_QUEUE_MSGREADY)) != null) {
				for (MonitorMeasure measure : measures) {
					String VHost = measure.getParameter("Vhost");
					String Queue = measure.getParameter("Queue");
					Queue temp3 = new Queue(Server, Port, User, Pass, VHost,
							Queue);
					measure.setValue(temp3.getMessages_Ready());
				}
			}
			if ((measures = env.getMonitorMeasures(METRIC_GROUP_QUEUE,
					MSR_QUEUE_MSGUNACK)) != null) {
				for (MonitorMeasure measure : measures) {
					String VHost = measure.getParameter("Vhost");
					String Queue = measure.getParameter("Queue");
					Queue temp3 = new Queue(Server, Port, User, Pass, VHost,
							Queue);
					measure.setValue(temp3.getMessages_Unack());
				}
			}
			return new Status(Status.StatusCode.Success);

		} catch (Exception exp) {
			log.log(Level.SEVERE, exp.getMessage(), exp);
			throw exp;
		}
	}

	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
		// TODO
	}
}
