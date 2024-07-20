package unit;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.infrastructure.factories.LocalPlatformDetectorFactory;
import org.retrolauncher.backend.app.games.infrastructure.services.*;

import static org.junit.jupiter.api.Assertions.*;

public class LocalPlatformDetectorFactoryUnitTests {
    private final LocalPlatformDetectorFactory sut = new LocalPlatformDetectorFactory();

    @Test
    void it_should_be_able_to_create_a_nes_detector_service() {
        var result = sut.createFrom("Nintendo Entertainment System");
        assertEquals(NESPlatformDetectorService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_snes_detector_service() {
        var result = sut.createFrom("Super Nintendo Entertainment System");
        assertEquals(SNESPlatformDetectorService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_gb_detector_service() {
        var result = sut.createFrom("Game Boy/Color");
        assertEquals(GBCPlatformDetectorService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_sms_detector_service() {
        var result = sut.createFrom("Sega Master System");
        assertEquals(SMSPlatformDetectorService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_psx_detector_service() {
        var result = sut.createFrom("Playstation 1");
        assertEquals(PSXPlatformDetectorService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_default_detector_service() {
        var result = sut.createFrom("Nintendo 64");
        assertEquals(N64PlatformDetectorService.class, result.getClass());
    }

    @Test
    void it_should_not_be_able_to_create_a_undefined_detector_service() {
        Runnable result = () -> sut.createFrom("undefined");
        assertThrows(Exception.class, result::run);
    }
}
