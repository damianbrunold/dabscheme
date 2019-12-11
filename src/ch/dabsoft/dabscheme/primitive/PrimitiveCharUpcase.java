package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCharUpcase extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("char-upcase expected 1 argument, but got " + arguments.length);
        return Character.toUpperCase(Value.asChar(arguments[0]));
    }

    @Override
    public String toString() {
        return "#<primitive:char-upcase>";
    }

}
