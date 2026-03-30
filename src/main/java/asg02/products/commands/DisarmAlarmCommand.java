package asg02.products.commands;

import asg02.products.observers.SmartAlarm;

public class DisarmAlarmCommand implements Command {
    private final SmartAlarm alarm;

    public DisarmAlarmCommand(SmartAlarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.disarm();
    }

    @Override
    public void undo() {
        alarm.arm();
    }
}
