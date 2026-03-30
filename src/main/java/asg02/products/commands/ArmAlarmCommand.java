package asg02.products.commands;

import asg02.products.observers.SmartAlarm;

public class ArmAlarmCommand implements Command {
    private final SmartAlarm alarm;

    public ArmAlarmCommand(SmartAlarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.arm();
    }

    @Override
    public void undo() {
        alarm.disarm();
    }
}
