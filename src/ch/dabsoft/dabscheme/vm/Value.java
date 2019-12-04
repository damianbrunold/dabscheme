package ch.dabsoft.dabscheme.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Value {

    public static final Pair NIL = new Pair();
    public static final Boolean T = Boolean.TRUE;
    public static final Boolean F = Boolean.FALSE;

    private static Map<String, String> symbols = new HashMap<>();

    public static String intern(String string) {
        // this ensures that symbols with identical names are
        // represented by identical string objects. thus, we
        // can compare symbols using the simple == operator.
        String result = symbols.get(string);
        if (result == null) {
            result = string;
            symbols.put(string, result);
        }
        return result;
    }

    public static Values asValues(Object value) { return (Values) value; }
    public static Long asInteger(Object value) {
        return (Long) value;
    }
    public static Double asReal(Object value) {
        return (Double) value;
    }
    public static Boolean asBoolean(Object value) {
        return (Boolean) value;
    }
    public static String asSymbol(Object value) {
        return (String) value;
    }
    public static char[] asString(Object value) {
        return (char[]) value;
    }
    public static Character asChar(Object value) {
        return (Character) value;
    }
    public static Lambda asLambda(Object value) {
        return (Lambda) value;
    }
    public static Primitive asPrimitive(Object value) {
        return (Primitive) value;
    }
    public static Pair asPair(Object value) {
        return (Pair) value;
    }
    @SuppressWarnings({"unchecked"})
    public static List<Object> asVector(Object value) {
        return (List<Object>) value;
    }

    public static boolean isValues(Object value) { return value instanceof Values; }
    public static boolean isInteger(Object value) {
        return value instanceof Long;
    }
    public static boolean isReal(Object value) {
        return value instanceof Double;
    }
    public static boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }
    public static boolean isSymbol(Object value) {
        return value instanceof String;
    }
    public static boolean isString(Object value) {
        return value instanceof char[];
    }
    public static boolean isChar(Object value) {
        return value instanceof Character;
    }
    public static boolean isVector(Object value) {
        return value instanceof List;
    }
    public static boolean isPair(Object value) {
        return value instanceof Pair;
    }
    public static boolean isLambda(Object value) {
        return value instanceof Lambda;
    }
    public static boolean isPrimitive(Object value) {
        return value instanceof Primitive;
    }

    public static boolean isAtom(Object value) { return isConstant(value) || isSymbol(value); }
    public static boolean isConstant(Object value) { return isInteger(value) || isReal(value) || isBoolean(value) || isString(value) || isChar(value); }

    public static String printRep(Object value) {
        if (isValues(value)) return printRepValues(asValues(value));
        if (isInteger(value) || isReal(value) || isSymbol(value)) return value.toString();
        if (isBoolean(value)) return asBoolean(value) ? "#t" : "#f";
        if (isChar(value)) return printRepChar(asChar(value));
        if (isString(value)) return printRepString(asString(value));
        if (isPair(value)) return printRepPair(asPair(value));
        if (isVector(value)) return printRepVector(asVector(value));
        return value.toString(); // TODO
    }

    private static String printRepValues(Values values) {
        StringBuilder result = new StringBuilder();
        for (Object value : values.values) {
            result.append(printRep(value)).append("\n");
        }
        if (result.length() > 0) result.setLength(result.length() - 1);
        return result.toString();
    }

    private static String printRepChar(char ch) {
        switch (ch) {
            case ' ': return "#\\space";
            case '\t': return "#\\tab";
            case '\r': return "#\\return";
            case '\n': return "#\\newline";
            default: return "#\\" + ch;
        }
    }

    private static String printRepString(char[] str) {
        StringBuilder result = new StringBuilder("\"");
        result.append(new String(str).replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t"));
        result.append("\"");
        return result.toString();
    }

    private static String printRepVector(List<Object> elements) {
        StringBuilder result = new StringBuilder("#(");
        for (Object obj : elements) {
            result.append(printRep(obj)).append(' ');
        }
        if (result.charAt(result.length() - 1) == ' ') result.setCharAt(result.length() - 1, ')');
        else result.append(')');
        return result.toString();
    }

    private static String printRepPair(Pair pair) {
        if (pair.car == null && pair.cdr == null) return "()";
        if (Value.isSymbol(pair.car) && Value.asSymbol(pair.car).equals("quote")) {
            return "'" + printRep(Value.asPair(pair.cdr).car);
        } else if (Value.isSymbol(pair.car) && Value.asSymbol(pair.car).equals("quasiquote")) {
            return "`" + printRep(Value.asPair(pair.cdr).car);
        } else if (Value.isSymbol(pair.car) && Value.asSymbol(pair.car).equals("unquote")) {
            return "," + printRep(Value.asPair(pair.cdr).car);
        } else if (Value.isSymbol(pair.car) && Value.asSymbol(pair.car).equals("unquote-splicing")) {
            return ",@" + printRep(Value.asPair(pair.cdr).car);
        }
        StringBuilder result = new StringBuilder("(");
        Pair current = pair;
        while (true) {
            result.append(printRep(current.car));
            if (!Value.isPair(current.cdr)) {
                result.append(" . ").append(printRep(current.cdr)).append(")");
                break;
            } else if (current.cdr == Value.NIL) {
                result.append(")");
                break;
            } else {
                result.append(" ");
            }
            current = Value.asPair(current.cdr);
        }
        return result.toString();
    }

    public static String displayRep(Object value) {
        return value.toString(); // TODO
    }
}
