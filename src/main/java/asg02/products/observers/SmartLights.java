package asg02.products.observers;

public class SmartLights implements Observer {
    private final String location;
    private boolean isOn;

    public SmartLights(String location) {
        this.location = location;
        this.isOn = false;
    }

    @Override
    public void update(String event) {
        if (event.contains("motion")) {
            turnOn();
        }
    }

    public void turnOn() {
        isOn = true;
        System.out.println(location + " SmartLights turned ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(location + " SmartLights turned OFF");
    }

    public boolean isOn() {
        return isOn;
    }

    public String getLocation() {
        return location;
    }
}
