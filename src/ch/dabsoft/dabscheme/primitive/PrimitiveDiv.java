package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveDiv extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length == 1) {
            double value = toReal(arguments[0]);
            if (value == 0.0) throw new SchemeError("/", "Division by ~s", value);
            return 1.0 / value;
        }
        double result = toReal(arguments[0]);
        for (int i = 1; i < arguments.length; i++) {
            double value = toReal(arguments[i]);
            if (value == 0.0) throw new SchemeError("/", "Division by ~s", value);
            result /= value;
        }
        if (result == (double) ((long) result) && allIntegers(arguments)) return (long) result;
        return result;
    }

    @Override
    public String toString() {
        return "#<primitive:/>";
    }

}
