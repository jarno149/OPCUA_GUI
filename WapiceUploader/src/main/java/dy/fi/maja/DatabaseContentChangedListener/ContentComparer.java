/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.DatabaseContentChangedListener;

import dy.fi.maja.appmodels.HighBayRack;
import dy.fi.maja.appmodels.ManuFacturingOrder;
import dy.fi.maja.appmodels.PCBBox;
import dy.fi.maja.appmodels.WorkOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.javers.core.*;
import org.javers.core.diff.*;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.ObjectRemoved;

/**
 *
 * @author Jarno
 */
public class ContentComparer
{
    public static PCBBox[] comparePcbBoxesForChanges(PCBBox[] newResult)
    {
        ArrayList<PCBBox> changedObjects = new ArrayList<>();
        
        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
        List<PCBBox> list1 = Arrays.asList(newResult);
        List<PCBBox> list2 = Arrays.asList(DatabaseContent.pCBBoxes);
        Diff diff = javers.compareCollections(list2, list1, PCBBox.class);
        
        List<PCBBox> modifiedObjects = diff.getObjectsByChangeType(ValueChange.class);
        List<PCBBox> createdObjects = diff.getObjectsByChangeType(NewObject.class);
        
        for(PCBBox o : modifiedObjects)
        {
            changedObjects.add(o);
        }
        for(PCBBox o : createdObjects)
        {
            changedObjects.add(o);
        }
        
        DatabaseContent.pCBBoxes = newResult;
        return changedObjects.toArray(new PCBBox[0]);
    }
    
    public static WorkOrder[] compareWorkOrdersForChanges(WorkOrder[] newResult)
    {
        ArrayList<WorkOrder> changedObjects = new ArrayList<>();
        
        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
        List<WorkOrder> list1 = Arrays.asList(newResult);
        List<WorkOrder> list2 = Arrays.asList(DatabaseContent.workOrders);
        Diff diff = javers.compareCollections(list2, list1, WorkOrder.class);
        
        List<WorkOrder> modifiedObjects = diff.getObjectsByChangeType(ValueChange.class);
        List<WorkOrder> createdObjects = diff.getObjectsByChangeType(NewObject.class);
        List<WorkOrder> removedObjects = diff.getObjectsByChangeType(ObjectRemoved.class);
        
        for(WorkOrder o : modifiedObjects)
        {
            changedObjects.add(o);
        }
        for(WorkOrder o : createdObjects)
        {
            changedObjects.add(o);
        }
        
        DatabaseContent.workOrders = newResult;
        if(removedObjects.size() > 0)
            return newResult;
        return changedObjects.toArray(new WorkOrder[0]);
    }
    
    public static ManuFacturingOrder[] compareManuFacturingOrdersForChanges(ManuFacturingOrder[] newResult)
    {
        ArrayList<ManuFacturingOrder> changedObjects = new ArrayList<>();
        
        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
        List<ManuFacturingOrder> list1 = Arrays.asList(newResult);
        List<ManuFacturingOrder> list2 = Arrays.asList(DatabaseContent.manuFacturingOrders);
        Diff diff = javers.compareCollections(list2, list1, ManuFacturingOrder.class);
        
        List<ManuFacturingOrder> modifiedObjects = diff.getObjectsByChangeType(ValueChange.class);
        List<ManuFacturingOrder> createdObjects = diff.getObjectsByChangeType(NewObject.class);
        List<ManuFacturingOrder> removedObjects = diff.getObjectsByChangeType(ObjectRemoved.class);
        
        for(ManuFacturingOrder o : modifiedObjects)
        {
            changedObjects.add(o);
        }
        for(ManuFacturingOrder o : createdObjects)
        {
            changedObjects.add(o);
        }
        
        DatabaseContent.manuFacturingOrders = newResult;
        if(removedObjects.size() > 0)
            return newResult;
        return changedObjects.toArray(new ManuFacturingOrder[0]);
    }
    
    public static HighBayRack[] compareHighBayRacksForChanges(HighBayRack[] newResult)
    {
        ArrayList<HighBayRack> changedObjects = new ArrayList<>();
        
        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
        List<HighBayRack> list1 = Arrays.asList(newResult);
        List<HighBayRack> list2 = Arrays.asList(DatabaseContent.highBayRacks);
        Diff diff = javers.compareCollections(list2, list1, HighBayRack.class);
        
        List<HighBayRack> modifiedObjects = diff.getObjectsByChangeType(ValueChange.class);
        List<HighBayRack> createdObjects = diff.getObjectsByChangeType(NewObject.class);
        
        for(HighBayRack o : modifiedObjects)
        {
            changedObjects.add(o);
        }
        for(HighBayRack o : createdObjects)
        {
            changedObjects.add(o);
        }
        
        DatabaseContent.highBayRacks = newResult;
        return changedObjects.toArray(new HighBayRack[0]);
    }
}
