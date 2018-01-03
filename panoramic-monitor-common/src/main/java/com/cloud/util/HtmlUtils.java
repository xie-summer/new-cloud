package com.cloud.util;
/**
 * Utility class for HTML escaping. Escapes and unescapes
 * based on the W3C HTML 4.01 recommendation, handling
 * character entity references.
 *
 * <p>Reference:
 * <a href="http://www.w3.org/TR/html4/charset.html">http://www.w3.org/TR/html4/charset.html</a>
 *
 * <p>For a comprehensive set of String escaping utilities,
 * consider Jakarta Commons Lang and its StringEscapeUtils class.
 * We are not using that class here to avoid a runtime dependency
 * on Commons Lang just for HTML escaping. Furthermore, Spring's
 * HTML escaping is more flexible and 100% HTML 4.0 compliant.
 * Copy From Spring
 * @author Juergen Hoeller
 * @author Martin Kersten
 * @since 01.03.2003
 * @see org.apache.commons.lang.StringEscapeUtils
 */
public abstract class HtmlUtils {

	/**
	 * Shared instance of pre-parsed HTML character entity references.
	 */
	private static final HtmlCharacterEntityReferences characterEntityReferences =
			new HtmlCharacterEntityReferences();


	/**
	 * Turn special characters into HTML character references.
	 * Handles complete character set defined in HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding
	 * entity reference (e.g. <code>&lt;</code>).
	 * <p>Reference:
	 * <a href="http://www.w3.org/TR/html4/sgml/entities.html">
	 * http://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @return the escaped string
	 */
	public static String htmlEscape(String input) {
		if (input == null) {
			return null;
		}
		StringBuilder escaped = new StringBuilder(input.length() * 2);
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			String reference = characterEntityReferences.convertToReference(character);
			if (reference != null) {
				escaped.append(reference);
			}
			else {
				escaped.append(character);
			}
		}
		return escaped.toString();
	}

	/**
	 * Turn special characters into HTML character references.
	 * Handles complete character set defined in HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding numeric
	 * reference in decimal format (&#<i>Decimal</i>;).
	 * <p>Reference:
	 * <a href="http://www.w3.org/TR/html4/sgml/entities.html">
	 * http://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @return the escaped string
	 */
	public static String htmlEscapeDecimal(String input) {
		if (input == null) {
			return null;
		}
		StringBuilder escaped = new StringBuilder(input.length() * 2);
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			if (characterEntityReferences.isMappedToReference(character)) {
				escaped.append(HtmlCharacterEntityReferences.DECIMAL_REFERENCE_START);
				escaped.append((int) character);
				escaped.append(HtmlCharacterEntityReferences.REFERENCE_END);
			}
			else {
				escaped.append(character);
			}
		}
		return escaped.toString();
	}

	/**
	 * Turn special characters into HTML character references.
	 * Handles complete character set defined in HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding numeric
	 * reference in hex format (&#x<i>Hex</i>;).
	 * <p>Reference:
	 * <a href="http://www.w3.org/TR/html4/sgml/entities.html">
	 * http://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @return the escaped string
	 */
	public static String htmlEscapeHex(String input) {
		if (input == null) {
			return null;
		}
		StringBuilder escaped = new StringBuilder(input.length() * 2);
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			if (characterEntityReferences.isMappedToReference(character)) {
				escaped.append(HtmlCharacterEntityReferences.HEX_REFERENCE_START);
				escaped.append(Integer.toString(character, 16));
				escaped.append(HtmlCharacterEntityReferences.REFERENCE_END);
			}
			else {
				escaped.append(character);
			}
		}
		return escaped.toString();
	}
	public static String htmlUnescape(String input) {
		if (input == null) {
			return null;
		}
		return new HtmlCharacterEntityDecoder(characterEntityReferences, input).decode();
	}

}
