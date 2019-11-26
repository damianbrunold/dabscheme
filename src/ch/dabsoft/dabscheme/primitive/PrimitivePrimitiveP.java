package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitivePrimitiveP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("primitive? expected 1 argument, but got " + arguments.length);
        return Value.isPrimitive(arguments[0]);
    }

    @Override
    public String toString() {
        return "#<primitive:primitive?>";
    }

}
