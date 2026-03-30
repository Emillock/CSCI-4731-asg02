package asg02.products.legacy;

public class GlorbThermostat {
    int heatIndex;

    public void setHeatIndex(int fahrenheit) {
        heatIndex=fahrenheit;
        System.out.println("GlorbThermostat heat index set to " + fahrenheit + "F");
    }

    public int getHeatIndex() {
        return heatIndex;
    }
}