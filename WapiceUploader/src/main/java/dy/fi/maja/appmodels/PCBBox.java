/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.appmodels;

/**
 *
 * @author Jarno
 */
public class PCBBox
{
    private int boxPos;
    private int pNo;
    private int type;
    private String description;
    private int oNo;
    private int boxPNo;
    private int boxId;

    public PCBBox(int boxPos, int pNo, int type, String description, int oNo, int boxPNo, int boxId)
    {
        this.boxPos = boxPos;
        this.pNo = pNo;
        this.type = type;
        this.description = description;
        this.oNo = oNo;
        this.boxPNo = boxPNo;
        this.boxId = boxId;
    }

    public int getBoxPos()
    {
        return boxPos;
    }

    public void setBoxPos(int boxPos)
    {
        this.boxPos = boxPos;
    }

    public int getpNo()
    {
        return pNo;
    }

    public void setpNo(int pNo)
    {
        this.pNo = pNo;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
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

    public int getoNo()
    {
        return oNo;
    }

    public void setoNo(int oNo)
    {
        this.oNo = oNo;
    }

    public int getBoxPNo()
    {
        return boxPNo;
    }

    public void setBoxPNo(int boxPNo)
    {
        this.boxPNo = boxPNo;
    }

    public int getBoxId()
    {
        return boxId;
    }

    public void setBoxId(int boxId)
    {
        this.boxId = boxId;
    }
    
    
}
