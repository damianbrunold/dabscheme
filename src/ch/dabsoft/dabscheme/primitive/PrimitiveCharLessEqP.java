package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCharLessEqP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length < 2) throw new IllegalStateException("char<=? expected at least 2 arguments, but got " + arguments.length);
        Object current = arguments[0];
        for (int i = 1; i < arguments.length; i++) {
            Object next = arguments[i];
            if (!(Value.asChar(current) <= Value.asChar(next))) return false;
            current = next;
        }
        return true;
    }

    @Override
    public String toString() {
        return "#<primitive:char<=?>";
    }

}
