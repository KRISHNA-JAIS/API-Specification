package com.amway.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
	private static String masheryApiKey;
	private static String masheryApiSecret;
	private static String masheryUsername;
	private static String masheryPassword;
	private static String apiHost;
	private static String tokenEndpoint;
	private static String resourceEndpoint;
	private static String siteUUID;
	private static String userAgent;
	private static String v2ApiKey;
	private static String v2Secret;
	private static String siteId;
	private static Integer queryDelay;
	

	public ConfigProperties() {
		if(masheryApiKey==null) {
			init();
		}
    }
	public void init() {
        try (InputStream input = new FileInputStream(System.getProperty("user.home")+"/.amway/mashery/config.properties")) {
            Properties p = new Properties();
            p.load(input);
            masheryApiKey = p.getProperty("mash.apikey");
            masheryApiSecret = p.getProperty("mash.secret");
            masheryUsername = p.getProperty("mash.user");
            masheryPassword = p.getProperty("mash.pw");
            apiHost = p.getProperty("mash.host");
            tokenEndpoint = p.getProperty("mash.tokenendpoint");
            resourceEndpoint = p.getProperty("mash.resourceendpoint");
            siteUUID = p.getProperty("mash.siteuuid");
            userAgent = p.getProperty("mash.useragent");
            v2ApiKey = p.getProperty("mash.v2apikey");
            v2Secret = p.getProperty("mash.v2secret");
            siteId = p.getProperty("mash.siteid");
            queryDelay = Integer.parseInt(p.getProperty("mash.querydelay"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	public String GetMasheryApiKey() {return masheryApiKey;}
	public String GetMasheryApiSecret() {return masheryApiSecret;}
	public String GetMasheryUsername() {return masheryUsername;}
	public String GetMasheryPassword() {return masheryPassword;}
	public String GetApiHost() {return apiHost;}
	public String GetTokenEndpoint() {return tokenEndpoint;}
	public String GetResourceEndpoint() {return resourceEndpoint;}
	public String GetSiteUUID() {return siteUUID;}
	public String GetUserAgent() {return userAgent;}
	public String GetV2ApiKey() {return v2ApiKey;}
	public String GetV2Secret() {return v2Secret;}
	public String GetSiteId() {return siteId;}
	public Integer GetQueryDelay() {return queryDelay;}
	
}
