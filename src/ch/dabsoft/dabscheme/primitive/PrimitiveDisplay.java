package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.Writer;

public class PrimitiveDisplay extends Primitive {

    private Globals globals;

    public PrimitiveDisplay(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length == 0 || arguments.length > 2) {
            throw new IllegalStateException("display expected 1 or 2 arguments, but got " + arguments.length);
        }
        Writer port;
        if (arguments.length == 1) {
            port = Value.asOutputPort(globals.resolve("*output-port*"));
        } else {
            port = Value.asOutputPort(arguments[1]);
        }
        try {
            port.write(Value.displayRep(arguments[0]));
            port.flush();
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("display", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:display>";
    }

}
