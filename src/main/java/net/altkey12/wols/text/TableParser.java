package net.altkey12.wols.text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Assumes table with first row as header row for columns
 * @author USER
 *
 */
public class TableParser implements StdoutParser {

	@Override
	public Map<String, String> parse(String s) {
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		List<Integer> columnIndices = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		
		String[] lines = s.split(System.lineSeparator());
		for(int i=0; i < lines.length; i++) {
			if(lines[i].length() == 0)
				continue;
			
			// Parse header row
			if(i==0) {
				String header = lines[i];
				
				char previous = ' ';
				char current;
				int columnStart = 0, columnEnd;
				for(int j=0; j < header.length(); j++) {
					current = header.charAt(j);
					
					// Start column
					if(!Character.isWhitespace(current) && (Character.isWhitespace(previous) || j == 0)) {
						columnStart = j;
						columnIndices.add(columnStart);
					}
					// End column
					else if((Character.isWhitespace(current) || j == header.length()-1) && !Character.isWhitespace(previous)) {
						columnEnd = (j == header.length()-1)? header.length(): j;
						String column = header.substring(columnStart, columnEnd);
						columns.add(column);
					}
					
					previous = current;
				}
				
//				System.out.println("Sanity check: " + columnIndices.size() + " = " + columns.size());
			}
			// Parse data value rows
			else {
				String values = lines[i];
				for(int ci=0; ci < columnIndices.size(); ci++) {
					int start = columnIndices.get(ci);
					int end = ((ci+1) < columnIndices.size())? columnIndices.get(ci+1): values.length();
					String value = values.substring(start, end).trim();
					data.merge(columns.get(ci), value, (oldVal, newVal) -> {
						return oldVal.concat(", ").concat(newVal);
					});
				}
				
//				System.out.println("Sanity check: " + columnIndices.size() + " = " + columns.size());
			}
		}
		
		return data;
	}

}
