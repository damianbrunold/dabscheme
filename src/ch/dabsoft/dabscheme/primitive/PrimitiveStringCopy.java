package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveStringCopy extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("string-copy expected 1 argument, but got " + arguments.length);
        char[] s = Value.asString(arguments[0]);
        return new String(s).toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:string-copy>";
    }

}
