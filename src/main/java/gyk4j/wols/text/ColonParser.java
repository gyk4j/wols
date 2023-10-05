package gyk4j.wols.text;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class ColonParser implements StdoutParser {

	public static final char KEY_SEPARATOR_CHAR = '/';
	public static final String KEY_SEPARATOR_STR = Character.toString(KEY_SEPARATOR_CHAR);

	@Override
	public Map<String, String> parse(String s) {
		Map<String, String> data = new LinkedHashMap<String, String>();

		Stack<ParentNode> parents = new Stack<ParentNode>();
		int pIndentation = 0;
		String pKey = "";
		String pValue = "";
		int pSeparatorPos = 0;

		String[] lines = s.split(System.lineSeparator());
		for (String l : lines) {
			if (l.trim().length() == 0) {
				continue;
			}

			int indentation = countSpaces(l);

			// child
			if (pIndentation < indentation) {
				ParentNode p = new ParentNode(pIndentation, pKey, pValue);
				parents.push(p);
			}
			// ancestor - remove all siblings and children
			else { // if(parents.peek().getSpaces() >= indentation) {
				while (!parents.isEmpty() && parents.peek().getSpaces() >= indentation) {
					// ParentNode removed =
					parents.pop();
				}
				pIndentation = (parents.isEmpty()) ? 0 : parents.peek().getSpaces();
				pKey = (parents.isEmpty()) ? "" : parents.peek().getKey();
				pValue = (parents.isEmpty()) ? "" : parents.peek().getValue();
			}

			int separatorPos = l.indexOf(':');

			String[] kv = new String[2];

			// Key-value pair, or header with trailing colon (empty string value)
			if (separatorPos != -1) {
				kv[0] = trimEllipsis(l.substring(0, separatorPos)).trim();
				kv[1] = l.substring(separatorPos + 1).trim();
			}
			// Indented value placed on following line after the previous line colon
			// separator
			else if (separatorPos == -1 && indentation > pSeparatorPos) {
				// System.out.println("[Indented child] " + l);
				kv[0] = pKey;
				kv[1] = l.trim();
				if (!parents.isEmpty()) {
					parents.pop();
				}
			}
			// Indented child data but not key value pair
			else if (separatorPos == -1 && indentation > pIndentation) {
				kv[0] = null;
				kv[1] = null;

				splitKeyValuePairs(data, l.trim(), ",", "=", parents);
				continue;
			}
			// Header without colon
			else {
				// System.out.println("[Header without colon] " + l);
				kv[0] = l.trim();
				kv[1] = "";
			}

			data.merge(getParentPrefix(parents).concat(kv[0]), kv[1], (oldVal, newVal) -> {
				return oldVal.concat(", ").concat(newVal);
			});

			pKey = kv[0]; // Save in case the next line is a child.
			pValue = kv[1];

			pIndentation = indentation;
			pSeparatorPos = separatorPos;
		}
		return data;
	}

	private int countSpaces(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isWhitespace(s.charAt(i))) {
				count++;
			} else {
				break;
			}
		}

		return count;
	}

	private String getParentPrefix(Stack<ParentNode> parents) {
		final StringBuilder sb = new StringBuilder();
		parents.forEach(p -> {
			sb.append(p.toString());
			sb.append(KEY_SEPARATOR_CHAR);
		});
		String s = sb.toString();
		return s;
	}

	private String trimEllipsis(String s) {
		String o = s.replaceAll("[ \\.]+$", "").trim();
		return o;
	}

	/**
	 * 
	 * @param data
	 * @param line
	 * @param tokenSeparator
	 * @param keyValueSeparator
	 * @param parents
	 */
	private void splitKeyValuePairs(Map<String, String> data, String line, String tokenSeparator,
			String keyValueSeparator, Stack<ParentNode> parents) {

		String[] parts = line.split(tokenSeparator);
		for (String part : parts) {
			String[] kv = part.trim().split(keyValueSeparator);
			
			data.merge(getParentPrefix(parents).concat(kv[0]).trim(), kv[1].trim(), (oldVal, newVal) -> {
				return oldVal.concat(", ").concat(newVal);
			});
		}
	}

	class ParentNode {
		int spaces;
		String key;
		String value;

		public ParentNode(int spaces, String key, String value) {
			this.spaces = spaces;
			this.key = key;
			this.value = value;
		}

		public int getSpaces() {
			return spaces;
		}

		public void setSpaces(int spaces) {
			this.spaces = spaces;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append(key.trim());
			if (key.length() > 0 && value.length() > 0) {
				sb.append(':');
			}
			sb.append(value.trim());

			return sb.toString();
		}
	}
	
	public static boolean isTopLevelKey(String key) {
		return key.indexOf(ColonParser.KEY_SEPARATOR_CHAR) == -1;
	}
	
	public static String buildKey(String... keys) {
		return String.join(KEY_SEPARATOR_STR, keys);
	}

	public static String getValue(Map<String, String> map, String... keys) {
		return map.get(buildKey(keys));
	}
}
