
package com.rule.engine.evaluator.booleans;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;


public class ExpressionRegex {

	public static final Predicate<String> NON_EMPTY_STRING = new Predicate<String>() {
		@Override
		public boolean evaluate(String s) {
			return !s.trim().isEmpty();
		}
	};

	public static final String JAVA_FUNCTION_PREFIX = "##";
	public static final String JS_FUNCTION_PREFIX = "#";
	public static final String RULE_PREFIX = "~";
	public static final String RULE_SUFFIX = "()";
	public static final String FORMULA_PREFIX = "$";
	public static final String OGNL_PREFIX = "@";
	public static final String MDID = "['\"](\\d*-\\d*)['\"]";

	public static final String[] OPERATORS_CHARS = {"-", "+", "*", "/", "%", "<", ">"};
	public static final String[] X_CHARS_OPERATORS = {"==", ">=", "<=", "!=", "&&", "\\|\\|"}; // operators with multiple chars
	public static final String[] AND_OR_OPERATORS = { "&&", "||"}; // operators with multiple chars

	public static final String[] LOGIC_CHARS = {"!!", "!"};

	public static final String PARENTHESES_EXPRESSION_REGEX = "\\(.*?\\)";
	public static final String PARENTHESES_EXPRESSION_REGEX_EXT = "\\((.*?)\\)";

	public static final String ARGUMENT_REGEX = "\\{(.+?)\\}"; // { argNum } e.g. {1}
	public static final String OPERATORS_REGEX = StringUtils.join(X_CHARS_OPERATORS, "|") + "|" + "[" + StringUtils.join(OPERATORS_CHARS) + "]";

	public static final String LOGIC_CHARS_REGEX = StringUtils.join(LOGIC_CHARS, "|");
	public static final Pattern FORMULA_FORMAT_REGEX = Pattern.compile("\\$(\\d+)(?:" + PARENTHESES_EXPRESSION_REGEX + ")?");
	public static final Pattern OGNL_FORMAT_REGEX = Pattern.compile(OGNL_PREFIX + "([\\w.]+)(?:" + PARENTHESES_EXPRESSION_REGEX + ")?"); // TODO remove parentheses from ognl regex, ognl setter is deprecated
	public static final Pattern OGNL_EXT_FORMAT_REGEX = Pattern.compile("(?:#get\\()?(?:#set\\()?" + OGNL_PREFIX + "([\\w.]+)(?:" + PARENTHESES_EXPRESSION_REGEX_EXT + ")?");
	public static final Pattern JS_FUNCTION_FORMAT_REGEX = Pattern.compile(JS_FUNCTION_PREFIX + "(\\w+)" + PARENTHESES_EXPRESSION_REGEX);
	public static final Pattern JAVA_FUNCTION_FORMAT_REGEX = Pattern.compile(JAVA_FUNCTION_PREFIX + "(\\w+)" + PARENTHESES_EXPRESSION_REGEX);
	public static final Pattern RULE_FORMAT_REGEX = Pattern.compile(RULE_PREFIX + "(\\d+)" + PARENTHESES_EXPRESSION_REGEX);
	public static final Pattern MDID_FORMAT_REGEX = Pattern.compile(MDID);
	public static final Pattern ARGUMENT_FORMAT_REGEX = Pattern.compile(ARGUMENT_REGEX);

	public static final Pattern SPLIT_ITEM_REGEX = Pattern.compile("-?\\d+(?:\\.\\d+)?" + '|' + OPERATORS_REGEX + "|" + LOGIC_CHARS_REGEX + "|"
			+ FORMULA_FORMAT_REGEX + "|" + OGNL_FORMAT_REGEX + "|" + JS_FUNCTION_FORMAT_REGEX + "|" + JAVA_FUNCTION_FORMAT_REGEX + "|"
			+ RULE_FORMAT_REGEX + "|" + ARGUMENT_REGEX + "|" + PARENTHESES_EXPRESSION_REGEX + "|" + "\'.*?\'" + "|" + "\".*?\"" + "|" + "[\\w\'\"]*");

}