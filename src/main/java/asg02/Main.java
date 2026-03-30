package asg02;

import asg02.builders.RoutineBuilder;
import asg02.connections.CloudConnection;
import asg02.factories.BudgetFactory;
import asg02.factories.DeviceFactory;
import asg02.factories.LuxuryFactory;
import asg02.products.abstracts.SmartThermostat;
import asg02.products.automations.AutomationRoutine;
import asg02.products.concretes.SmartDevice;
import asg02.products.configurations.Configuration;
import asg02.products.legacy.GlorbThermostat;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        System.out.println("CloudConnection using Singleton pattern:\n");

        CloudConnection conn1 = CloudConnection.getInstance("KEY", "some.server.com");
        CloudConnection conn2 = CloudConnection.getInstance("KEY", "some.server.com");

        System.out.println("Instance 1 address: " + System.identityHashCode(conn1));
        System.out.println("Instance 2 address: " + System.identityHashCode(conn2));
        System.out.println("Check for being the same object: " + (conn1 == conn2));

        System.out.println("\nDevice Ecosystem using Abstract Factory pattern:");

        System.out.println("\nBudget Line created using BudgetFactory");
        DeviceFactory budgetFactory = new BudgetFactory();
        budgetFactory.createLight().turnOn();
        budgetFactory.createLock().lock();
        budgetFactory.createThermostat().setTemperature(24.0);

        System.out.println("\nLuxury Line created using LuxuryFactory");
        DeviceFactory luxuryFactory = new LuxuryFactory();
        luxuryFactory.createLight().turnOn();
        luxuryFactory.createLock().lock();
        luxuryFactory.createThermostat().setTemperature(24.0);

        System.out.println("\nLegacy Glorb Integration using Adapter pattern:\n");

        GlorbThermostat legacyDevice = new GlorbThermostat();
        SmartThermostat adapted = new SmartDevice.GlorbAdapter(legacyDevice);

        adapted.setTemperature(25.0); // expected 25.0C -> 77F
        adapted.setTemperature(0.0); // expected 0.0C -> 32F
        adapted.setTemperature(100.0); // expected 100.0C -> 212F

        System.out.println("\nAutomation Routine using Builder pattern:\n");

        AutomationRoutine vacationMode = new RoutineBuilder()
                .withName("Vacation Mode")
                .addDevice(new SmartDevice.LuxuryLight())
                .addDevice(new SmartDevice.LuxuryLight())
                .addDevice(new SmartDevice.BudgetLock())
                .addDevice(new SmartDevice.GlorbAdapter(new GlorbThermostat()))
                .atTime("11:59")
                .toggleNotification(true)
                .build();

        System.out.println("Routine built: " + vacationMode);

        System.out.println("\nConfiguration using Prototype pattern:\n");

        Configuration livingRoom = new Configuration("192.168.1.10", "8080", "v0.0.1");

        Configuration guestRoom = livingRoom.clone();
        guestRoom.setIpAddress("192.168.1.20");

        System.out.println("Clone     : " + guestRoom.getIpAddress());
        System.out.println("Original  : " + livingRoom.getIpAddress());

        System.out.println("Check for IPs difference: "
                + !livingRoom.getIpAddress().equals(guestRoom.getIpAddress()));
    }
}
