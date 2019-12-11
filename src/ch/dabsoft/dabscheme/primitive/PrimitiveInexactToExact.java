package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveInexactToExact extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("inexact->exact expected 1 argument, but got " + arguments.length);
        if (Value.isInteger(arguments[0])) return arguments[0];
        return Value.asReal(arguments[0]).longValue();
    }

    @Override
    public String toString() {
        return "#<primitive:inexact->exact>";
    }

}
