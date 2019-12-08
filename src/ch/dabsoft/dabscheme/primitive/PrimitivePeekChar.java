package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Globals;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.IOException;
import java.io.PushbackReader;

public class PrimitivePeekChar extends Primitive {

    private Globals globals;

    public PrimitivePeekChar(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length > 1) throw new IllegalStateException("peek-char expected 0 or 1 arguments, but got " + arguments.length);
        PushbackReader port;
        if (arguments.length == 0) {
            port = Value.asInputPort(globals.resolve("*input-port*"));
        } else {
            port = Value.asInputPort(arguments[0]);
        }
        try {
            int ch = port.read();
            port.unread(ch);
            if (ch == -1) return Value.EOF;
            return (char) ch;
        } catch (IOException e) {
            throw new SchemeError("peek-char", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:read-char>";
    }

}
