package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Encoding;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrimitiveOpenInputFile extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1 && arguments.length != 2) throw new IllegalStateException("open-input-file expected 1 or 2 arguments, but got " + arguments.length);
        String filename = new String(Value.asString(arguments[0]));
        try {
            Charset encoding = StandardCharsets.UTF_8;
            if (arguments.length == 2) {
                encoding = Encoding.getEncoding(Value.asSymbol(arguments[1]));
            }
            return new PushbackReader(
                    new InputStreamReader(
                            new FileInputStream(filename),
                            encoding));
        } catch (FileNotFoundException e) {
            throw new SchemeError("open-input-file", "file not found: ~s", filename);
        }
    }

    @Override
    public String toString() {
        return "#<primitive:open-input-file>";
    }

}
