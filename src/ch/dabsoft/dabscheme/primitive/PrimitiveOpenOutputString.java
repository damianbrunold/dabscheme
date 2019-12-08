package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class PrimitiveOpenOutputString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 0) throw new IllegalStateException("open-output-string expected 0 arguments, but got " + arguments.length);
        return new StringWriter();
    }

    @Override
    public String toString() {
        return "#<primitive:open-output-string>";
    }

}
