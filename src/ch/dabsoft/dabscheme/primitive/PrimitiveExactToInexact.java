package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveExactToInexact extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("exact->inexact expected 1 argument, but got " + arguments.length);
        if (Value.isReal(arguments[0])) return arguments[0];
        return Value.asInteger(arguments[0]).doubleValue();
    }

    @Override
    public String toString() {
        return "#<primitive:exact->inexact>";
    }

}
