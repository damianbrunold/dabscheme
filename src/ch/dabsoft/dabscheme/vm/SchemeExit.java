package ch.dabsoft.dabscheme.vm;

public class SchemeExit extends RuntimeException {

    public SchemeExit() {
        // empty
    }

    @Override
    public String toString() {
        return "Exit";
    }

}
