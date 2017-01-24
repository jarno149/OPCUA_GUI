/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.DatabaseRepository.DBRepo;
import dy.fi.maja.appmodels.ManuFacturingOrder;
import dy.fi.maja.appmodels.WorkOrder;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jarno
 */
@RestController
@RequestMapping("/order")
public class OrderController
{
    @RequestMapping(value = "/product", method = RequestMethod.GET, produces = "application/json")
    public ManuFacturingOrder[] getManuFacturingOrders()
    {
        return DBRepo.getManuFacturingOrders();
    }
    
    @RequestMapping(value = "/work", method = RequestMethod.GET, produces = "application/json")
    public WorkOrder[] getWorkOrders(@RequestParam(value = "resourceid", required = false) Integer resourceId)
    {
        if(resourceId != null)
            return DBRepo.getWorkOrders(resourceId.intValue());
        
        return DBRepo.getWorkOrders();
    }
}
