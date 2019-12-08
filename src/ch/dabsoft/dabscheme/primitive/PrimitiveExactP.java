package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveExactP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("exact? expected 1 argument, but got " + arguments.length);
        return Value.isInteger(arguments[0]);
    }

    @Override
    public String toString() {
        return "#<primitive:exact?>";
    }

}
