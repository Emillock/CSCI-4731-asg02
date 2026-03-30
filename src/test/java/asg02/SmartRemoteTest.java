package asg02;

import asg02.products.commands.*;
import asg02.products.observers.SmartAlarm;
import asg02.products.observers.SmartLights;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SmartRemoteTest {
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
    @DisplayName("SmartRemote initializes with empty slots")
    void initializesWithEmptySlots() {
        SmartRemote remote = new SmartRemote();
        assertNull(remote.getCommand(0));
        assertNull(remote.getCommand(1));
        assertNull(remote.getLastCommand());
    }

    @Test
    @DisplayName("setCommand() programs a button")
    void setCommandProgramsButton() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Test");
        TurnOnLightCommand command = new TurnOnLightCommand(lights);

        remote.setCommand(0, command);

        assertEquals(command, remote.getCommand(0));
        assertTrue(output().contains("Button 0 programmed: TurnOnLightCommand"));
    }

    @Test
    @DisplayName("setCommand() with invalid slot shows error")
    void setCommandInvalidSlot() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Test");
        TurnOnLightCommand command = new TurnOnLightCommand(lights);

        remote.setCommand(-1, command);
        assertTrue(output().contains("Invalid button slot"));

        console.reset();
        remote.setCommand(10, command);
        assertTrue(output().contains("Invalid button slot"));
    }

    @Test
    @DisplayName("pressButton() executes command and tracks last command")
    void pressButtonExecutesCommand() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Hallway");
        TurnOnLightCommand command = new TurnOnLightCommand(lights);

        remote.setCommand(0, command);
        console.reset();

        remote.pressButton(0);

        assertTrue(lights.isOn());
        assertEquals(command, remote.getLastCommand());
        assertTrue(output().contains("Button 0 pressed"));
        assertTrue(output().contains("Hallway SmartLights turned ON"));
    }

    @Test
    @DisplayName("pressButton() on unprogrammed button shows message")
    void pressButtonUnprogrammed() {
        SmartRemote remote = new SmartRemote();

        remote.pressButton(2);

        assertTrue(output().contains("Button 2 is not programmed"));
    }

    @Test
    @DisplayName("pressButton() with invalid slot shows error")
    void pressButtonInvalidSlot() {
        SmartRemote remote = new SmartRemote();

        remote.pressButton(-1);
        assertTrue(output().contains("Invalid button slot"));

        console.reset();
        remote.pressButton(10);
        assertTrue(output().contains("Invalid button slot"));
    }

    @Test
    @DisplayName("pressUndo() undoes last command")
    void pressUndoUndoesLastCommand() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Living Room");
        TurnOnLightCommand command = new TurnOnLightCommand(lights);

        remote.setCommand(0, command);
        remote.pressButton(0);
        console.reset();

        remote.pressUndo();

        assertFalse(lights.isOn());
        assertNull(remote.getLastCommand());
        assertTrue(output().contains("Undo executed"));
        assertTrue(output().contains("Living Room SmartLights turned OFF"));
    }

    @Test
    @DisplayName("pressUndo() without previous command shows message")
    void pressUndoWithoutCommand() {
        SmartRemote remote = new SmartRemote();

        remote.pressUndo();

        assertTrue(output().contains("No command to undo"));
    }

    @Test
    @DisplayName("pressUndo() clears last command after execution")
    void pressUndoClearsLastCommand() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Test");
        TurnOnLightCommand command = new TurnOnLightCommand(lights);

        remote.setCommand(0, command);
        remote.pressButton(0);
        remote.pressUndo();

        assertNull(remote.getLastCommand());

        console.reset();
        remote.pressUndo();
        assertTrue(output().contains("No command to undo"));
    }

    @Test
    @DisplayName("Multiple commands can be programmed")
    void multipleCommandsProgrammed() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Test");
        SmartAlarm alarm = new SmartAlarm("Test");

        TurnOnLightCommand cmd0 = new TurnOnLightCommand(lights);
        TurnOffLightCommand cmd1 = new TurnOffLightCommand(lights);
        ArmAlarmCommand cmd2 = new ArmAlarmCommand(alarm);

        remote.setCommand(0, cmd0);
        remote.setCommand(1, cmd1);
        remote.setCommand(2, cmd2);

        assertEquals(cmd0, remote.getCommand(0));
        assertEquals(cmd1, remote.getCommand(1));
        assertEquals(cmd2, remote.getCommand(2));
    }

    @Test
    @DisplayName("TurnOnLightCommand execute and undo work correctly")
    void turnOnLightCommandWorks() {
        SmartLights lights = new SmartLights("Test");
        TurnOnLightCommand command = new TurnOnLightCommand(lights);

        assertInstanceOf(Command.class, command);

        command.execute();
        assertTrue(lights.isOn());

        console.reset();
        command.undo();
        assertFalse(lights.isOn());
    }

    @Test
    @DisplayName("TurnOffLightCommand execute and undo work correctly")
    void turnOffLightCommandWorks() {
        SmartLights lights = new SmartLights("Test");
        lights.turnOn();
        console.reset();

        TurnOffLightCommand command = new TurnOffLightCommand(lights);
        assertInstanceOf(Command.class, command);

        command.execute();
        assertFalse(lights.isOn());

        console.reset();
        command.undo();
        assertTrue(lights.isOn());
    }

    @Test
    @DisplayName("ArmAlarmCommand execute and undo work correctly")
    void armAlarmCommandWorks() {
        SmartAlarm alarm = new SmartAlarm("Test");
        ArmAlarmCommand command = new ArmAlarmCommand(alarm);

        assertInstanceOf(Command.class, command);

        command.execute();
        assertTrue(alarm.isArmed());

        console.reset();
        command.undo();
        assertFalse(alarm.isArmed());
    }

    @Test
    @DisplayName("DisarmAlarmCommand execute and undo work correctly")
    void disarmAlarmCommandWorks() {
        SmartAlarm alarm = new SmartAlarm("Test");
        alarm.arm();
        console.reset();

        DisarmAlarmCommand command = new DisarmAlarmCommand(alarm);
        assertInstanceOf(Command.class, command);

        command.execute();
        assertFalse(alarm.isArmed());

        console.reset();
        command.undo();
        assertTrue(alarm.isArmed());
    }

    @Test
    @DisplayName("Complete workflow: program, execute, undo multiple commands")
    void completeWorkflow() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Kitchen");
        SmartAlarm alarm = new SmartAlarm("Home");

        // Program buttons
        remote.setCommand(0, new TurnOnLightCommand(lights));
        remote.setCommand(1, new ArmAlarmCommand(alarm));
        console.reset();

        // Execute commands
        remote.pressButton(0);
        assertTrue(lights.isOn());

        remote.pressButton(1);
        assertTrue(alarm.isArmed());

        // Undo last command (arm alarm)
        console.reset();
        remote.pressUndo();
        assertFalse(alarm.isArmed());
        assertTrue(lights.isOn()); // Lights should still be on

        // Try undo again (should show no command)
        console.reset();
        remote.pressUndo();
        assertTrue(output().contains("No command to undo"));
    }

    @Test
    @DisplayName("Last command updates correctly with multiple button presses")
    void lastCommandUpdatesCorrectly() {
        SmartRemote remote = new SmartRemote();
        SmartLights lights = new SmartLights("Test");

        TurnOnLightCommand cmd0 = new TurnOnLightCommand(lights);
        TurnOffLightCommand cmd1 = new TurnOffLightCommand(lights);

        remote.setCommand(0, cmd0);
        remote.setCommand(1, cmd1);
        console.reset();

        remote.pressButton(0);
        assertEquals(cmd0, remote.getLastCommand());

        remote.pressButton(1);
        assertEquals(cmd1, remote.getLastCommand());
    }
}
