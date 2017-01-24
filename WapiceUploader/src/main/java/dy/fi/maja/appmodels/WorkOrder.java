/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.appmodels;

import java.sql.Date;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Jarno
 */
public class WorkOrder
{
    @JsonProperty("ONo")
    private int oNo;
    @JsonProperty("OPos")
    private int oPos;
    @JsonProperty("StepNo")
    private int stepNo;
    @JsonProperty("ResourceID")
    private int resourceId;
    @JsonProperty("OpNo")
    private int opNo;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Start")
    private Date start;
    @JsonProperty("End")
    private Date end;

    public WorkOrder(int oNo, int oPos, int stepNo, int resourceId, int opNo, String description, Date start, Date end)
    {
        this.oNo = oNo;
        this.oPos = oPos;
        this.stepNo = stepNo;
        this.resourceId = resourceId;
        this.opNo = opNo;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public int getoNo()
    {
        return oNo;
    }

    public void setoNo(int oNo)
    {
        this.oNo = oNo;
    }

    public int getoPos()
    {
        return oPos;
    }

    public void setoPos(int oPos)
    {
        this.oPos = oPos;
    }

    public int getStepNo()
    {
        return stepNo;
    }

    public void setStepNo(int stepNo)
    {
        this.stepNo = stepNo;
    }

    public int getResourceId()
    {
        return resourceId;
    }

    public void setResourceId(int resourceId)
    {
        this.resourceId = resourceId;
    }

    public int getOpNo()
    {
        return opNo;
    }

    public void setOpNo(int opNo)
    {
        this.opNo = opNo;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }
    
    
}
