package asg02;

import asg02.products.configurations.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrototypeTest {

    @Test
    @DisplayName("clone() returns a different object reference")
    void cloneReturnsNewInstance() throws CloneNotSupportedException {
        Configuration original = new Configuration("10.0.0.1", "8080", "v1.0");
        Configuration cloned = original.clone();

        assertNotSame(original, cloned);
    }

    @Test
    @DisplayName("clone() copies all field values correctly")
    void cloneCopiesAllFields() throws CloneNotSupportedException {
        Configuration original = new Configuration("192.168.1.10", "443", "v3.4.1");
        Configuration cloned = original.clone();

        assertEquals(original.getIpAddress(), cloned.getIpAddress());
        assertEquals(original.getPort(), cloned.getPort());
        assertEquals(original.getFirmwareVersion(), cloned.getFirmwareVersion());
    }

    @Test
    @DisplayName("Changing clone IP does not affect original IP")
    void changingCloneIpDoesNotAffectOriginal() throws CloneNotSupportedException {
        Configuration original = new Configuration("192.168.1.10", "8080", "v3.4.1");
        Configuration cloned = original.clone();

        cloned.setIpAddress("192.168.1.99");

        assertEquals("192.168.1.10", original.getIpAddress());
        assertEquals("192.168.1.99", cloned.getIpAddress());
    }
}
