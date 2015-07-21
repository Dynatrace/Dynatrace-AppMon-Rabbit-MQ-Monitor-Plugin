##Rabbit MQ Monitor Plugin

![images_community/download/attachments/119079240/icon.png](images_community/download/attachments/119079240/icon.png)
The monitor returns a number of values associated with RabbitMQ. The RabbitMQ Monitor makes 2 initial calls to the RabbitMQ rest interface which gets the overview and node information.    
    
The plug-in then uses the JSon-simple library to parse the JSon script returned from the rest interface into an overview and nodes object. The overview object contains the values from the overview of the RabbitMQ Server. The Nodes object contains an array of Node objects. One Node object is created for each node returned in the JSon script. The Node is used to hold information about individual nodes.    
    
Finally the monitor iterates through the measures and uses the correct objects to match each measure. If a Node measure is created the Node Name is a required field in the Measure configuration. This will tell the Nodes object which Node to pull the information from. If a Queue Measure is created the monitor will make an additional call to the RabbitMQ rest interface to retrieve data about the Queue specified in the measure configuration. 

Find further information in the [dynaTrace community](https://community.dynatrace.com/community/display/DL/Rabbit+MQ+Monitor+Plugin)  
    