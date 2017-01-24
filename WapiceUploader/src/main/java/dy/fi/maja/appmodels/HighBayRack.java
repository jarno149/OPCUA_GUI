/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.appmodels;

import java.sql.Timestamp;
import org.codehaus.jackson.annotate.JsonProperty;


public class HighBayRack
{
    @JsonProperty("BufPos")
    private int bufPos;
    @JsonProperty("PNo")
    private int pNo;
    @JsonProperty("Zone")
    private int Zone;
    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("TimeStamp")
    private Timestamp timeStamp;
    @JsonProperty("Description")
    private String description;

    public HighBayRack(int bufPos, int pNo, int Zone, int quantity, Timestamp timeStamp, String description)
    {
        this.bufPos = bufPos;
        this.pNo = pNo;
        this.Zone = Zone;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
        this.description = description;
    }

    public int getBufPos()
    {
        return bufPos;
    }

    public void setBufPos(int bufPos)
    {
        this.bufPos = bufPos;
    }

    public int getpNo()
    {
        return pNo;
    }

    public void setpNo(int pNo)
    {
        this.pNo = pNo;
    }

    public int getZone()
    {
        return Zone;
    }

    public void setZone(int Zone)
    {
        this.Zone = Zone;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Timestamp getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
