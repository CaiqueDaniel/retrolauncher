package org.retrolauncher.app._shared.infrastructure.services;

import org.retrolauncher.app._shared.application.services.EnvConfigService;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class ProdEnvConfigService implements EnvConfigService {
    private final Properties config;

    public ProdEnvConfigService() {
        this.config = this.loadConfig();
    }

    @Override
    public String getRomsPath() {
        return this.config.getProperty("roms_path");
    }

    @Override
    public String getCoresPath() {
        return new StringBuilder(this.config.getProperty("retroarch_path")).append("/cores").toString();
    }

    @Override
    public String getRetroArchPath() {
        return new StringBuilder(this.config.getProperty("retroarch_path")).append("/retroarch").toString();
    }

    private Properties loadConfig() {
        try {
            String configFilePath = Objects.requireNonNull(
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResource("env.properties")
            ).getPath();

            Properties config = new Properties();
            config.load(new FileInputStream(configFilePath));
            return config;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
