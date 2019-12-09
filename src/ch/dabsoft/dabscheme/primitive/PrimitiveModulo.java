package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveModulo extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new SchemeError("modulo", "expected 2 arguments, but got " + arguments.length);
        if (allIntegers(arguments)) {
            long a = Value.asInteger(arguments[0]);
            long b = Value.asInteger(arguments[1]);
            long r = a % b;
            if ((r >= 0) != (b >= 0)) {
                return r + b;
            }
            return r;
        } else {
            double a = toReal(arguments[0]);
            double b = toReal(arguments[1]);
            double r = a % b;
            if ((r >= 0) != (b >= 0)) {
                return r + b;
            }
            return r;
        }
    }

    @Override
    public String toString() {
        return "#<primitive:modulo>";
    }

}
