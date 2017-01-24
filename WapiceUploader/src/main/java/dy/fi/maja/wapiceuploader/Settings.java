/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.wapiceuploader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dy.fi.maja.appmodels.WapiceDevice;
import java.io.FileReader;
import java.io.FileWriter;

public class Settings
{
    public static Settings applicationSettings;
    
    private ServerSettings serverSettings;
    private IotSettings iotSettings;
    private DatabaseSettings databaseSettings;
    
    public static void readSettings()
    {
        try
        {
            applicationSettings = new Gson().fromJson(new FileReader("./settings.json"), Settings.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Cannot read settings file");
            System.exit(0);
        }
    }

    public DatabaseSettings getDatabaseSettings()
    {
        return databaseSettings;
    }

    public void setDatabaseSettings(DatabaseSettings databaseSettings)
    {
        this.databaseSettings = databaseSettings;
    }

    public ServerSettings getServerSettings()
    {
        return serverSettings;
    }

    public void setServerSettings(ServerSettings serverSettings)
    {
        this.serverSettings = serverSettings;
    }

    public IotSettings getIotSettings()
    {
        return iotSettings;
    }

    public void setIotSettings(IotSettings iotSettings)
    {
        this.iotSettings = iotSettings;
    }
    
    public static class DatabaseSettings
    {
        private String filePath;

        public String getFilePath()
        {
            return filePath;
        }

        public void setFilePath(String filePath)
        {
            this.filePath = filePath;
        }
        
        public String getFolderpath()
        {
            int li = filePath.lastIndexOf("/");
            return filePath.substring(0, li+1);
        }
        
        public String getFilename()
        {
            int li = filePath.lastIndexOf("/");
            return filePath.substring(li+1);
        }
    }
    
    public static class ServerSettings
    {
        private int port;

        public int getPort()
        {
            return port;
        }

        public void setPort(int port)
        {
            this.port = port;
        }
    }
    
    public static class IotSettings
    {
        private boolean useWapice;
        private String url;
        private String username;
        private String password;
        private WapiceDevice configuredDevice;

        public boolean isUseWapice()
        {
            return useWapice;
        }

        public void setUseWapice(boolean useWapice)
        {
            this.useWapice = useWapice;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public WapiceDevice getDevice()
        {
            return configuredDevice;
        }

        public void setDevice(WapiceDevice device)
        {
            this.configuredDevice = device;
            
            // Write settings file
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            try
            {
                String json = g.toJson(Settings.applicationSettings);
                FileWriter writer = new FileWriter("./settings.json");
                writer.write(json);
                writer.flush();
                writer.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
