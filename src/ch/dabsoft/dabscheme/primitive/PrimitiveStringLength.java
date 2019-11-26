package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveStringLength extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("string-length expected 1 argument, but got " + arguments.length);
        return (long) Value.asString(arguments[0]).length;
    }

    @Override
    public String toString() {
        return "#<primitive:string-length>";
    }

}
