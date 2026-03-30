package asg02;

import asg02.products.abstracts.SmartLight;
import asg02.products.abstracts.SmartLock;
import asg02.products.abstracts.SmartThermostat;
import asg02.products.concretes.SmartDevice;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SmartDeviceTest {
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

    @Nested
    @DisplayName("BudgetLight")
    class BudgetLightTests {

        @Test
        @DisplayName("Extends SmartDevice")
        void extendsSmartDevice() {
            assertInstanceOf(SmartDevice.class, new SmartDevice.BudgetLight());
        }

        @Test
        @DisplayName("Implements SmartLight")
        void implementsSmartLight() {
            assertInstanceOf(SmartLight.class, new SmartDevice.BudgetLight());
        }

        @Test
        @DisplayName("turnOn() does not throw")
        void turnOnDoesNotThrow() {
            assertDoesNotThrow(() -> new SmartDevice.BudgetLight().turnOn());
        }

        @Test
        @DisplayName("turnOn() prints expected message")
        void turnOnPrintsMessage() {
            new SmartDevice.BudgetLight().turnOn();
            assertTrue(output().contains("BudgetLight"),
                    "Output should mention BudgetLight.");
        }

        @Test
        @DisplayName("Each call returns a new instance")
        void eachInstantiationIsUnique() {
            assertNotSame(new SmartDevice.BudgetLight(), new SmartDevice.BudgetLight());
        }
    }

    @Nested
    @DisplayName("BudgetLock")
    class BudgetLockTests {

        @Test
        @DisplayName("Extends SmartDevice")
        void extendsSmartDevice() {
            assertInstanceOf(SmartDevice.class, new SmartDevice.BudgetLock());
        }

        @Test
        @DisplayName("Implements SmartLock")
        void implementsSmartLock() {
            assertInstanceOf(SmartLock.class, new SmartDevice.BudgetLock());
        }

        @Test
        @DisplayName("lock() does not throw")
        void lockDoesNotThrow() {
            assertDoesNotThrow(() -> new SmartDevice.BudgetLock().lock());
        }

        @Test
        @DisplayName("lock() prints expected message")
        void lockPrintsMessage() {
            new SmartDevice.BudgetLock().lock();
            assertTrue(output().contains("BudgetLock"),
                    "Output should mention BudgetLock.");
        }
    }

    @Nested
    @DisplayName("BudgetThermostat")
    class BudgetThermostatTests {

        @Test
        @DisplayName("Extends SmartDevice")
        void extendsSmartDevice() {
            assertInstanceOf(SmartDevice.class, new SmartDevice.BudgetThermostat());
        }

        @Test
        @DisplayName("Implements SmartThermostat")
        void implementsSmartThermostat() {
            assertInstanceOf(SmartThermostat.class, new SmartDevice.BudgetThermostat());
        }

        @Test
        @DisplayName("setTemperature() does not throw")
        void setTemperatureDoesNotThrow() {
            assertDoesNotThrow(() -> new SmartDevice.BudgetThermostat().setTemperature(22.0));
        }

        @Test
        @DisplayName("setTemperature() prints the value passed in")
        void setTemperaturePrintsValue() {
            new SmartDevice.BudgetThermostat().setTemperature(21.5);
            assertTrue(output().contains("21.5"));
        }

        @Test
        @DisplayName("setTemperature() works with negative values")
        void setTemperatureNegative() {
            assertDoesNotThrow(() -> new SmartDevice.BudgetThermostat().setTemperature(-10.0));
            assertTrue(output().contains("-10.0"));
        }

        @Test
        @DisplayName("setTemperature() works with zero")
        void setTemperatureZero() {
            new SmartDevice.BudgetThermostat().setTemperature(0.0);
            assertTrue(output().contains("0.0"));
        }
    }


    @Test
    @DisplayName("Implements SmartLight")
    void implementsSmartLight() {
        assertInstanceOf(SmartLight.class, new SmartDevice.LuxuryLight());
    }

    @Test
    @DisplayName("turnOn() does not throw")
    void turnOnDoesNotThrow() {
        assertDoesNotThrow(() -> new SmartDevice.LuxuryLight().turnOn());
    }

    @Test
    @DisplayName("turnOn() prints expected message")
    void turnOnPrintsMessage() {
        new SmartDevice.LuxuryLight().turnOn();
        assertTrue(output().contains("LuxuryLight"));
    }


    @Test
    @DisplayName("Extends SmartDevice")
    void extendsSmartDevice() {
        assertInstanceOf(SmartDevice.class, new SmartDevice.LuxuryLock());
    }

    @Test
    @DisplayName("Implements SmartLock")
    void implementsSmartLock() {
        assertInstanceOf(SmartLock.class, new SmartDevice.LuxuryLock());
    }

    @Test
    @DisplayName("lock() does not throw")
    void lockDoesNotThrow() {
        assertDoesNotThrow(() -> new SmartDevice.LuxuryLock().lock());
    }

    @Test
    @DisplayName("lock() prints expected message")
    void lockPrintsMessage() {
        new SmartDevice.LuxuryLock().lock();
        assertTrue(output().contains("LuxuryLock"));
    }

    @Test
    @DisplayName("Implements SmartThermostat")
    void implementsSmartThermostat() {
        assertInstanceOf(SmartThermostat.class, new SmartDevice.LuxuryThermostat());
    }

    @Test
    @DisplayName("setTemperature() does not throw")
    void setTemperatureDoesNotThrow() {
        assertDoesNotThrow(() -> new SmartDevice.LuxuryThermostat().setTemperature(20.0));
    }

    @Test
    @DisplayName("setTemperature() prints the value passed in")
    void setTemperaturePrintsValue() {
        new SmartDevice.LuxuryThermostat().setTemperature(24.0);
        assertTrue(output().contains("24.0"));
    }

}
