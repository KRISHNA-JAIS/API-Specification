package com.amway.service;

import java.io.*;

public class GetSpecification {
    /**
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {
        String method = null;
        String resource = null;
        String parameters = null;
        String body = null;
        String line = "";
        String serviceId = "";
        try {
            System.out.println("Started...");
            int outputFileCount = 1;
            String accessToken = APIServices.GetAccessToken();
            System.out.println("accessToken: " + accessToken);
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/serviceId.csv"));
            while ((serviceId = br.readLine()) != null) {
                File output = new File("src/main/resources/output"+ outputFileCount +".csv");
                Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"));
                System.out.println(serviceId);
                String response = null;
                method = "GET";
                resource = "/services/" + serviceId + "/endpoints";
                parameters = "fields=allowMissingApiKey," +
                        "apiKeyValueLocationKey," +
                        "apiKeyValueLocations," +
                        "apiMethodDetectionKey," +
                        "apiMethodDetectionLocations," +
                        "cache," +
                        "connectionTimeoutForSystemDomainRequest," +
                        "connectionTimeoutForSystemDomainResponse," +
                        "cookiesDuringHttpRedirectsEnabled," +
                        "cors," +
                        "created," +
                        "customRequestAuthenticationAdapter," +
                        "dropApiKeyFromIncomingCall," +
                        "forceGzipOfBackendCall,forwardedHeaders," +
                        "gzipPassthroughSupportEnabled," +
                        "headersToExcludeFromIncomingCall," +
                        "highSecurity," +
                        "hostPassthroughIncludedInBackendCallHeader," +
                        "id," +
                        "inboundSslRequired," +
                        "jsonpCallbackParameter," +
                        "jsonpCallbackParameterValue," +
                        "methods,methods.responseFilters," +
                        "name," +
                        "numberOfHttpRedirectsToFollow," +
                        "oauthGrantTypes," +
                        "outboundRequestTargetPath," +
                        "outboundRequestTargetQueryParameters," +
                        "outboundTransportProtocol," +
                        "processor," +
                        "publicDomains," +
                        "requestAuthenticationType," +
                        "requestPathAlias," +
                        "requestProtocol," +
                        "returnedHeaders," +
                        "scheduledMaintenanceEvent," +
                        "stringsToTrimFromApiKey," +
                        "supportedHttpMethods," +
                        "systemDomainAuthentication," +
                        "systemDomains," +
                        "trafficManagerDomain," +
                        "updated," +
                        "useSystemDomainCredential&limit=100&offset=0";
                body = null;
                response = APIServices.CallAPI(accessToken,
                        method,
                        resource,
                        parameters,
                        body);
                System.out.println(response);
                w.append(response).append("\"");
                w.append("\n");
                outputFileCount++;
                w.flush();
                w.close();
                System.out.println("Completed...");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}