package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PrimitiveCloseInputPort extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("close-input-port expected 1 argument, but got " + arguments.length);
        try {
            Value.asInputPort(arguments[0]).close();
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("close-input-port", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:close-input-port>";
    }

}
