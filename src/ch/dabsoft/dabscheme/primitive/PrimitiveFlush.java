package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.Writer;

public class PrimitiveFlush extends Primitive {

    private Globals globals;

    public PrimitiveFlush(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length > 1) {
            throw new IllegalStateException("flush expected 0 or 1 arguments, but got " + arguments.length);
        }
        Writer port;
        if (arguments.length == 0) {
            port = Value.asOutputPort(globals.resolve("*output-port*"));
        } else {
            port = Value.asOutputPort(arguments[0]);
        }
        try {
            port.flush();
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("flush", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:write>";
    }

}
