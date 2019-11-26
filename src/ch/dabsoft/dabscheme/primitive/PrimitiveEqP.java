package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveEqP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("eq? expected 2 arguments, but got " + arguments.length);
        Object obj1 = arguments[0];
        Object obj2 = arguments[1];
        return obj1 == obj2;
    }

    @Override
    public String toString() {
        return "#<primitive:eq?>";
    }

}
