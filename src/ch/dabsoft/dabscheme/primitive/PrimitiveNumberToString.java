package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveNumberToString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1 && arguments.length != 2) throw new IllegalStateException("number->string expected 1 or 2 arguments, but got " + arguments.length);
        int base = 10;
        if (arguments.length == 2) base = Value.asInteger(arguments[1]).intValue();
        if (Value.isReal(arguments[0])) return Double.toString(Value.asReal(arguments[0])).toCharArray();
        else return Long.toString(Value.asInteger(arguments[0]), base).toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:number->string>";
    }

}
