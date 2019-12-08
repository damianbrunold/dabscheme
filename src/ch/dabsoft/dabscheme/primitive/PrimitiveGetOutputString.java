package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.StringWriter;

public class PrimitiveGetOutputString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("get-output-string expected 1 argument, but got " + arguments.length);
        StringWriter writer = (StringWriter) Value.asOutputPort(arguments[0]);
        return writer.toString().toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:get-output-string>";
    }

}
