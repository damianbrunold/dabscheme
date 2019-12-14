package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class PrimitiveDisassemble extends Primitive {

    private Globals globals;

    public PrimitiveDisassemble(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1 && arguments.length != 2) throw new IllegalStateException("disassemble expected 1 or 2 arguments, but got " + arguments.length);
        Lambda fn = Value.asLambda(arguments[0]);
        Writer port;
        if (arguments.length == 1) {
            port = Value.asOutputPort(globals.resolve("*output-port*"));
        } else {
            port = Value.asOutputPort(arguments[1]);
        }
        try {
            port.write(fn.name + "\n");
            port.write("env: " + Value.printRep(fn.env) + "\n");
            writeCode(port, fn.code, "");
            port.flush();
            return new Values();
        } catch (IOException e) {
            throw new SchemeError("disassemble", "i/o error");
        }
    }

    private void writeCode(Writer port, List<Instruction> code, String indent) throws IOException {
        int index = 0;
        for (Instruction instruction : code) {
            if (instruction.opcode == Opcode.FN) {
                port.write(String.format("%s%-3s %s\n", indent, index + ":", instruction.opcode.toString()));
                port.write(indent + "    env: " + Value.asPair(instruction.arg1).second() + "\n");
                port.write(indent + "    args: " + Value.asPair(instruction.arg1).fourth() + "\n");
                writeCode(port, (List<Instruction>) Value.asPair(instruction.arg1).sixth(), indent + "    ");
            } else {
                port.write(String.format("%s%-3s %s\n", indent, index + ":", Value.displayRep(instruction)));
            }
            index++;
        }
    }

    @Override
    public String toString() {
        return "#<primitive:disassemble>";
    }

}
