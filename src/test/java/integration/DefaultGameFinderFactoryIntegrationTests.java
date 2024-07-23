package integration;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.infrastructure.factories.DefaultGameFinderFactory;
import org.retrolauncher.backend.app.games.infrastructure.services.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultGameFinderFactoryIntegrationTests {
    private final DefaultGameFinderFactory sut = new DefaultGameFinderFactory();

    @Test
    void it_should_be_able_to_factory_a_psx_finder_service() {
        var result = sut.createFrom("Playstation 1");
        assertEquals(PSXGameFinderService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_nes_finder_service() {
        var result = sut.createFrom("Nintendo Entertainment System");
        assertEquals(NESGameFinderService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_snes_finder_service() {
        var result = sut.createFrom("Super Nintendo Entertainment System");
        assertEquals(SNESGameFinderService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_gb_finder_service() {
        var result = sut.createFrom("Game Boy/Color");
        assertEquals(GBGameFinderService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_sms_finder_service() {
        var result = sut.createFrom("Sega Master System");
        assertEquals(SMSGameFinderService.class, result.getClass());
    }

    @Test
    void it_should_be_able_to_create_a_default_finder_service() {
        var result = sut.createFrom("Nintendo 64");
        assertEquals(N64GameFinderService.class, result.getClass());
    }


    @Test
    void it_should_not_factory_any_service_for_a_unknown_platform() {
        Runnable action = () -> sut.createFrom("unknown");
        assertThrows(RuntimeException.class, action::run);
    }
}
