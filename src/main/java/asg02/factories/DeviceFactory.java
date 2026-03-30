package asg02.factories;

import asg02.products.abstracts.SmartLight;
import asg02.products.abstracts.SmartLock;
import asg02.products.abstracts.SmartThermostat;

public interface DeviceFactory {
    SmartLight createLight();

    SmartLock createLock();

    SmartThermostat createThermostat();
}
