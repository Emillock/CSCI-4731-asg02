package asg02.products.automations;

import asg02.products.concretes.SmartDevice;

import java.util.ArrayList;
import java.util.List;

public class AutomationRoutine {
    private final String name;
    private final List<SmartDevice> devices;
    private final String triggerTime;
    private final boolean sendNotification;

    public AutomationRoutine(String name, List<SmartDevice> devices, String triggerTime, boolean sendNotification) {
        this.name = name;
        this.devices = List.copyOf(devices);
        this.triggerTime = triggerTime;
        this.sendNotification = sendNotification;
    }

    @Override
    public String toString() {
        List<String> names = new ArrayList<>();
        for (SmartDevice d : devices) names.add(d.getClass().getSimpleName());

        return "AutomationRoutine{\n"
                + "\tname='" + name + "',\n"
                + "\ttrigger='" + triggerTime + "',\n"
                + "\tnotify=" + sendNotification + ",\n"
                + "\tdevices=" + names + "\n"
                + '}';
    }

    public String getName() {
        return name;
    }

    public List<SmartDevice> getDevices() {
        return devices;
    }

    public String getTriggerTime() {
        return triggerTime;
    }

    public boolean isSendNotification() {
        return sendNotification;
    }
}