package asg02.products.strategies;

public class SilentPushStrategy implements AlertStrategy {
    @Override
    public void executeAlert(String alarmName) {
        System.out.println("Sending silent push notification to homeowner's phone.");
    }
}
