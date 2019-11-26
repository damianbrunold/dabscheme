package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitivePairP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("pair? expected 1 argument, but got " + arguments.length);
        return Value.isPair(arguments[0]) && arguments[0] != Value.NIL;
    }

    @Override
    public String toString() {
        return "#<primitive:pair?>";
    }

}
