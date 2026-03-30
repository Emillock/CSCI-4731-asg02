package asg02;

import asg02.builders.RoutineBuilder;
import asg02.products.automations.AutomationRoutine;
import asg02.products.concretes.SmartDevice;
import asg02.products.legacy.GlorbThermostat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderTest {

    @Test
    @DisplayName("Builder constructs a valid routine with all fields set")
    void buildsValidRoutineWithAllFields() {
        AutomationRoutine routine = new RoutineBuilder()
                .withName("Good Morning")
                .addDevice(new SmartDevice.BudgetThermostat())
                .addDevice(new SmartDevice.LuxuryLight())
                .atTime("07:00")
                .toggleNotification(true)
                .build();

        assertNotNull(routine);

        assertEquals("Good Morning", routine.getName());
        assertEquals("07:00", routine.getTriggerTime());
        assertTrue(routine.isSendNotification());
    }

    @Test
    @DisplayName("Vacation Mode routine contains all four devices")
    void vacationModeHasFourDevices() {
        List<Class<?>> expectedClasses = new ArrayList<>();
        expectedClasses.add(SmartDevice.LuxuryLight.class);
        expectedClasses.add(SmartDevice.LuxuryLight.class);
        expectedClasses.add(SmartDevice.BudgetLock.class);
        expectedClasses.add(SmartDevice.GlorbAdapter.class);

        AutomationRoutine routine = new RoutineBuilder()
                .withName("Vacation Mode")
                .addDevice(new SmartDevice.LuxuryLight())
                .addDevice(new SmartDevice.LuxuryLight())
                .addDevice(new SmartDevice.BudgetLock())
                .addDevice(new SmartDevice.GlorbAdapter(new GlorbThermostat()))
                .atTime("11:59")
                .toggleNotification(true)
                .build();

        List<SmartDevice> devices = routine.getDevices();
        assertInstanceOf(expectedClasses.get(0), devices.get(0));
    }

}
