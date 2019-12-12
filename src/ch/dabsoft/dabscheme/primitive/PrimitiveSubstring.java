package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveSubstring extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length == 0 || arguments.length > 3) throw new IllegalStateException("substring expected between 1 and 3 arguments, but got " + arguments.length);
        char[] s = Value.asString(arguments[0]);
        int start = 0;
        int end = s.length;
        if (arguments.length > 1) {
            start = Value.asInteger(arguments[1]).intValue();
            if (start < 0) start = s.length + start;
        }
        if (arguments.length > 2) {
            end = Value.asInteger(arguments[2]).intValue();
            if (end < 0) end = s.length + end;
        }
        return new String(s).substring(start, end).toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:substring>";
    }

}
