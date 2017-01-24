/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.appmodels;


import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
/**
 *
 * @author k1400284
 */
public class OPCUAVariable
{
    private String id;
    private String identifier;
    private int nsIndex;
    private String type;
    private int serverId;
    private String value;
    
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private DateTime serverTimeStamp;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private DateTime localTimeStamp;

    public DateTime getServerTimeStamp()
    {
        return serverTimeStamp;
    }

    public void setServerTimeStamp(DateTime serverTimeStamp)
    {
        this.serverTimeStamp = serverTimeStamp;
    }

    public DateTime getLocalTimeStamp()
    {
        return localTimeStamp;
    }

    public void setLocalTimeStamp(DateTime localTimeStamp)
    {
        this.localTimeStamp = localTimeStamp;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public int getNsIndex()
    {
        return nsIndex;
    }

    public void setNsIndex(int nsIndex)
    {
        this.nsIndex = nsIndex;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getServerId()
    {
        return serverId;
    }

    public void setServerId(int serverId)
    {
        this.serverId = serverId;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
    
    public WapiceDevice.DataNode ToWapiceDatanode()
    {
        WapiceDevice.DataNode node = new WapiceDevice.DataNode("OPCUA_SERVER:" + String.valueOf(this.id) + "-" + this.identifier, this.value);
        node.setTs(System.currentTimeMillis());
        node.setPath(String.valueOf(this.id) + "/" + this.identifier.replace(".", "/"));
        return node;
    }
}
