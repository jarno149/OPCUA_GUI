/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.DatabaseRepository.DBRepo;
import dy.fi.maja.appmodels.HighBayRack;
import dy.fi.maja.appmodels.PCBBox;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jarno
 */
@RestController
@RequestMapping("/storagestate")
public class StorageStateController
{
    @RequestMapping(value = "/highbay", method = RequestMethod.GET, produces = "application/json")
    public HighBayRack[] getHighBayRacks()
    {
        return DBRepo.getHighBayRacks();
    }
    
    @RequestMapping(value = "/pcbbox", method = RequestMethod.GET, produces = "application/json")
    public PCBBox[] getPCBBoxes()
    {
        return DBRepo.getPCBBoxes();
    }
}
