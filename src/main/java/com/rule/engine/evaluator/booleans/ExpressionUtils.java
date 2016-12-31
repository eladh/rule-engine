package com.rule.engine.evaluator.booleans;


import com.rule.engine.evaluator.BooleanParser;
import com.rule.engine.evaluator.structures.Groups;
import com.rule.engine.evaluator.structures.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public final class ExpressionUtils {

	private static final Pattern SPLIT_ARGS_REGEX = Pattern.compile(",(?![^\\(\\[\\{]*\\))");

	public enum BracketType {
		parentheses("(", ")"),
		squareBrackets("[", "]"),
		braces("{", "}");

		private final String openBracket;
		private final String closeBracket;

		BracketType(String openBracket, String closeBracket) {
			this.openBracket = openBracket;
			this.closeBracket = closeBracket;
		}
	}

	private ExpressionUtils() {}

	public static Node processSplitRule(String rule) {
		return BooleanParser.parse(rule);
	}

	public static Pair<String, List<String>> extractIdAndArgs(String idAndArgs) throws UnsupportedOperationException {
		int bracketsIndex = idAndArgs.indexOf("(");

		if (bracketsIndex == -1) {
			return Groups.pair(idAndArgs, Collections.<String>emptyList());
		}

		String id = idAndArgs.substring(0, bracketsIndex);
		String[] commasSplit = SPLIT_ARGS_REGEX.split(idAndArgs.substring(bracketsIndex + 1, idAndArgs.length() - 1));

		if (commasSplit.length == 1 && commasSplit[0].trim().isEmpty()) {
			return Groups.pair(id, Collections.<String>emptyList());
		}

		List<String> args = new ArrayList<String>();
		for (final String s : commasSplit) {
			args.add(s.trim());
		}
		fixUnbalancedBrackets(args, BracketType.parentheses, ",");
		fixUnbalancedBrackets(args, BracketType.braces, ",");
		fixUnbalancedBrackets(args, BracketType.squareBrackets, ",");
		return Groups.pair(id, args);
	}


	/**
	 * join list items that was split incorrectly because the lazy regex cannot understand nested brackets, e.g. #60000( #60000() ) -> [ "#60000( #60000()" , ")" ]
	 *
	 * @param items         list of items
	 * @param bracketType   the bracket type to fix
	 * @param joinSeparator separator to use when joining items
	 */

	public static void fixUnbalancedBrackets(List<String> items, BracketType bracketType, String joinSeparator) {
		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);
			int spareOpeningBrackets = countSpareOpeningBrackets(item, bracketType);
			if (spareOpeningBrackets > 0) {
				// search for more closing brackets
				int realClosingBracketIndex = -1;
				for (int j = i + 1; j < items.size(); j++) {
					spareOpeningBrackets = spareOpeningBrackets + countSpareOpeningBrackets(items.get(j), bracketType);
					if (spareOpeningBrackets == 0) {
						realClosingBracketIndex = j;
						break;
					}
				}
				if (realClosingBracketIndex > 0) {
					// join bracket's items to one item
					List<String> subList = items.subList(i, realClosingBracketIndex + 1);
					String jointItem = StringUtils.join(subList, joinSeparator);
					subList.clear(); // remove old unbalanced items
					items.add(i, jointItem); // add new balanced item
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
	}

	private static int countSpareOpeningBrackets(String item, BracketType bracketType) {
		return StringUtils.countMatches(item, bracketType.openBracket) - StringUtils.countMatches(item, bracketType.closeBracket);
	}
}