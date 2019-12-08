package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveMakeString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (0 == arguments.length || arguments.length > 2) throw new IllegalStateException("make-string expected 1 or 2 arguments, but got " + arguments.length);
        int n = (int) (long) Value.asInteger(arguments[0]);
        char ch = ' ';
        if (arguments.length == 2) ch = Value.asChar(arguments[1]);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(ch);
        }
        return result.toString().toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:make-string>";
    }

}
