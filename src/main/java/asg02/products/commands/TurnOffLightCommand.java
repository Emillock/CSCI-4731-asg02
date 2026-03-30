package asg02.products.commands;

import asg02.products.observers.SmartLights;

public class TurnOffLightCommand implements Command {
    private final SmartLights lights;

    public TurnOffLightCommand(SmartLights lights) {
        this.lights = lights;
    }

    @Override
    public void execute() {
        lights.turnOff();
    }

    @Override
    public void undo() {
        lights.turnOn();
    }
}
