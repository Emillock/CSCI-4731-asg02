package asg02.factories;

import asg02.products.abstracts.SmartLight;
import asg02.products.abstracts.SmartLock;
import asg02.products.abstracts.SmartThermostat;
import asg02.products.concretes.SmartDevice;

public class LuxuryFactory implements DeviceFactory {
    @Override
    public SmartLight createLight() {
        return new SmartDevice.LuxuryLight();
    }

    @Override
    public SmartLock createLock() {
        return new SmartDevice.LuxuryLock();
    }

    @Override
    public SmartThermostat createThermostat() {
        return new SmartDevice.LuxuryThermostat();
    }
}
