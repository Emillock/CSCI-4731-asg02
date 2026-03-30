package asg02;

import asg02.products.strategies.AlertStrategy;
import asg02.products.strategies.SilentPushStrategy;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SilentPushStrategyTest {
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
    @DisplayName("SilentPushStrategy implements AlertStrategy interface")
    void implementsAlertStrategy() {
        SilentPushStrategy strategy = new SilentPushStrategy();
        assertInstanceOf(AlertStrategy.class, strategy);
    }

    @Test
    @DisplayName("executeAlert() prints silent push notification message")
    void executeAlertPrintsSilentMessage() {
        SilentPushStrategy strategy = new SilentPushStrategy();
        strategy.executeAlert("Test Alarm");

        assertEquals("Sending silent push notification to homeowner's phone.", output());
    }

    @Test
    @DisplayName("executeAlert() works with different alarm names")
    void executeAlertWorksWithDifferentNames() {
        SilentPushStrategy strategy = new SilentPushStrategy();

        strategy.executeAlert("Front Door");
        String output1 = output();
        console.reset();

        strategy.executeAlert("Back Entrance");
        String output2 = output();

        assertEquals(output1, output2);
        assertTrue(output1.contains("silent push notification"));
    }

    @Test
    @DisplayName("executeAlert() is consistent across multiple calls")
    void executeAlertIsConsistent() {
        SilentPushStrategy strategy = new SilentPushStrategy();

        for (int i = 0; i < 5; i++) {
            console.reset();
            strategy.executeAlert("Alarm " + i);
            assertEquals("Sending silent push notification to homeowner's phone.", output());
        }
    }
}
