package hu.szrnkapeter.minihttpserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
	private static SslSocketConnector createConnector(final Config config) {
		final SslSocketConnector connector = new SslSocketConnector();
		connector.setPort(443);
		connector.setMaxIdleTime(30000);
		connector.setKeystore(config.getKeystoreLocation());
		connector.setKeyPassword(config.getKeystorePassword());
		connector.setTrustPassword(config.getTruststorePassword());
		connector.setTruststore(config.getTruststoreLocation());
		return connector;
	}

	public static void main(final String[] args) throws Exception {
		final Config config = PropertyUtil.loadProperties();

		final Server server = new Server(config.getServerPort());

		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setResourceBase(config.getWwwDir());
		context.setContextPath("/");

		final ServletHolder holderHome = new ServletHolder(DefaultServlet.class);
		holderHome.setInitParameter("resourceBase", config.getWwwDir());
		holderHome.setInitParameter("dirAllowed", "true");
		holderHome.setInitParameter("pathInfoOnly", "true");
		context.addServlet(holderHome, "/*");
		server.setHandler(context);

		if ("https".equals(config.getServerType())) {
			server.addConnector(createConnector(config));
		}

		server.start();
		server.join();
	}
}
