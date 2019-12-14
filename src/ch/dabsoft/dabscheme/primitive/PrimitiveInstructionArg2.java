package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Instruction;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveInstructionArg2 extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("instruction-arg2 expected 1 argument, but got " + arguments.length);
        Instruction instruction = Value.asInstruction(arguments[0]);
        if (instruction.arg2 == null) return new Values();
        return instruction.arg2;
    }

    @Override
    public String toString() {
        return "#<primitive:instruction-arg2>";
    }

}
