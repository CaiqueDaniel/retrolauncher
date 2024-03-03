package integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app._shared.infrastructure.database.jackson.model.Model;
import org.retrolauncher.backend.database.FileDatabaseDriver;
import org.retrolauncher.backend.database.JacksonFileDatabaseDriver;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JacksonFileDatabaseDriverIntegrationTests {
    private final TestRepository sut = new TestRepository(
            new JacksonFileDatabaseDriver<>(TestModel.class),
            "./test.json"
    );

    @AfterEach
    void afterEach() throws IOException {
        sut.clear();
    }

    @Test
    void it_should_be_able_to_write_data() throws IOException {
        TestModel model = new TestModel(UUID.randomUUID(), "Teste");

        sut.save(model);
        assertEquals(1, sut.listAll().size());
    }
}

class TestRepository {
    private final FileDatabaseDriver<Map<String, TestModel>> driver;
    private final String filePath;

    public TestRepository(FileDatabaseDriver<Map<String, TestModel>> driver, String filePath) {
        this.driver = driver;
        this.filePath = filePath;
    }

    public void save(TestModel model) throws IOException {
        Map<String, TestModel> storage = this.driver.read(this.filePath);
        storage.put(model.getId().toString(), model);
        this.driver.write(storage, this.filePath);
    }

    public Map<String, TestModel> listAll() throws IOException {
        return this.driver.read(this.filePath);
    }

    public void clear() throws IOException {
        this.driver.clear(this.filePath);
    }
}

@Setter
@Getter
@NoArgsConstructor
class TestModel extends Model {
    @JsonProperty
    private String name;

    public TestModel(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}