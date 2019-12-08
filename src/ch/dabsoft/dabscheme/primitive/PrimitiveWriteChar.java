package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Writer;

public class PrimitiveWriteChar extends Primitive {

    private Globals globals;

    public PrimitiveWriteChar(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length == 0 || arguments.length > 2) {
            throw new IllegalStateException("write-char expected 1 or 2 arguments, but got " + arguments.length);
        }
        Writer port;
        if (arguments.length == 1) {
            port = Value.asOutputPort(globals.resolve("*output-port*"));
        } else {
            port = Value.asOutputPort(arguments[1]);
        }
        try {
            port.write(Value.asChar(arguments[0]));
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("write-char", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:write-char>";
    }

}
