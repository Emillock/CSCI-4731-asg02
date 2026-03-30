package asg02.products.configurations;

public class Configuration implements Cloneable {
    private String ipAddress;
    private String port;
    private String firmwareVersion;

    public Configuration(String ipAddress, String port, String firmwareVersion) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.firmwareVersion = firmwareVersion;
    }

    @Override
    public Configuration clone() throws CloneNotSupportedException {
        return (Configuration) super.clone();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ip) {
        this.ipAddress = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
}
