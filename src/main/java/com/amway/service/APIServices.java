package com.amway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIServices {
    static ConfigProperties c = new ConfigProperties();
    public static String CallAPI(String token, String method, String resource, String parameters, String body) throws IOException, Exception {
    	System.setProperty("https.protocols", "TLSv1.2");
        URL url = new URL(c.GetApiHost() + c.GetResourceEndpoint() + resource + ( parameters != null&&parameters.length() >0 ? "?" + parameters : ""));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("User-Agent", c.GetUserAgent());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        if (method.equals("POST")|| method.equals("PUT"))
        {
        	connection.setDoOutput(true);
        	DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        	wr.writeBytes(body);
        	wr.flush();
        	wr.close();
        }
        int responseCode = connection.getResponseCode();
        Thread.sleep(c.GetQueryDelay());
        InputStream is;
        Integer i=0;
        Integer retryLoops=10;
        while (responseCode >= 400) {
        	i++;
        	is = connection.getErrorStream();
        	System.out.println("ResponseCode: " + responseCode + " Message: " + connection.getResponseMessage() + " Path: " + url.toString());
        	System.out.println("Retrying...");
        	Thread.sleep(10000);
            if (method.equals("POST")|| method.equals("PUT"))
            {
            	connection.setDoOutput(true);
            	DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            	wr.writeBytes(body);
            	wr.flush();
            	wr.close();
            }
            if (i>=retryLoops) {
            	System.out.println("Too many response errors from API call. Exiting...");
            	System.exit(1);
            }
        } 
        is = connection.getInputStream();
        
        if (is != null)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        }

        return null;
    }
    public static String GetAccessToken() throws Exception
    {
    	System.setProperty("https.protocols", "TLSv1.2");
    	String htmlBody = "grant_type=password&username="+c.GetMasheryUsername()+"&password="+c.GetMasheryPassword()+"&scope="+c.GetSiteUUID();
        String urlStr = "https://api.mashery.com" + c.GetTokenEndpoint();
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String userpass = c.GetMasheryApiKey() + ":" + c.GetMasheryApiSecret();
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", basicAuth);
        connection.setRequestProperty("User-Agent", c.GetUserAgent());
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        connection.setDoOutput(true);
        connection.setReadTimeout(0);


        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

        wr.write(htmlBody);
        wr.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer buf = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            buf.append(inputLine);
        }

        wr.close();
        in.close();
        String jsonResponse = buf.toString();
        System.out.println(jsonResponse);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        String accessToken = rootNode.path("access_token").textValue();
        System.out.println("accessToken="+accessToken);
        Thread.sleep(1000);
        return accessToken;
    }

}
