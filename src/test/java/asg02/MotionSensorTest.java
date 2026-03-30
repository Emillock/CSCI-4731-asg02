package asg02;

import asg02.products.observers.MotionSensor;
import asg02.products.observers.Observer;
import asg02.products.observers.Subject;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MotionSensorTest {
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
    @DisplayName("MotionSensor implements Subject interface")
    void implementsSubject() {
        MotionSensor sensor = new MotionSensor("Living Room");
        assertInstanceOf(Subject.class, sensor);
    }

    @Test
    @DisplayName("MotionSensor stores location correctly")
    void storesLocation() {
        MotionSensor sensor = new MotionSensor("Kitchen");
        assertEquals("Kitchen", sensor.getLocation());
    }

    @Test
    @DisplayName("detectMotion() prints message")
    void detectMotionPrintsMessage() {
        MotionSensor sensor = new MotionSensor("Hallway");
        sensor.detectMotion();
        assertTrue(output().contains("Hallway MotionSensor detected movement!"));
    }

    @Test
    @DisplayName("addObserver() adds observer successfully")
    void addObserverAddsObserver() {
        MotionSensor sensor = new MotionSensor("Garage");
        MockObserver observer = new MockObserver();

        sensor.addObserver(observer);
        sensor.detectMotion();

        assertTrue(observer.wasNotified);
        assertEquals("Garage motion detected", observer.lastEvent);
    }

    @Test
    @DisplayName("addObserver() prevents duplicate observers")
    void addObserverPreventsDuplicates() {
        MotionSensor sensor = new MotionSensor("Bedroom");
        MockObserver observer = new MockObserver();

        sensor.addObserver(observer);
        sensor.addObserver(observer); // Try to add same observer twice
        sensor.detectMotion();

        assertEquals(1, observer.notificationCount);
    }

    @Test
    @DisplayName("addObserver() handles null gracefully")
    void addObserverHandlesNull() {
        MotionSensor sensor = new MotionSensor("Patio");
        assertDoesNotThrow(() -> sensor.addObserver(null));
    }

    @Test
    @DisplayName("removeObserver() removes observer successfully")
    void removeObserverRemovesObserver() {
        MotionSensor sensor = new MotionSensor("Basement");
        MockObserver observer = new MockObserver();

        sensor.addObserver(observer);
        sensor.removeObserver(observer);
        sensor.detectMotion();

        assertFalse(observer.wasNotified);
    }

    @Test
    @DisplayName("notifyObservers() notifies all registered observers")
    void notifyObserversNotifiesAll() {
        MotionSensor sensor = new MotionSensor("Office");
        MockObserver observer1 = new MockObserver();
        MockObserver observer2 = new MockObserver();
        MockObserver observer3 = new MockObserver();

        sensor.addObserver(observer1);
        sensor.addObserver(observer2);
        sensor.addObserver(observer3);
        sensor.detectMotion();

        assertTrue(observer1.wasNotified);
        assertTrue(observer2.wasNotified);
        assertTrue(observer3.wasNotified);
        assertEquals("Office motion detected", observer1.lastEvent);
        assertEquals("Office motion detected", observer2.lastEvent);
        assertEquals("Office motion detected", observer3.lastEvent);
    }

    // Mock Observer for testing
    private static class MockObserver implements Observer {
        boolean wasNotified = false;
        String lastEvent = null;
        int notificationCount = 0;

        @Override
        public void update(String event) {
            wasNotified = true;
            lastEvent = event;
            notificationCount++;
        }
    }
}
