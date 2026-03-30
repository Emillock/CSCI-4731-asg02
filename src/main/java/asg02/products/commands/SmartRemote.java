package asg02.products.commands;

public class SmartRemote {
    private static final int NUM_SLOTS = 5;
    private final Command[] slots;
    private Command lastCommand;

    public SmartRemote() {
        this.slots = new Command[NUM_SLOTS];
        this.lastCommand = null;
    }

    public void setCommand(int slot, Command command) {
        if (slot >= 0 && slot < NUM_SLOTS) {
            slots[slot] = command;
            System.out.println("Button " + slot + " programmed: " + command.getClass().getSimpleName());
        } else {
            System.out.println("Invalid button slot: " + slot);
        }
    }

    public void pressButton(int slot) {
        if (slot >= 0 && slot < NUM_SLOTS && slots[slot] != null) {
            System.out.println("Button " + slot + " pressed");
            slots[slot].execute();
            lastCommand = slots[slot];
        } else if (slot < 0 || slot >= NUM_SLOTS) {
            System.out.println("Invalid button slot: " + slot);
        } else {
            System.out.println("Button " + slot + " is not programmed");
        }
    }

    public void pressUndo() {
        if (lastCommand != null) {
            System.out.println("Undo executed");
            lastCommand.undo();
            lastCommand = null;
        } else {
            System.out.println("No command to undo");
        }
    }

    public Command getCommand(int slot) {
        if (slot >= 0 && slot < NUM_SLOTS) {
            return slots[slot];
        }
        return null;
    }

    public Command getLastCommand() {
        return lastCommand;
    }
}
