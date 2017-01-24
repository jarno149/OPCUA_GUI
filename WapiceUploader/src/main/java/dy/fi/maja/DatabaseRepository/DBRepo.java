/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.DatabaseRepository;

import dy.fi.maja.appmodels.HighBayRack;
import dy.fi.maja.appmodels.ManuFacturingOrder;
import dy.fi.maja.appmodels.PCBBox;
import dy.fi.maja.appmodels.WorkOrder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Jarno
 */
public class DBRepo
{
    private static String filePath;
    
    public static void initialize(String filePath)
    {
        DBRepo.filePath = filePath;
    }
    
    private static Statement getStatement()
    {
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + filePath);
            Statement statement = conn.createStatement();
            return statement;
        }
        catch (Exception e)
        {
            System.out.println("Cannot open connection to: " + filePath);
        }
        return null;
    }
    
    private static ResultSet executeQuery(String query)
    {
        Statement stat = getStatement();
        try
        {
            ResultSet resultSet = stat.executeQuery(query);
            return resultSet;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PCBBox[] getPCBBoxes()
    {
        ResultSet result = executeQuery("SELECT * FROM tblBoxPos INNER JOIN tblParts ON tblBoxPos.PNo = tblParts.PNo WHERE tblBoxPos.BoxId = 1");
        
        if(result == null)
            return null;
        
        ArrayList<PCBBox> boxes = new ArrayList<>();
        try
        {
            while(result.next())
            {
                int boxPos = result.getInt("BoxPos");
                int pNo = result.getInt("PNo");
                int type = result.getInt("Type");
                String description = result.getString("Description");
                int oNo = result.getInt("ONo");
                int boxPNo = result.getInt("BoxPNo");
                int boxId = result.getInt("BoxId");
                
                PCBBox box = new PCBBox(boxPos, pNo, type, description, oNo, boxPNo, boxId);
                boxes.add(box);
            }
            return boxes.toArray(new PCBBox[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new PCBBox[0];
    }
    
    public static WorkOrder[] getWorkOrders()
    {
        return getWorkOrders(-1);
    }
    
    public static WorkOrder[] getWorkOrders(int resourceId)
    {
        String queryString;
        if(resourceId < 0)
            queryString = "SELECT tblStep.ONo, tblStep.OPos, tblStep.StepNo, tblStep.ResourceID, tblStep.OpNo, tblStep.Start, tblStep.End, tblOperation.Description FROM tblStep INNER JOIN tblOperation ON tblStep.OpNo = tblOperation.OpNo WHERE tblStep.ResourceId = " + String.valueOf(resourceId);
        else
            queryString = "SELECT tblStep.ONo, tblStep.OPos, tblStep.StepNo, tblStep.ResourceID, tblStep.OpNo, tblStep.Start, tblStep.End, tblOperation.Description FROM tblStep INNER JOIN tblOperation ON tblStep.OpNo = tblOperation.OpNo";
        ResultSet result = executeQuery(queryString);
    
        if(result == null)
            return null;
        
        ArrayList<WorkOrder> orders = new ArrayList<>();
        try
        {
            while(result.next())
            {
                int oNo = result.getInt("ONo");
                int oPos = result.getInt("OPos");
                int stepNo = result.getInt("StepNo");
                int resId = result.getInt("ResourceID");
                int opNo = result.getInt("OpNo");
                String description = result.getString("Description");
                Date start = result.getDate("Start");
                Date end = result.getDate("End");
                
                WorkOrder order = new WorkOrder(oNo, oPos, stepNo, resId, opNo, description, start, end);
                orders.add(order);
            }
            return orders.toArray(new WorkOrder[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new WorkOrder[0];
    }
    
    public static ManuFacturingOrder[] getManuFacturingOrders()
    {
        ResultSet result = executeQuery("SELECT * FROM tblOrder INNER JOIN tblOrderPos ON tblOrder.ONo = tblOrderPos.ONo INNER JOIN tblParts ON tblParts.PNo = tblOrderPos.PNo ORDER BY tblOrder.ONo");
        
        if(result == null)
            return null;
        
        ArrayList<ManuFacturingOrder> orders = new ArrayList<>();
        try
        {
            while(result.next())
            {
                int oNo = result.getInt("ONo");
                int oPos = result.getInt("OPos");
                int pNo = result.getInt("PNo");
                String description = result.getString("Description");
                Date planedStart = result.getDate("PlanedStart");
                Date planedEnd = result.getDate("PlanedEnd");
                Date start = result.getDate("Start");
                Date end = result.getDate("End");
                int state = result.getInt("State");
                boolean error = result.getBoolean("Error");
                
                ManuFacturingOrder order = new ManuFacturingOrder(oNo, oPos, pNo, description, planedStart, planedEnd, start, end, state, error);
                orders.add(order);
            }
            return orders.toArray(new ManuFacturingOrder[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ManuFacturingOrder[0];
    }
    
    public static HighBayRack[] getHighBayRacks()
    {
        ResultSet result = executeQuery("SELECT * FROM tblBufferPos INNER JOIN tblParts ON tblBufferPos.PNo = tblParts.PNo WHERE tblBufferPos.ResourceId = 61 ORDER BY tblBufferPos.BufPos");
      
        if(result == null)
            return null;
        ArrayList<HighBayRack> racks = new ArrayList<>();
        try
        {
            while(result.next())
            {
                int pos = result.getInt("BufPos");
                int pno = result.getInt("PNo");
                int Zone = result.getInt("Zone");
                int quantity = result.getInt("Quantity");
                Timestamp timeStamp = result.getTimestamp("TimeStamp");
                String description = result.getString("Description");
                
                HighBayRack item = new HighBayRack(pos, pno, Zone, quantity, timeStamp, description);
                racks.add(item);
            }
            return racks.toArray(new HighBayRack[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new HighBayRack[0];
    }
}
