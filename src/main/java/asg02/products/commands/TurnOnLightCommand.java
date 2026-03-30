package asg02.products.commands;

import asg02.products.observers.SmartLights;

public class TurnOnLightCommand implements Command {
    private final SmartLights lights;

    public TurnOnLightCommand(SmartLights lights) {
        this.lights = lights;
    }

    @Override
    public void execute() {
        lights.turnOn();
    }

    @Override
    public void undo() {
        lights.turnOff();
    }
}
