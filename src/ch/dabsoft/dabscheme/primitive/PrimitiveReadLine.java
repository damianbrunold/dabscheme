package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Globals;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.IOException;
import java.io.PushbackReader;

public class PrimitiveReadLine extends Primitive {

    private Globals globals;

    public PrimitiveReadLine(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length > 1) throw new IllegalStateException("read-line expected 0 or 1 arguments, but got " + arguments.length);
        PushbackReader port;
        if (arguments.length == 0) {
            port = Value.asInputPort(globals.resolve("*input-port*"));
        } else {
            port = Value.asInputPort(arguments[0]);
        }
        try {
            int c = port.read();
            if (c == -1) return Value.EOF;
            StringBuilder result = new StringBuilder();
            while (c != -1) {
                char ch = (char) c;
                result.append(ch);
                if (ch == '\n') break;
                c = port.read();
            }
            return result.toString().toCharArray();
        } catch (IOException e) {
            throw new SchemeError("read-line", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:read-line>";
    }

}
