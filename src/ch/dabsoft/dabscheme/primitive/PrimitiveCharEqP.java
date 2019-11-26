package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCharEqP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("char=? expected 2 arguments, but got " + arguments.length);
        Object obj1 = arguments[0];
        Object obj2 = arguments[1];
        return Value.asChar(obj1).charValue() == Value.asChar(obj2).charValue();
    }

    @Override
    public String toString() {
        return "#<primitive:char=?>";
    }

}
