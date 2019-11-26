package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveRealP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("real? expected 1 argument, but got " + arguments.length);
        return Value.isReal(arguments[0]) || Value.isInteger(arguments[0]);
    }

    @Override
    public String toString() {
        return "#<primitive:real?>";
    }

}
