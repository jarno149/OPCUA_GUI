/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dy.fi.maja.appmodels.OPCUAVariable;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 *
 * @author Jarno
 */
public class HttpClient
{
    private String baseUrl;
    private String authString;

    public HttpClient(String baseUrl)
    {
        if(baseUrl.endsWith("/"))
            this.baseUrl = baseUrl;
        else
            this.baseUrl = baseUrl + "/";
        
        if(!baseUrl.startsWith("http://") && !baseUrl.startsWith("https://"))
            this.baseUrl = "http://" + baseUrl;
    }
    
    public void setCredentials(String username, String password)
    {
        this.authString = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
    
    public <T> T get(String pathUrl, Class<T> responseType)
    {
        T t;
        try
        {
            HttpURLConnection connection = null;
            URL url = new URL(this.baseUrl + pathUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            
            // Set authentication
            if(this.authString != null)
                connection.setRequestProperty("Authorization", this.authString);
            
            connection.setRequestProperty("Data-Type", "application/json");
            
            String responseString = readInputStream(connection.getInputStream());
            if(responseType == String.class)
            {
                t = (T)responseString;
                return t;
            }
            try
            {
                Gson g = new Gson();
                t = (T)g.fromJson(responseString, responseType);
                return t;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public void post(String pathUrl, String data)
    {
        try
        {
            System.out.println("Uploading to server: " + data);
            HttpURLConnection connection = null;
            URL url = new URL(this.baseUrl + pathUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            
            // Set authentication
            if(this.authString != null)
                connection.setRequestProperty("Authorization", this.authString);
            
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes("[" + data + "]");
            output.flush();
            output.close();
            
            
            int resultCode = connection.getResponseCode();
            String errorString = readInputStream(connection.getErrorStream());
            if(errorString != null)
                System.out.println(errorString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public <T> T post(String pathUrl, String data, Class<T> responseType)
    {
        T t;
        try
        {
            HttpURLConnection connection = null;
            URL url = new URL(this.baseUrl + pathUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            
            // Set authentication
            if(this.authString != null)
                connection.setRequestProperty("Authorization", this.authString);
            
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.write(data.getBytes());
            output.flush();
            output.close();
            
            if(connection.getResponseCode() != 201 && connection.getResponseCode() != 200)
            {
                String err = readInputStream(connection.getErrorStream());
                System.out.println(err);
                return null;
            }
            
            String responseString = readInputStream(connection.getInputStream());
            if(responseType == String.class)
                return (T)responseString;
            
            if(responseString != null)
            {
                try
                {
                    Gson g = new Gson();
                    t = (T)g.fromJson(responseString, responseType.getClass());
                    return t;
                    
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    private String readInputStream(InputStream stream)
    {
        if(stream == null)
            return null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
