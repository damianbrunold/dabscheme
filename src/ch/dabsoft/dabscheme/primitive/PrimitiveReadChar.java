package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.PushbackReader;

public class PrimitiveReadChar extends Primitive {

    private Globals globals;

    public PrimitiveReadChar(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length > 1) throw new IllegalStateException("read-char expected 0 or 1 arguments, but got " + arguments.length);
        PushbackReader port;
        if (arguments.length == 0) {
            port = Value.asInputPort(globals.resolve("*input-port*"));
        } else {
            port = Value.asInputPort(arguments[0]);
        }
        try {
            int ch = port.read();
            if (ch == -1) return Value.EOF;
            return (char) ch;
        } catch (IOException e) {
            throw new SchemeError("read-char", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:read-char>";
    }

}
