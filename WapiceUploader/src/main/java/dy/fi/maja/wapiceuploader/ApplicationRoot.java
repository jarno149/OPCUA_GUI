/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.wapiceuploader;

import com.google.gson.Gson;
import dy.fi.maja.DatabaseContentChangedListener.ContentComparer;
import dy.fi.maja.DatabaseContentChangedListener.DatabaseContent;
import dy.fi.maja.DatabaseRepository.DBRepo;
import dy.fi.maja.appmodels.HighBayRack;
import dy.fi.maja.appmodels.ManuFacturingOrder;
import dy.fi.maja.appmodels.PCBBox;
import dy.fi.maja.appmodels.WapiceDevice;
import dy.fi.maja.appmodels.WorkOrder;
import dy.fi.maja.filelistener.FileListener;
import dy.fi.maja.httpclient.Wapice;
import dy.fi.maja.appmodels.WapiceDevice.DataNode;
import dy.fi.maja.controllers.WebSocketController;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"dy.fi.maja.controllers"})
public class ApplicationRoot
{
    public static void main(String[] args)
    {
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println("//                                                                       //");
        System.out.println("//                     STARTING APPLICATION...                           //");
        System.out.println("//                                                                       //");
        System.out.println("//            Jarno Rajala 23.01.2017                                    //");
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        
        Settings.readSettings();
        
        // Initialize AccessDbRepository
        DBRepo.initialize(Settings.applicationSettings.getDatabaseSettings().getFilePath());
        
        // Initialize static database memory
        DatabaseContent.initialize();
        
        
        // Check if wapiceOutput is enabled in settings
        if(Settings.applicationSettings.getIotSettings().isUseWapice())
        {
            // Initialize wapiceClient
            String baseurl = Settings.applicationSettings.getIotSettings().getUrl();
            String username = Settings.applicationSettings.getIotSettings().getUsername();
            String password = Settings.applicationSettings.getIotSettings().getPassword();
            Wapice.initialize(baseurl, username, password); 
            
            System.out.println("\nWapiceClient is enabled, sending databasecontent to iot-ticket...\n");
            
            // Try 20 times register new device
            checkIfDeviceExists(20);
            
            // When database is initialized, send all data to wapice
            writeExistingDataToWapice();
        }
        else
        {
            System.out.println("\nWapiceClient is disabled...\n");
        }
        
     
        // START REST SERVER
        int serverPort = Settings.applicationSettings.getServerSettings().getPort();
        System.getProperties().put("server.port", serverPort);
        SpringApplication app = new SpringApplication(ApplicationRoot.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run();
        
        // Initialize filelistener
        initializeFilelistener();
        
        while(true){}
    }
    
    private static void writeExistingDataToWapice()
    {
        String deviceId = Settings.applicationSettings.getIotSettings().getDevice().getDeviceId();
        if(deviceId == null)
            return;
        Wapice.writeHighBayRack(deviceId, DatabaseContent.highBayRacks);
        Wapice.writeManufacturingOrders(deviceId, DatabaseContent.manuFacturingOrders);
        Wapice.writePCBBoxes(deviceId, DatabaseContent.pCBBoxes);
        Wapice.writeWorkOrders(deviceId, DatabaseContent.workOrders);
    }
    
    private static void initializeFilelistener()
    {
        String fileName = Settings.applicationSettings.getDatabaseSettings().getFilename();
        Path folderPath = Paths.get(Settings.applicationSettings.getDatabaseSettings().getFolderpath());
        FileListener listener = new FileListener(folderPath, fileName, new FileListener.IFileListenerActions()
        {
            @Override
            public void onEvent()
            {
                boolean wapiceEnabled = Settings.applicationSettings.getIotSettings().isUseWapice();
                Gson g = new Gson();
                
                    System.out.println("Database changed, uploading changed values to: " + Settings.applicationSettings.getIotSettings()
                            .getUrl());

                    String deviceId = Settings.applicationSettings.getIotSettings().getDevice().getDeviceId();

                    if (deviceId == null && wapiceEnabled)
                    {
                        return;
                    }

                    PCBBox[] pCBBoxNewResult = DBRepo.getPCBBoxes();
                    PCBBox[] pCBBoxChanges = ContentComparer.comparePcbBoxesForChanges(pCBBoxNewResult);
                    if (pCBBoxChanges.length > 0 && wapiceEnabled)
                    {
                        Wapice.writePCBBoxes(deviceId, pCBBoxChanges);
                    }
                    if(pCBBoxChanges.length > 0)
                        WebSocketController.SendMessageToAll(g.toJson(pCBBoxChanges));

                    ManuFacturingOrder[] manuFacturingOrdersNewResult = DBRepo.getManuFacturingOrders();
                    ManuFacturingOrder[] manuFacturingOrdersChanges = ContentComparer.compareManuFacturingOrdersForChanges(manuFacturingOrdersNewResult);
                    if (manuFacturingOrdersChanges.length > 0 && wapiceEnabled)
                    {
                        Wapice.writeManufacturingOrders(deviceId, manuFacturingOrdersChanges);
                    }
                    if(manuFacturingOrdersChanges.length > 0)
                       WebSocketController.SendMessageToAll(g.toJson(manuFacturingOrdersChanges));   

                    WorkOrder[] workOrdersNewResult = DBRepo.getWorkOrders();
                    WorkOrder[] workOrdersChanges = ContentComparer.compareWorkOrdersForChanges(workOrdersNewResult);
                    if (workOrdersChanges.length > 0 && wapiceEnabled)
                    {
                        Wapice.writeWorkOrders(deviceId, workOrdersChanges);
                    }
                    if(workOrdersChanges.length > 0)
                        WebSocketController.SendMessageToAll(g.toJson(workOrdersChanges));

                    HighBayRack[] highBayRacksNewResult = DBRepo.getHighBayRacks();
                    HighBayRack[] highBayRacksChanges = ContentComparer.compareHighBayRacksForChanges(highBayRacksNewResult);
                    if (highBayRacksChanges.length > 0 && wapiceEnabled)
                    {
                        Wapice.writeHighBayRack(deviceId, highBayRacksChanges);
                    }
                    if(highBayRacksChanges.length > 0)
                        WebSocketController.SendMessageToAll(g.toJson(highBayRacksChanges));
                }
        });
        listener.setMinimumDelayBetweenInvokes(50);
        listener.startListener();
    }
    
    private static void checkIfDeviceExists(int retries)
    {
        int retrycounter = 0;
        
        String deviceId = Settings.applicationSettings.getIotSettings().getDevice().getDeviceId();
        if(deviceId == null)
        {
            // Device dont exist, create it
            WapiceDevice device = Settings.applicationSettings.getIotSettings().getDevice();
            if(device.getName() == null)
            {
                System.out.println("Device's name not found");
                System.exit(0);
            }
            else if(device.getManufacturer() == null)
            {
                System.out.println("Device's manufacturer not found");
                System.exit(0);
            }
            while (true)
            {                
                WapiceDevice registeredDevice = Wapice.wapiceClient.registerNew(device);
                if(registeredDevice != null)
                {
                    Settings.applicationSettings.getIotSettings().setDevice(registeredDevice);
                    System.out.println("New device registered with name: " + registeredDevice.getName());
                    return;
                }
                if(retrycounter == retries)
                {
                    System.out.println("Cannot create device. Is computer connected to network?");
                    System.exit(0);
                }
                retrycounter++;
            }
        }
        else
        {
            WapiceDevice device = Wapice.wapiceClient.getSingleDevice(deviceId);
            if(device == null)
            {
                // Register new device
                WapiceDevice dev = Settings.applicationSettings.getIotSettings().getDevice();
                while(true)
                {
                    WapiceDevice registeredDevice = Wapice.wapiceClient.registerNew(dev);
                    if(registeredDevice != null)
                    {
                        Settings.applicationSettings.getIotSettings().setDevice(registeredDevice);
                        System.out.println("New device registered with name: " + registeredDevice.getName());
                        return;
                    }
                    if(retrycounter == retries)
                    {
                        System.out.println("Cannot create device. Is computer connected to network?");
                        System.exit(0);
                    }
                    retrycounter++;
                }
            }
            else
            {
                System.out.println("Found existing device with id: " + device.getDeviceId());
                return;
            }
        }
    }
}
