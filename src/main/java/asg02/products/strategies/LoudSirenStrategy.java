package asg02.products.strategies;

public class LoudSirenStrategy implements AlertStrategy {
    @Override
    public void executeAlert(String alarmName) {
        System.out.println("SOUNDING 120dB SIREN!");
    }
}
