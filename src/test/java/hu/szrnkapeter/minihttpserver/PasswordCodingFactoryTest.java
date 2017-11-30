package hu.szrnkapeter.minihttpserver;

import org.junit.Assert;
import org.junit.Test;

public class PasswordCodingFactoryTest {

	@Test
	public void test_base64() {
		final PasswordCodingFactory factory = new PasswordCodingFactory("base64");

		final String encodedString = factory.getManager().encode("password");
		Assert.assertEquals("Wrong encoded string!", "cGFzc3dvcmQ=", encodedString);

		final String decodedString = factory.getManager().decode(encodedString);
		Assert.assertEquals("Wrong decoded string!", "password", decodedString);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test_unknown() {
		final PasswordCodingFactory factory = new PasswordCodingFactory("base85");
		factory.getManager().encode("password");
	}
}
