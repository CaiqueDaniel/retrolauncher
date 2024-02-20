package org.retrolauncher.app._shared.infrastructure.services;

import org.retrolauncher.app._shared.application.exceptions.NotAbleToLoadEnvPropertiesException;
import org.retrolauncher.app._shared.application.services.EnvConfigService;
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
            Properties envConfigProperties = new Properties();
            envConfigProperties.load(this.getClass().getClassLoader().getResourceAsStream("env.properties"));
            return envConfigProperties;
        } catch (Exception exception) {
            throw new NotAbleToLoadEnvPropertiesException(exception);
        }
    }
}
