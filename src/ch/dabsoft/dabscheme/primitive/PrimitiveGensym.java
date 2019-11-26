package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveGensym extends Primitive {

    private static int counter = 1;

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 0) throw new IllegalStateException("gensym expected 0 argument, but got " + arguments.length);
        // do not intern the symbol to ensure unique symbol!
        return "gensym-" + counter++;
    }

    @Override
    public String toString() {
        return "#<primitive:gensym>";
    }

}
