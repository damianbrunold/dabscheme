package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PrimitiveOpenInputFile extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("open-input-file expected 1 argument, but got " + arguments.length);
        String filename = new String(Value.asString(arguments[0]));
        try {
            // TODO add support for specifying encoding!
            return new PushbackReader(
                    new InputStreamReader(
                            new FileInputStream(filename),
                            StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            throw new SchemeError("open-input-file", "file not found: ~s", filename);
        }
    }

    @Override
    public String toString() {
        return "#<primitive:open-input-file>";
    }

}
