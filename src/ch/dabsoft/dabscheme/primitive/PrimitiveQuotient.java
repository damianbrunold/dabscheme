package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveQuotient extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new SchemeError("quotient", "expected 2 arguments, but got " + arguments.length);
        if (allIntegers(arguments)) {
            return Value.asInteger(arguments[0]) / Value.asInteger(arguments[1]);
        } else {
            return (double) ((long) (toReal(arguments[0]) / toReal(arguments[1])));
        }
    }

    @Override
    public String toString() {
        return "#<primitive:quotient>";
    }

}
