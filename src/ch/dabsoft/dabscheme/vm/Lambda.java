package ch.dabsoft.dabscheme.vm;

import java.util.List;

public class Lambda {
    public String name;
    public Pair env;
    public List<Instruction> code;

    public Lambda(Pair env, List<Instruction> code) {
        this.env = env;
        this.code = code;
    }

    @Override
    public String toString() {
        return "#<" + (name == null ? "lambda" : name ) + ">";
    }
}
