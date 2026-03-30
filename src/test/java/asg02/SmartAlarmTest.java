package asg02;

import asg02.products.observers.MotionSensor;
import asg02.products.observers.Observer;
import asg02.products.observers.SmartAlarm;
import asg02.products.strategies.AlertStrategy;
import asg02.products.strategies.SilentPushStrategy;
import asg02.products.strategies.LoudSirenStrategy;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SmartAlarmTest {
    private final ByteArrayOutputStream console = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void captureConsole() {
        originalOut = System.out;
        System.setOut(new PrintStream(console));
    }

    @AfterEach
    void restoreConsole() {
        System.setOut(originalOut);
    }

    private String output() {
        return console.toString().trim();
    }

    @Test
    @DisplayName("SmartAlarm implements Observer interface")
    void implementsObserver() {
        SmartAlarm alarm = new SmartAlarm("Test Alarm");
        assertInstanceOf(Observer.class, alarm);
    }

    @Test
    @DisplayName("SmartAlarm stores name correctly")
    void storesName() {
        SmartAlarm alarm = new SmartAlarm("Front Door Alarm");
        assertEquals("Front Door Alarm", alarm.getName());
    }

    @Test
    @DisplayName("SmartAlarm starts in DISARMED state")
    void startsDisarmed() {
        SmartAlarm alarm = new SmartAlarm("Test");
        assertFalse(alarm.isArmed());
    }

    @Test
    @DisplayName("SmartAlarm starts with SILENT strategy by default")
    void startsWithSilentStrategy() {
        SmartAlarm alarm = new SmartAlarm("Test");
        assertInstanceOf(SilentPushStrategy.class, alarm.getCurrentStrategy());
    }

    @Test
    @DisplayName("arm() changes state to armed and prints message")
    void armChangesState() {
        SmartAlarm alarm = new SmartAlarm("Home Security");
        alarm.arm();

        assertTrue(alarm.isArmed());
        assertEquals("Home Security is now ARMED", output());
    }

    @Test
    @DisplayName("disarm() changes state to disarmed and prints message")
    void disarmChangesState() {
        SmartAlarm alarm = new SmartAlarm("Garage Alarm");
        alarm.arm();
        console.reset();

        alarm.disarm();

        assertFalse(alarm.isArmed());
        assertEquals("Garage Alarm is now DISARMED", output());
    }

    @Test
    @DisplayName("setAlertStrategy() changes strategy to SIREN")
    void setStrategyToSiren() {
        SmartAlarm alarm = new SmartAlarm("Test");
        alarm.setAlertStrategy("SIREN");

        assertInstanceOf(LoudSirenStrategy.class, alarm.getCurrentStrategy());
        assertTrue(output().contains("alert strategy changed to SIREN"));
    }

    @Test
    @DisplayName("setAlertStrategy() changes strategy to SILENT")
    void setStrategyToSilent() {
        SmartAlarm alarm = new SmartAlarm("Test");
        alarm.setAlertStrategy("SIREN");
        console.reset();

        alarm.setAlertStrategy("SILENT");

        assertInstanceOf(SilentPushStrategy.class, alarm.getCurrentStrategy());
        assertTrue(output().contains("alert strategy changed to SILENT"));
    }

    @Test
    @DisplayName("setAlertStrategy() with unknown strategy keeps current strategy")
    void setStrategyUnknownKeepsCurrent() {
        SmartAlarm alarm = new SmartAlarm("Test");
        AlertStrategy originalStrategy = alarm.getCurrentStrategy();

        alarm.setAlertStrategy("UNKNOWN");

        assertEquals(originalStrategy, alarm.getCurrentStrategy());
        assertTrue(output().contains("Unknown strategy"));
    }

    @Test
    @DisplayName("update() with motion when ARMED triggers current strategy")
    void updateWhenArmedTriggersStrategy() {
        SmartAlarm alarm = new SmartAlarm("Security");
        alarm.arm();
        console.reset();

        alarm.update("Hallway motion detected");

        assertTrue(output().contains("silent push notification"));
    }

    @Test
    @DisplayName("update() with motion when DISARMED does not trigger strategy")
    void updateWhenDisarmedDoesNotTrigger() {
        SmartAlarm alarm = new SmartAlarm("Security");
        // alarm is disarmed by default

        alarm.update("Hallway motion detected");

        assertFalse(output().contains("silent push notification"));
        assertFalse(output().contains("SIREN"));
    }

    @Test
    @DisplayName("update() without motion keyword does not trigger when armed")
    void updateWithoutMotionDoesNotTrigger() {
        SmartAlarm alarm = new SmartAlarm("Security");
        alarm.arm();
        console.reset();

        alarm.update("some other event");

        assertFalse(output().contains("silent push notification"));
        assertFalse(output().contains("SIREN"));
    }

    @Test
    @DisplayName("Strategy switching at runtime works correctly (SILENT to SIREN)")
    void strategySwitchingAtRuntime() {
        SmartAlarm alarm = new SmartAlarm("Security");
        alarm.arm();
        console.reset();

        // Test with SILENT strategy
        alarm.setAlertStrategy("SILENT");
        console.reset();
        alarm.update("motion detected");
        assertTrue(output().contains("silent push notification"));

        console.reset();

        // Switch to SIREN strategy
        alarm.setAlertStrategy("SIREN");
        console.reset();
        alarm.update("motion detected");
        assertTrue(output().contains("120dB SIREN"));
    }

    @Test
    @DisplayName("Integration with MotionSensor works correctly")
    void integrationWithMotionSensor() {
        MotionSensor sensor = new MotionSensor("Living Room");
        SmartAlarm alarm = new SmartAlarm("Home Alarm");

        sensor.addObserver(alarm);
        alarm.arm();
        console.reset();

        sensor.detectMotion();

        assertTrue(output().contains("silent push notification"));
    }

    @Test
    @DisplayName("Multiple arm() calls work correctly")
    void multipleArmCalls() {
        SmartAlarm alarm = new SmartAlarm("Test");
        alarm.arm();
        alarm.arm();

        assertTrue(alarm.isArmed());
    }

    @Test
    @DisplayName("Multiple disarm() calls work correctly")
    void multipleDisarmCalls() {
        SmartAlarm alarm = new SmartAlarm("Test");
        alarm.disarm();
        alarm.disarm();

        assertFalse(alarm.isArmed());
    }

    @Test
    @DisplayName("Arm/disarm toggle works correctly")
    void armDisarmToggle() {
        SmartAlarm alarm = new SmartAlarm("Test");

        assertFalse(alarm.isArmed());

        alarm.arm();
        assertTrue(alarm.isArmed());

        alarm.disarm();
        assertFalse(alarm.isArmed());

        alarm.arm();
        assertTrue(alarm.isArmed());
    }

    @Test
    @DisplayName("Strategy registry uses O(1) lookup (no conditionals)")
    void strategyRegistryUsesMapLookup() {
        SmartAlarm alarm = new SmartAlarm("Test");

        // Switch strategies multiple times to verify O(1) lookup
        alarm.setAlertStrategy("SILENT");
        assertInstanceOf(SilentPushStrategy.class, alarm.getCurrentStrategy());

        alarm.setAlertStrategy("SIREN");
        assertInstanceOf(LoudSirenStrategy.class, alarm.getCurrentStrategy());

        alarm.setAlertStrategy("SILENT");
        assertInstanceOf(SilentPushStrategy.class, alarm.getCurrentStrategy());
    }

    @Test
    @DisplayName("SmartAlarm with SIREN strategy responds to motion correctly")
    void sirenStrategyRespondsToMotion() {
        SmartAlarm alarm = new SmartAlarm("Security");
        alarm.arm();
        alarm.setAlertStrategy("SIREN");
        console.reset();

        alarm.update("Kitchen motion detected");

        assertTrue(output().contains("SOUNDING 120dB SIREN!"));
    }
}
