package asg02.connections;

public class CloudConnection {
    private static volatile CloudConnection instance;
    private final String apiKey;
    private final String serverUrl;

    private CloudConnection(String apiKey, String serverUrl) {
        this.apiKey = apiKey;
        this.serverUrl = serverUrl;
        System.out.println("CloudConnection Connected to " + serverUrl);
    }

    public static CloudConnection getInstance(String apiKey, String serverUrl) {
        if (instance == null) {
            synchronized (CloudConnection.class) {
                if (instance == null) {
                    instance = new CloudConnection(apiKey, serverUrl);
                }
            }
        }
        return instance;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getServerUrl() {
        return serverUrl;
    }
}