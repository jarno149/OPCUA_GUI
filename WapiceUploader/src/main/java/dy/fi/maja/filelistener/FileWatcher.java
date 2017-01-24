/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.filelistener;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author Jarno
 */
public class FileWatcher implements Runnable
{
    private WatchService _watchService;
    private FileListener _FileListenerInstance;
    private long _lastInvoke;
    private int _delayBetweenInvokes;

    public FileWatcher(WatchService _watchService, FileListener _FileListenerInstance)
    {
        this._watchService = _watchService;
        this._FileListenerInstance = _FileListenerInstance;
        this._delayBetweenInvokes = this._FileListenerInstance.getMinDelayBetweenInvokes();
        this._lastInvoke = System.currentTimeMillis();
    }
    
    
    @Override
    public void run()
    {
        try
        {
            WatchKey key = this._watchService.take();
            while(key != null)
            {
                for(WatchEvent event : key.pollEvents())
                {
                    String fileName = event.context().toString();
                    String compareFilename = this._FileListenerInstance.getFilename();
                    if(compareFilename.equals(fileName) || this._FileListenerInstance.isListenAllFilesInDirectory())
                    {
                        long compareTimeStamp = System.currentTimeMillis();
                        if(_lastInvoke + _delayBetweenInvokes < compareTimeStamp)
                        {
                            this._lastInvoke = compareTimeStamp;
                            this._FileListenerInstance.getActions().onEvent();
                        }
                    }
                }
                key.reset();
                key = this._watchService.take();
            }
            
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("FileListener thread stopped!");
    }
    
}
