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
public class ManuFacturingOrder
{
    @JsonProperty("ONo")
    private int oNo;
    @JsonProperty("OPos")
    private int oPos;
    @JsonProperty("PNo")
    private int pNo;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("PlanedStart")
    private Date planedStart;
    @JsonProperty("PlanedEnd")
    private Date planedEnd;
    @JsonProperty("Start")
    private Date start;
    @JsonProperty("End")
    private Date end;
    @JsonProperty("State")
    private int state;
    @JsonProperty("Error")
    private boolean error;

    public ManuFacturingOrder(int oNo, int oPos, int pNo, String description, Date planedStart, Date planedEnd, Date start, Date end, int state, boolean error)
    {
        this.oNo = oNo;
        this.oPos = oPos;
        this.pNo = pNo;
        this.description = description;
        this.planedStart = planedStart;
        this.planedEnd = planedEnd;
        this.start = start;
        this.end = end;
        this.state = state;
        this.error = error;
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

    public int getpNo()
    {
        return pNo;
    }

    public void setpNo(int pNo)
    {
        this.pNo = pNo;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getPlanedStart()
    {
        return planedStart;
    }

    public void setPlanedStart(Date planedStart)
    {
        this.planedStart = planedStart;
    }

    public Date getPlanedEnd()
    {
        return planedEnd;
    }

    public void setPlanedEnd(Date planedEnd)
    {
        this.planedEnd = planedEnd;
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

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }
    
    
}
