/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.appmodels.OPCUAVariable;
import com.google.gson.Gson;
import dy.fi.maja.httpclient.Wapice;
import dy.fi.maja.wapiceuploader.Settings;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author k1400284
 */
@RestController
public class VariableController
{
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testFunction()
    {
        return "OK!";
    }
    
    @RequestMapping(value = "/opcuavariables", method = RequestMethod.POST, consumes = "application/json")
    public void incomingVariables(@RequestBody OPCUAVariable variable)
    {
        Gson g = new Gson();
        System.out.println(g.toJson(variable));
        
        // Send data to webpages
        sendLiveDataToWebpage(variable);
        
        if(Settings.applicationSettings.getIotSettings().getDevice().getDeviceId() == null
                || !Settings.applicationSettings.getIotSettings().isUseWapice())
            return;
        
        Wapice.wapiceClient.writeDatanode(Settings.applicationSettings.getIotSettings().getDevice().getDeviceId(), variable.ToWapiceDatanode());
    }
    
    private static void sendLiveDataToWebpage(OPCUAVariable var)
    {
        Gson g = new Gson();
        if(var.getIdentifier().equals("xRobIsHome_36"))
        {
            // ROBOT IS HOME
            WebSocketController.SendMessageToAll(g.toJson(var));
        }
    }
}
