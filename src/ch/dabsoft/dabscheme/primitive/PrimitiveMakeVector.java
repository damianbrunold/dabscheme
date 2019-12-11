package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveMakeVector extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (0 == arguments.length || arguments.length > 2) throw new IllegalStateException("make-vector expected 1 or 2 arguments, but got " + arguments.length);
        int n = (int) (long) Value.asInteger(arguments[0]);
        Object obj = 0;
        if (arguments.length == 2) obj = arguments[1];

        List<Object> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(obj);
        }
        return result;
    }

    @Override
    public String toString() {
        return "#<primitive:make-vector>";
    }

}
