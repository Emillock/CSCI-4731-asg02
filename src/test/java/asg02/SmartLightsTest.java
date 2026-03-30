package asg02;

import asg02.products.observers.Observer;
import asg02.products.observers.SmartLights;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SmartLightsTest {
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
    @DisplayName("SmartLights implements Observer interface")
    void implementsObserver() {
        SmartLights lights = new SmartLights("Living Room");
        assertInstanceOf(Observer.class, lights);
    }

    @Test
    @DisplayName("SmartLights stores location correctly")
    void storesLocation() {
        SmartLights lights = new SmartLights("Kitchen");
        assertEquals("Kitchen", lights.getLocation());
    }

    @Test
    @DisplayName("SmartLights starts in OFF state")
    void startsInOffState() {
        SmartLights lights = new SmartLights("Bedroom");
        assertFalse(lights.isOn());
    }

    @Test
    @DisplayName("turnOn() turns lights on and prints message")
    void turnOnTurnsLightsOn() {
        SmartLights lights = new SmartLights("Hallway");
        lights.turnOn();

        assertTrue(lights.isOn());
        assertEquals("Hallway SmartLights turned ON", output());
    }

    @Test
    @DisplayName("turnOff() turns lights off and prints message")
    void turnOffTurnsLightsOff() {
        SmartLights lights = new SmartLights("Garage");
        lights.turnOn();
        console.reset();

        lights.turnOff();

        assertFalse(lights.isOn());
        assertEquals("Garage SmartLights turned OFF", output());
    }

    @Test
    @DisplayName("update() with motion event turns lights on")
    void updateWithMotionTurnsLightsOn() {
        SmartLights lights = new SmartLights("Patio");
        lights.update("motion detected");

        assertTrue(lights.isOn());
        assertEquals("Patio SmartLights turned ON", output());
    }

    @Test
    @DisplayName("update() with motion keyword in event turns lights on")
    void updateWithMotionKeywordTurnsLightsOn() {
        SmartLights lights = new SmartLights("Office");
        lights.update("Hallway motion detected");

        assertTrue(lights.isOn());
        assertTrue(output().contains("Office SmartLights turned ON"));
    }

    @Test
    @DisplayName("update() without motion keyword does not turn lights on")
    void updateWithoutMotionDoesNothing() {
        SmartLights lights = new SmartLights("Basement");
        lights.update("some other event");

        assertFalse(lights.isOn());
        assertEquals("", output());
    }

    @Test
    @DisplayName("Multiple turnOn() calls work correctly")
    void multipleTurnOnCalls() {
        SmartLights lights = new SmartLights("Study");
        lights.turnOn();
        lights.turnOn();

        assertTrue(lights.isOn());
    }

    @Test
    @DisplayName("Multiple turnOff() calls work correctly")
    void multipleTurnOffCalls() {
        SmartLights lights = new SmartLights("Den");
        lights.turnOff();
        lights.turnOff();

        assertFalse(lights.isOn());
    }

    @Test
    @DisplayName("State toggles correctly between on and off")
    void stateTogglesCorrectly() {
        SmartLights lights = new SmartLights("Closet");

        assertFalse(lights.isOn());

        lights.turnOn();
        assertTrue(lights.isOn());

        lights.turnOff();
        assertFalse(lights.isOn());

        lights.turnOn();
        assertTrue(lights.isOn());
    }
}
