package introsde.endpoint;

import javax.xml.ws.Endpoint;
import introsde.ws.DataBaseServiceImpl;
//Endpoint publisher
public class DataBaseServicePublisher{
    public static void main(String[] args) {
       Endpoint.publish("http://localhost:6903/ws/database", new DataBaseServiceImpl());
    }
}
