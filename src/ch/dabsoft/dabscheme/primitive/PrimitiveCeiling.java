package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCeiling extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new SchemeError("ceiling", "expected 1 argument, but got " + arguments.length);
        if (allIntegers(arguments)) {
            return Value.asInteger(arguments[0]);
        } else {
            return Math.ceil(toReal(arguments[0]));
        }
    }

    @Override
    public String toString() {
        return "#<primitive:ceiling>";
    }

}
