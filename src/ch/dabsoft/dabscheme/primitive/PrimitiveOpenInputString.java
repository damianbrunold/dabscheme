package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PrimitiveOpenInputString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("open-input-string expected 1 argument, but got " + arguments.length);
        String text = new String(Value.asString(arguments[0]));
        return new PushbackReader(new StringReader(text));
    }

    @Override
    public String toString() {
        return "#<primitive:open-input-string>";
    }

}
