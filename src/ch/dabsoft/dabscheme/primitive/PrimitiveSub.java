package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveSub extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (allIntegers(arguments)) {
            long result = Value.asInteger(arguments[0]);
            if (arguments.length == 1) {
                result = -result;
            } else {
                for (int i = 1; i < arguments.length; i++) result -= Value.asInteger(arguments[i]);
            }
            return result;
        } else {
            double result = toReal(arguments[0]);
            if (arguments.length == 1) {
                result = -result;
            } else {
                for (int i = 1; i < arguments.length; i++) result -= toReal(arguments[i]);
            }
            return result;
        }
    }

    @Override
    public String toString() {
        return "#<primitive:->";
    }

}
