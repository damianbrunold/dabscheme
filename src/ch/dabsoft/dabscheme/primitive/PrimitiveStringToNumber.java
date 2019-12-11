package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveStringToNumber extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1 && arguments.length != 2) throw new IllegalStateException("string->number expected 1 or 2 arguments, but got " + arguments.length);
        String s = new String(Value.asString(arguments[0]));
        boolean exact = s.indexOf('.') == -1;
        int base = 10;
        if (arguments.length == 2) base = Value.asInteger(arguments[1]).intValue();
        while (s.startsWith("#")) {
            if (s.startsWith("#e")) {
                exact = true;
                s = s.substring(2);
            } else if (s.startsWith("#i")) {
                exact = false;
                s = s.substring(2);
            } else if (s.startsWith("#x")) {
                base = 16;
                s = s.substring(2);
            } else if (s.startsWith("#b")) {
                base = 2;
                s = s.substring(2);
            } else if (s.startsWith("#o")) {
                base = 8;
                s = s.substring(2);
            } else {
                break;
            }
        }
        try {
            if (exact) return Long.parseLong(s, base);
            else return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return Value.F;
        }
    }

    @Override
    public String toString() {
        return "#<primitive:string->number>";
    }

}
