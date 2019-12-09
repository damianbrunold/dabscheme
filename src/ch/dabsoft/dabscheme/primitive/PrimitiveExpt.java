package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveExpt extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new SchemeError("expt", "expected 2 arguments, but got " + arguments.length);
        if (allIntegers(arguments)) {
            long a = Value.asInteger(arguments[0]);
            long b = Value.asInteger(arguments[1]);
            if (b == 0) return 1;
            if (b >= 0) {
                return (long) Math.pow(a, b);
            } else {
                return Math.pow(a, b);
            }
        } else {
            double a = toReal(arguments[0]);
            double b = toReal(arguments[1]);
            if (b == 0.0) return 1.0;
            return Math.pow(a, b);
        }
    }

    @Override
    public String toString() {
        return "#<primitive:expt>";
    }

}
