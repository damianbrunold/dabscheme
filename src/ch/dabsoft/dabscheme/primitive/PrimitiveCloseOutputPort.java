package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

import java.io.IOException;

public class PrimitiveCloseOutputPort extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("close-output-port expected 1 argument, but got " + arguments.length);
        try {
            Value.asOutputPort(arguments[0]).close();
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("close-output-port", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:close-output-port>";
    }

}
