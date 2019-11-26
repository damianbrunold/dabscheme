package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveStringRef extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("string-ref expected 2 arguments, but got " + arguments.length);
        return Value.asString(arguments[0])[(int) (long) arguments[1]];
    }

    @Override
    public String toString() {
        return "#<primitive:string-ref>";
    }

}
