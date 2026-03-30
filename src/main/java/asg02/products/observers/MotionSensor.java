package asg02.products.observers;

import java.util.ArrayList;
import java.util.List;

public class MotionSensor implements Subject {
    private final String location;
    private final List<Observer> observers;

    public MotionSensor(String location) {
        this.location = location;
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        String event = location + " motion detected";
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    public void detectMotion() {
        System.out.println(location + " MotionSensor detected movement!");
        notifyObservers();
    }

    public String getLocation() {
        return location;
    }
}
