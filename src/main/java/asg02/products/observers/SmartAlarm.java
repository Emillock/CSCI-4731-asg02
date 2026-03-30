package asg02.products.observers;

import asg02.products.strategies.AlertStrategy;
import asg02.products.strategies.SilentPushStrategy;
import asg02.products.strategies.LoudSirenStrategy;

import java.util.HashMap;
import java.util.Map;

public class SmartAlarm implements Observer {
    private final String name;
    private AlertStrategy currentStrategy;
    private final Map<String, AlertStrategy> strategyRegistry;
    private boolean isArmed;

    public SmartAlarm(String name) {
        this.name = name;
        this.strategyRegistry = new HashMap<>();
        this.isArmed = false;

        // Pre-register all available strategies for O(1) lookup
        strategyRegistry.put("SILENT", new SilentPushStrategy());
        strategyRegistry.put("SIREN", new LoudSirenStrategy());

        // Set default strategy
        this.currentStrategy = strategyRegistry.get("SILENT");
    }

    @Override
    public void update(String event) {
        if (isArmed && event.contains("motion")) {
            currentStrategy.executeAlert(name);
        }
    }

    // Strategy pattern method - O(1) lookup, no if/else conditionals
    public void setAlertStrategy(String strategyName) {
        AlertStrategy strategy = strategyRegistry.get(strategyName);
        if (strategy != null) {
            this.currentStrategy = strategy;
            System.out.println(name + " alert strategy changed to " + strategyName);
        } else {
            System.out.println("Unknown strategy: " + strategyName);
        }
    }

    // Command pattern receiver methods
    public void arm() {
        isArmed = true;
        System.out.println(name + " is now ARMED");
    }

    public void disarm() {
        isArmed = false;
        System.out.println(name + " is now DISARMED");
    }

    // Getters
    public String getName() {
        return name;
    }

    public boolean isArmed() {
        return isArmed;
    }

    public AlertStrategy getCurrentStrategy() {
        return currentStrategy;
    }
}
