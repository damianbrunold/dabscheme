package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveStringSetB extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 3) throw new IllegalStateException("string-set! expected 2 arguments, but got " + arguments.length);
        char[] s = Value.asString(arguments[0]);
        s[(int) (long) arguments[1]] = Value.asChar(arguments[2]);
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:string-set!>";
    }

}
