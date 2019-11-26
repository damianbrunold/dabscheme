package ch.dabsoft.dabscheme.vm;

public class Token {

    public String value;
    public TokenType type;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return value + " (" + type + ")";
    }

}
