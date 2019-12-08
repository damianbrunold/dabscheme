package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.Writer;

public class PrimitiveNewline extends Primitive {

    private Globals globals;

    public PrimitiveNewline(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length > 1) {
            throw new IllegalStateException("newline expected 0 or 1 arguments, but got " + arguments.length);
        }
        Writer port;
        if (arguments.length == 0) {
            port = Value.asOutputPort(globals.resolve("*output-port*"));
        } else {
            port = Value.asOutputPort(arguments[1]);
        }
        try {
            port.write("\n");
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("newline", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:newline>";
    }

}
