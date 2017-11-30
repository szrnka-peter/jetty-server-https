package hu.szrnkapeter.minihttpserver;

import org.junit.Assert;
import org.junit.Test;

public class PropertyUtilTest {

	@Test
	public void test_withoutPropertyFile() {
		final Config config = PropertyUtil.loadProperties("test.properties");
		Assert.assertEquals("Wrong server type!", "http", config.getServerType());
	}

	@Test
	public void test_withPropertyFile() {
		final Config config = PropertyUtil.loadProperties();

		Assert.assertEquals("Wrong server type!", "https", config.getServerType());
	}
}
