package asg02.products.concretes;

import asg02.products.abstracts.SmartLight;
import asg02.products.abstracts.SmartLock;
import asg02.products.abstracts.SmartThermostat;
import asg02.products.legacy.GlorbThermostat;

public class SmartDevice {

    public static class BudgetLight extends SmartDevice implements SmartLight {
        @Override
        public void turnOn() {
            System.out.println("BudgetLight illuminated plastic");
        }
    }

    public static class BudgetLock extends SmartDevice implements SmartLock {
        @Override
        public void lock() {
            System.out.println("BudgetLock locked with PIN code");
        }
    }

    public static class BudgetThermostat extends SmartDevice implements SmartThermostat {
        @Override
        public void setTemperature(double celsius) {
            System.out.println("BudgetThermostat slowly sets temperature to " + celsius + "C");
        }
    }

    public static class LuxuryLight extends SmartDevice implements SmartLight {
        @Override
        public void turnOn() {
            System.out.println("LuxuryLight illuminated glass");
        }
    }

    public static class LuxuryLock extends SmartDevice implements SmartLock {
        @Override
        public void lock() {
            System.out.println("LuxuryLock locked with FaceID");
        }
    }

    public static class LuxuryThermostat extends SmartDevice implements SmartThermostat {
        @Override
        public void setTemperature(double celsius) {
            System.out.println("LuxuryThermostat with the help of AI sets temperature to " + celsius + "C");
        }
    }

    public static class GlorbAdapter extends SmartDevice implements SmartThermostat {
        private final GlorbThermostat glorb;

        public GlorbAdapter(GlorbThermostat glorb) {
            this.glorb = glorb;
        }

        @Override
        public void setTemperature(double celsius) {
            int fahrenheit = (int) Math.round((celsius * 9.0 / 5.0) + 32);
            System.out.println("GlorbAdapter Converting " + celsius + "C -> " + fahrenheit + "F");
            glorb.setHeatIndex(fahrenheit);
        }

        public double getTemperature(String type) {
            int heatIndex = glorb.getHeatIndex();
            if (type.equalsIgnoreCase("c")) {
                return (heatIndex - 32) * 5.0f / 9.0f;
            }
            else if (type.equalsIgnoreCase("f")) return heatIndex;
            else return Float.NaN;
        }
    }
}
