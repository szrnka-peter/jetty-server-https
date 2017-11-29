package hu.szrnkapeter.minihttpserver;

import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

public class PasswordCodingFactory {

	/**
	 * Concrete implementation of PasswordManager
	 *
	 */
	static class Base64PasswordManager implements PassWordManager {

		@Override
		public String decode(final String input) {
			return new String(DatatypeConverter.parseBase64Binary(input), StandardCharsets.UTF_8);
		}

		@Override
		public String encode(final String input) {
			return DatatypeConverter.printBase64Binary(input.getBytes());
		}

	}

	static interface PassWordManager {
		String decode(String input);

		String encode(String input);
	}

	private PassWordManager manager;

	public PasswordCodingFactory(final String method) {
		if ("base64".equals(method)) {
			manager = new Base64PasswordManager();
		} else {
			throw new UnsupportedOperationException("Currently only Base64 password decoding is supported!");
		}
	}

	public PassWordManager getManager() {
		return manager;
	}
}
