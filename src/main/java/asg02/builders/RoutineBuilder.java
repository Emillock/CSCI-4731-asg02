package asg02.builders;

import asg02.products.automations.AutomationRoutine;
import asg02.products.concretes.SmartDevice;

import java.util.ArrayList;
import java.util.List;

public class RoutineBuilder {
    private String name;
    private final List<SmartDevice> devices = new ArrayList<>();
    private String triggerTime;
    private boolean sendNotification = false;

    public RoutineBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RoutineBuilder addDevice(SmartDevice device) {
        this.devices.add(device);
        return this;
    }

    public RoutineBuilder atTime(String triggerTime) {
        this.triggerTime = triggerTime;
        return this;
    }

    public RoutineBuilder toggleNotification(boolean enabled) {
        this.sendNotification = enabled;
        return this;
    }

    public AutomationRoutine build() {
        if (name == null || name.isEmpty())
            throw new IllegalStateException("Routine must have a name.");
        if (devices.isEmpty())
            throw new IllegalStateException("Routine must include at least one device.");
        if (triggerTime == null || triggerTime.isEmpty())
            throw new IllegalStateException("Routine must have a trigger time.");

        return new AutomationRoutine(name, devices, triggerTime, sendNotification);
    }
}
