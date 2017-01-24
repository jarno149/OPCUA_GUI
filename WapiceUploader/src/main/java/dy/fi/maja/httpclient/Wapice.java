/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dy.fi.maja.appmodels.HighBayRack;
import dy.fi.maja.appmodels.ManuFacturingOrder;
import dy.fi.maja.appmodels.PCBBox;
import dy.fi.maja.appmodels.WapiceDevice;
import dy.fi.maja.appmodels.WapiceDevice.DataNode;
import dy.fi.maja.appmodels.WorkOrder;
import java.util.Optional;

/**
 *
 * @author Jarno
 */
public class Wapice
{
    public static Wapice wapiceClient;
    
    private HttpClient client;
    
    public static void initialize(String baseurl, String username, String password)
    {
        wapiceClient = new Wapice(baseurl, username, password);
    }

    public Wapice(String baseurl, String username, String password)
    {
        client = new HttpClient(baseurl);
        client.setCredentials(username, password);
    }
    
    public WapiceDevice registerNew(WapiceDevice device)
    {
        String registeredDeviceString = client.post("devices/", device.toJsonString(), String.class);
        JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(registeredDeviceString).getAsJsonObject();
        String id = jo.get("deviceId").getAsString();
        String href = jo.get("href").getAsString();
        
        device.setDeviceId(id);
        device.setHref(href);
        
        return device;
    }
    
    public WapiceDevice[] getDevices()
    {
        WapiceDevice[] existingDevices = client.get("devices/", WapiceDevice[].class);
        return existingDevices;
    }
    
    public WapiceDevice[] getDevices(int limit)
    {
        String existingDevicesString = client.get("devices?limit=" + String.valueOf(limit), String.class);
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(existingDevicesString).getAsJsonObject();
        JsonArray array = rootObject.getAsJsonArray("items");
        return new Gson().fromJson(array, WapiceDevice[].class);
    }
    
    public int getDeviceCount()
    {
        String responseString = client.get("devices?limit=99999", String.class);
        JsonParser parser = new JsonParser();
        return parser.parse(responseString).getAsJsonObject().get("fullSize").getAsInt();
    }
    
    public WapiceDevice getSingleDevice(String deviceId)
    {
        WapiceDevice existingDevice = client.get("devices/" + deviceId, WapiceDevice.class);
        return existingDevice;
    }
    
    public static void writeHighBayRack(String deviceId, HighBayRack[] racks)
    {
        // SEND DATA TO WAPICE
        for (HighBayRack rack : racks)
        {
            String path = "HighBayRack/" + String.valueOf(rack.getBufPos()) + "/";
            String name = "HighBayRack_" + String.valueOf(rack.getBufPos());
            DataNode node = new DataNode(name, String.valueOf(rack.getpNo()));
            node.setPath(path + "PNo");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, rack.getDescription());
            node.setPath(path + "Description");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
        }
    }
    
    public static void writeWorkOrders(String deviceId, WorkOrder[] orders)
    {
        // SEND DATA TO WAPICE
        for (WorkOrder order : orders)
        {
            String path = "WorkOrder/" + String.valueOf(order.getoNo()) + "/";
            String name = "WorkOrder_" + String.valueOf(order.getoNo());
            DataNode node = new DataNode(name, String.valueOf(order.getoPos()));
            node.setPath(path + "OPos");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getStepNo()));
            node.setPath(path + "StepNo");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getResourceId()));
            node.setPath(path + "ResourceID");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getOpNo()));
            node.setPath(path + "OpNo");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getDescription()));
            node.setPath(path + "Description");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getStart().getTime()));
            node.setPath(path + "Start");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getEnd().getTime()));
            node.setPath(path + "End");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
        }
    }
    
    public static void writeManufacturingOrders(String deviceId, ManuFacturingOrder[] orders)
    {
        // SEND DATA TO WAPICE
        for (ManuFacturingOrder order : orders)
        {
            String path = "ManufacturingOrder/" + String.valueOf(order.getoNo()) + "/";
            String name = "ManufacturingOrder_" + String.valueOf(order.getoNo());
            DataNode node = new DataNode(name, String.valueOf(order.getoPos()));
            node.setPath(path + "OPos");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, order.getDescription());
            node.setPath(path + "Description");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getPlanedStart().getTime()));
            node.setPath(path + "PlanedStart");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getPlanedEnd().getTime()));
            node.setPath(path + "PlanedEnd");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getStart().getTime()));
            node.setPath(path + "Start");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getEnd().getTime()));
            node.setPath(path + "End");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(order.getState()));
            node.setPath(path + "State");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
        }
    }
    
    public static void writePCBBoxes(String deviceId, PCBBox[] boxes)
    {
        // SEND DATA TO WAPICE
        for (PCBBox box : boxes)
        {
            String path = "PCBBox/" + String.valueOf(box.getBoxPos()) + "/";
            String name = "PCBBox_" + String.valueOf(box.getBoxPos());
            DataNode node = new DataNode(name, String.valueOf(box.getBoxPNo()));
            node.setPath(path + "PNo");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(box.getType()));
            node.setPath(path + "Type");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, box.getDescription());
            node.setPath(path + "Description");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(box.getBoxPNo()));
            node.setPath(path + "BoxPNo");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
            node = new DataNode(name, String.valueOf(box.getBoxId()));
            node.setPath(path + "BoxID");
            node.setTs(System.currentTimeMillis());
            Wapice.wapiceClient.writeDatanode(deviceId, node);
        }
    }
    
    public void writeDatanode(String deviceId, DataNode data)
    {
        DatanodeWriter writer = new DatanodeWriter(client, deviceId, data);
        writer.run();
    }
    
    private static class DatanodeWriter implements Runnable
    {
        private HttpClient client;
        private String deviceId;
        private DataNode data;

        public DatanodeWriter(HttpClient client, String deviceId, DataNode data)
        {
            this.client = client;
            this.deviceId = deviceId;
            this.data = data;
        }
        
        @Override
        public void run()
        {
            client.post("process/write/" + deviceId + "/", data.toJsonString());
        }
    }
}
