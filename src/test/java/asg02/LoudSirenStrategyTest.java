package asg02;

import asg02.products.strategies.AlertStrategy;
import asg02.products.strategies.LoudSirenStrategy;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class LoudSirenStrategyTest {
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
    @DisplayName("LoudSirenStrategy implements AlertStrategy interface")
    void implementsAlertStrategy() {
        LoudSirenStrategy strategy = new LoudSirenStrategy();
        assertInstanceOf(AlertStrategy.class, strategy);
    }

    @Test
    @DisplayName("executeAlert() prints loud siren message")
    void executeAlertPrintsLoudMessage() {
        LoudSirenStrategy strategy = new LoudSirenStrategy();
        strategy.executeAlert("Test Alarm");

        assertEquals("SOUNDING 120dB SIREN!", output());
    }

    @Test
    @DisplayName("executeAlert() works with different alarm names")
    void executeAlertWorksWithDifferentNames() {
        LoudSirenStrategy strategy = new LoudSirenStrategy();

        strategy.executeAlert("Main Alarm");
        String output1 = output();
        console.reset();

        strategy.executeAlert("Emergency Alert");
        String output2 = output();

        assertEquals(output1, output2);
        assertTrue(output1.contains("120dB SIREN"));
    }

    @Test
    @DisplayName("executeAlert() is consistent across multiple calls")
    void executeAlertIsConsistent() {
        LoudSirenStrategy strategy = new LoudSirenStrategy();

        for (int i = 0; i < 5; i++) {
            console.reset();
            strategy.executeAlert("Alarm " + i);
            assertEquals("SOUNDING 120dB SIREN!", output());
        }
    }

    @Test
    @DisplayName("LoudSirenStrategy output differs from SilentPushStrategy")
    void outputDiffersFromSilentStrategy() {
        LoudSirenStrategy loudStrategy = new LoudSirenStrategy();
        loudStrategy.executeAlert("Test");
        String loudOutput = output();

        console.reset();

        asg02.products.strategies.SilentPushStrategy silentStrategy = new asg02.products.strategies.SilentPushStrategy();
        silentStrategy.executeAlert("Test");
        String silentOutput = output();

        assertNotEquals(loudOutput, silentOutput);
    }
}
