package calc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static final Pattern ARABIC_PATTERN = Pattern.compile("(10|[1-9])([\\+\\-\\*/])(10|[1-9])");
	private static final Pattern ROMAN_PATTERN = Pattern.compile("([IVX]{1,4})([\\+\\-\\*/])([IVX]{1,4})");

	private static final Map<String, Integer> ROMAN_2_ARABIC_MAP = new HashMap<String, Integer>();

	private static final List<String> ROMAN_MAIN_NUMBERS = List.of("I", "IV", "V", "IX", "X", "XL", "L", "XC", "C");
	private static final List<Integer> ROMAN_MAIN_NUMBERS_INT = List.of(1, 4, 5, 9, 10, 40, 50, 90, 100);

	static {
		ROMAN_2_ARABIC_MAP.put("I", 1);
		ROMAN_2_ARABIC_MAP.put("II", 2);
		ROMAN_2_ARABIC_MAP.put("III", 3);
		ROMAN_2_ARABIC_MAP.put("IV", 4);
		ROMAN_2_ARABIC_MAP.put("V", 5);
		ROMAN_2_ARABIC_MAP.put("VI", 6);
		ROMAN_2_ARABIC_MAP.put("VII", 7);
		ROMAN_2_ARABIC_MAP.put("VIII", 8);
		ROMAN_2_ARABIC_MAP.put("IX", 9);
		ROMAN_2_ARABIC_MAP.put("X", 10);
	}

	private static String arabic2Roman(int number) {
		StringBuilder builder = new StringBuilder();
		for (int i = ROMAN_MAIN_NUMBERS.size() - 1; i >= 0; --i) {
			if (number < ROMAN_MAIN_NUMBERS_INT.get(i)) {
				continue;
			}
			builder.append(ROMAN_MAIN_NUMBERS.get(i));
			number -= ROMAN_MAIN_NUMBERS_INT.get(i);
			if (number == 0) {
				break;
			}
			++i;
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		if (args == null) {
			throw new IllegalArgumentException("Wrong input");
		}
		StringBuilder builder = new StringBuilder();
		for (String arg : args) {
			builder.append(arg);
		}
		System.out.println(calc(builder.toString()));
	}

	public static String calc(String input) {
		input = input.replace(" ", "");
		Matcher m = ARABIC_PATTERN.matcher(input);
		if (m.matches()) {
			int firstNum = Integer.parseInt(m.group(1));
			int secondNum = Integer.parseInt(m.group(3));
			String op = m.group(2);
			if (op.equals("+")) {
				return String.valueOf(firstNum + secondNum);
			} else if (op.equals("-")) {
				return String.valueOf(firstNum - secondNum);
			} else if (op.equals("*")) {
				return String.valueOf(firstNum * secondNum);
			} else if (op.equals("/")) {
				return String.valueOf(firstNum / secondNum);
			}
		}
		m = ROMAN_PATTERN.matcher(input);
		if (m.matches()) {
			Integer firstNum = ROMAN_2_ARABIC_MAP.get(m.group(1));
			if (firstNum == null) {
				throw new IllegalArgumentException("Wrong first argument");
			}
			Integer secondNum = ROMAN_2_ARABIC_MAP.get(m.group(3));
			if (secondNum == null) {
				throw new IllegalArgumentException("Wrong second argument");
			}
			String op = m.group(2);
			if (op.equals("+")) {
				return arabic2Roman(firstNum + secondNum);
			} else if (op.equals("-")) {
				int result = firstNum - secondNum;
				if (result <= 0) {
					throw new IllegalArgumentException("Result is not positive");
				}
				return arabic2Roman(result);
			} else if (op.equals("*")) {
				return arabic2Roman(firstNum * secondNum);
			} else if (op.equals("/")) {
				return arabic2Roman(firstNum / secondNum);
			}
		}
		throw new IllegalArgumentException("Wrong input");
	}

}
