/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.DatabaseContentChangedListener;

import dy.fi.maja.DatabaseRepository.DBRepo;
import dy.fi.maja.appmodels.HighBayRack;
import dy.fi.maja.appmodels.ManuFacturingOrder;
import dy.fi.maja.appmodels.PCBBox;
import dy.fi.maja.appmodels.WorkOrder;

/**
 *
 * @author Jarno
 */
public class DatabaseContent
{
    public static PCBBox[] pCBBoxes;
    public static WorkOrder[] workOrders;
    public static ManuFacturingOrder[] manuFacturingOrders;
    public static HighBayRack[] highBayRacks;
    
    public static void initialize()
    {
        pCBBoxes = DBRepo.getPCBBoxes();
        workOrders = DBRepo.getWorkOrders();
        manuFacturingOrders = DBRepo.getManuFacturingOrders();
        highBayRacks = DBRepo.getHighBayRacks();
    }
}
