package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveError extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length < 2) throw new IllegalStateException("error expected 2 arguments or more, but got " + arguments.length);
        String origin = Value.asSymbol(arguments[0]);
        String message = new String(Value.asString(arguments[1]));
        List<Object> values = new ArrayList<>();
        for (int i = 2; i < arguments.length; i++) values.add(arguments[i]);
        return new SchemeError(origin, message, values.toArray());
    }

    @Override
    public String toString() {
        return "#<primitive:error>";
    }

}
