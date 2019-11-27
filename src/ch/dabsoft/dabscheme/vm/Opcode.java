package ch.dabsoft.dabscheme.vm;

public enum Opcode {

    LVAR,
    LSET,
    GVAR,
    GSET,
    POP,
    CONST,
    JUMP,
    FJUMP,
    TJUMP,
    SAVE,
    RETURN,
    CALLJ,
    ARGS,
    ARGSDOT,
    ARGMV,
    FN,
    SETCC,
    CC,
    FLATTEN_APPLY,
    FLATTEN_MULTVALS,

}
