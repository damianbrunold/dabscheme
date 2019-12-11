package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCharLowerCaseP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("char-lower-case? expected 1 argument, but got " + arguments.length);
        return Character.isLowerCase(Value.asChar(arguments[0]));
    }

    @Override
    public String toString() {
        return "#<primitive:char-lower-case?>";
    }

}
