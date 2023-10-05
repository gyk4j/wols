package gyk4j.wols.repository;

import java.util.List;

import gyk4j.wols.io.ResourceLoader;

public class CountryCodesRepository {
	public static final String COUNTRY_CODES_CHARSET = "ISO-8859-1";
	public static final String COUNTRY_CODES_FILE = "country-codes.txt";
	
	public String[] findAll() {
		// Path file = Paths.get(COUNTRY_CODES_FILE);
		List<String> allLines = ResourceLoader.readAllLines(COUNTRY_CODES_FILE);
		String[] codes = new String[allLines.size()];

		for (int i=0; i < allLines.size(); i++) {
			String line = allLines.get(i);
			int separator = line.indexOf(',');
			if (separator != -1) {
				String prefix = line.substring(0, separator);
				String country = line.substring(separator + 1);
				// System.out.println(String.format("%50s = %s", country, prefix));

				codes[i] = String.format("(+%s) %s", prefix, country);
			}
		}
		return codes;
	}
}
