package asg02;

import asg02.connections.CloudConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class CloudConnectionTest {

    @Test
    @DisplayName("Same instance is returned on repeated calls")
    void sameInstanceReturnedOnRepeatedCalls() {
        CloudConnection conn1 = CloudConnection.getInstance("KEY", "some.server.com");
        CloudConnection conn2 = CloudConnection.getInstance("KEY", "some.server.com");

        assertSame(conn1, conn2);
    }
}
