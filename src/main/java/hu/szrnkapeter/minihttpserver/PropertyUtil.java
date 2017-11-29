package hu.szrnkapeter.minihttpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import hu.szrnkapeter.minihttpserver.PasswordCodingFactory.PassWordManager;

public class PropertyUtil {

	public static Config loadProperties() {
		final Config config = new Config();
		final Properties prop = new Properties();
		InputStream input = null;

		if (!new File("config.properties").exists()) {
			System.out.println("No configuration file exists. Server starts in HTTP mode.");
			config.setServerPort(8080);
			config.setServerType("HTTP");
			config.setWwwDir(".");
			return config;
		}

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			final PassWordManager passManager = new PasswordCodingFactory(prop.getProperty("password.encrypttype")).getManager();

			config.setEncriptType(prop.getProperty("password.encrypttype"));
			config.setKeystoreLocation(prop.getProperty("keystore.location"));
			config.setKeystorePassword(passManager.decode(prop.getProperty("keystore.password")));
			config.setServerPort(Integer.valueOf(prop.getProperty("server.port")));
			config.setServerType(prop.getProperty("server.type"));
			config.setWwwDir(prop.getProperty("www.dir"));
			config.setTruststoreLocation(prop.getProperty("truststore.location"));
			config.setTruststorePassword(passManager.decode(prop.getProperty("truststore.password")));

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		return config;
	}
}
