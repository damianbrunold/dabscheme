package ch.dabsoft.dabscheme.vm;

public class ReturnAddress {

    public int ip;
    public Lambda fn;
    public Pair env;

    public ReturnAddress(int ip, Lambda fn, Pair env) {
        this.ip = ip;
        this.fn = fn;
        this.env = env;
    }

    @Override
    public String toString() {
        return "#<RETADDR:" + ip + ">";
    }
}
