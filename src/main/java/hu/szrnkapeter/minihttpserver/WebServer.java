package hu.szrnkapeter.minihttpserver;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServer {

	private Config config;
	private final Server server;

	public WebServer(final Config config) {
		this.config = config;
		server = new Server(config.getServerPort());
	}

	private SslSocketConnector createConnector() {
		final SslSocketConnector connector = new SslSocketConnector();
		connector.setPort(443);
		connector.setMaxIdleTime(30000);
		connector.setKeystore(config.getKeystoreLocation());
		connector.setKeyPassword(config.getKeystorePassword());
		connector.setTrustPassword(config.getTruststorePassword());
		connector.setTruststore(config.getTruststoreLocation());
		return connector;
	}

	public void start() throws Exception {
		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setResourceBase(config.getWwwDir());
		context.setContextPath("/");

		final ServletHolder holderHome = new ServletHolder(DefaultServlet.class);
		holderHome.setInitParameter("resourceBase", config.getWwwDir());
		holderHome.setInitParameter("dirAllowed", "true");
		holderHome.setInitParameter("pathInfoOnly", "true");
		context.addServlet(holderHome, "/*");
		server.setHandler(context);

		if ("https".equals(config.getServerType()) && new File(config.getKeystoreLocation()).exists()) {
			server.addConnector(createConnector());
		}

		server.start();
		server.join();
	}
}
