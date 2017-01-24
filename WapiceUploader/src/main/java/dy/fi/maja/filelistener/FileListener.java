/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.filelistener;

import java.io.IOException;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchService;

/**
 *
 * @author Jarno
 */
public class FileListener
{
    private Path _pathToListen;
    private String _filename;
    private IFileListenerActions _actions;
    private boolean _listenAllFilesInDirectory;
    private int _minDelayBetweenInvokes;
    
    private Thread listenerThread;

    public FileListener(Path _pathToListen, String _filename, IFileListenerActions _actions)
    {
        this._pathToListen = _pathToListen;
        this._filename = _filename;
        this._actions = _actions;
    }
    
    public void startListener()
    {
        try
        {
            WatchService watchService = _pathToListen.getFileSystem().newWatchService();
            FileWatcher watcher = new FileWatcher(watchService, this);
            this.listenerThread = new Thread(watcher);
            this.listenerThread.start();
            this._pathToListen.register(watchService, ENTRY_MODIFY);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void stopListener()
    {
        if(this.listenerThread != null)
        {
            this.listenerThread.interrupt();
            this.listenerThread = null;
        }
    }
    
    public boolean isListening()
    {
        if(this.listenerThread != null)
        {
            return this.listenerThread.isAlive();
        }
        return false;
    }

    public IFileListenerActions getActions()
    {
        return _actions;
    }

    public String getFilename()
    {
        return _filename;
    }

    public int getMinDelayBetweenInvokes()
    {
        return _minDelayBetweenInvokes;
    }

    public boolean isListenAllFilesInDirectory()
    {
        return _listenAllFilesInDirectory;
    }
    
    public void setMinimumDelayBetweenInvokes(int milliseconds)
    {
        this._minDelayBetweenInvokes = milliseconds;
    }
    
    public static interface IFileListenerActions
    {
        void onEvent();
    }
}
