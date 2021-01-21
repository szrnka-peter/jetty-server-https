package hu.szrnkapeter.minihttpserver;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class WebServer {

	private Config config;
	private final Server server;

	public WebServer(final Config config) {
		this.config = config;
		server = new Server(config.getServerPort());
	}

	private Connector createConnector() {
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath(config.getKeystoreLocation());
		sslContextFactory.setKeyStorePassword(config.getTruststorePassword());
		sslContextFactory.setTrustStorePassword(config.getTruststorePassword());
		sslContextFactory.setTrustStorePath(config.getTruststoreLocation());
		
		HttpConfiguration https = new HttpConfiguration();
		https.addCustomizer(new SecureRequestCustomizer());
		
		final ServerConnector connector = new ServerConnector(server,
				new SslConnectionFactory(sslContextFactory, "http/1.1"),
				new HttpConnectionFactory(https));
		connector.setPort(443);
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
