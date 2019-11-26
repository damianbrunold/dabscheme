package ch.dabsoft.dabscheme.vm;

public class SchemeError extends RuntimeException {

    private String origin;
    private String message;

    public SchemeError(String origin, String message, Object... values) {
        this.origin = origin;
        Object[] strings = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strings[i] = Value.printRep(values[i]);
        }
        this.message = String.format(message.replace("~s", "%s"), strings);
    }

    @Override
    public String toString() {
        return "Error in " + origin + ": " + message;
    }

}
