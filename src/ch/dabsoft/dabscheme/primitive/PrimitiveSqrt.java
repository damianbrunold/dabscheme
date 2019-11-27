package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveSqrt extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("sqrt expected 1 argument, but got " + arguments.length);
        double result = Math.sqrt(toReal(arguments[0]));
        if (Value.isInteger(arguments[0]) && result == (double) ((long) result)) return (long) result;
        return result;
    }

    @Override
    public String toString() {
        return "#<primitive:sqrt>";
    }

}
