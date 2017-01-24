/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.appmodels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;

/**
 *
 * @author Jarno
 */
public class WapiceDevice
{
    private String deviceId;
    private String href;
    private String name;
    private String manufacturer;
    private String type;
    private String description;
    private Attribute[] attributes;

    public WapiceDevice(String name, String manufacturer)
    {
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Attribute[] getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Attribute[] attributes)
    {
        this.attributes = attributes;
    }
    
    public String toJsonString()
    {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
    
    public static class Attribute
    {
        private String key;
        private String value;

        public String getKey()
        {
            return key;
        }

        public void setKey(String key)
        {
            this.key = key;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
        
        
    }
    
    public static class DataNode
    {
        private String name;
        private String path;
        private String v;
        private long ts;
        private String unit;
        private String dataType;

        public DataNode(String name, String v)
        {
            this.name = name;
            this.v = v;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getPath()
        {
            return path;
        }

        public void setPath(String path)
        {
            this.path = path;
        }

        public String getV()
        {
            return v;
        }

        public void setV(String v)
        {
            this.v = v;
        }

        public long getTs()
        {
            return ts;
        }

        public void setTs(long ts)
        {
            this.ts = ts;
        }

        public String getUnit()
        {
            return unit;
        }

        public void setUnit(String unit)
        {
            this.unit = unit;
        }

        public String getDataType()
        {
            return dataType;
        }

        public void setDataType(String dataType)
        {
            this.dataType = dataType;
        }
        
        public String toJsonString()
        {
            return new Gson().toJson(this);
        }
    }
}
