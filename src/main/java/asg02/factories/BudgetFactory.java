package asg02.factories;

import asg02.products.abstracts.SmartLight;
import asg02.products.abstracts.SmartLock;
import asg02.products.abstracts.SmartThermostat;
import asg02.products.concretes.SmartDevice;

public class BudgetFactory implements DeviceFactory {
    @Override
    public SmartLight createLight() {
        return new SmartDevice.BudgetLight();
    }

    @Override
    public SmartLock createLock() {
        return new SmartDevice.BudgetLock();
    }

    @Override
    public SmartThermostat createThermostat() {
        return new SmartDevice.BudgetThermostat();
    }
}
