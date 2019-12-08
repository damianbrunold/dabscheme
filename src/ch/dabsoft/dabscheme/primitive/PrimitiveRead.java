package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveRead extends Primitive {

    private Globals globals;

    public PrimitiveRead(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length > 1) throw new IllegalStateException("read expected 0 or 1 arguments, but got " + arguments.length);
        PushbackReader port;
        if (arguments.length == 0) {
            port = Value.asInputPort(globals.resolve("*input-port*"));
        } else {
            port = Value.asInputPort(arguments[0]);
        }
        try {
            return read(port);
        } catch (IOException e) {
            throw new SchemeError("read", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:read>";
    }

    public Token readToken(PushbackReader in) throws IOException {
        String whitespace = " \r\n\t";
        String delim = "()[]\"" + whitespace;
        int c = in.read();
        int state = 0;
        StringBuilder token = new StringBuilder();
        while (c != -1) {
            char ch = (char) c;
            switch (state) {
                case 0: // whitespace or single char token
                    if (ch == '(' || ch == '[') return new Token("(", TokenType.OPENPAR);
                    else if (ch == ')' || ch == ']') return new Token(")", TokenType.CLOSEPAR);
                    else if (ch == '\'') return new Token("'", TokenType.QUOTE);
                    else if (ch == '`') return new Token("`", TokenType.BACKQUOTE);
                    else if (ch == ',') {
                        state = 4; // unquote, unquote-splicing
                    } else if (ch == '.') {
                        state = 12; // either dot or real
                        token.append(ch);
                    } else if (ch == ';') {
                        state = 7; // comment
                    } else if (whitespace.indexOf(ch) != -1) {
                        break;
                    } else if (('0' <= ch && ch <= '9') || ch == '+' || ch == '-') {
                        state = 1; // number, +, -
                        token.append(ch);
                    } else if (ch == '\"') {
                        state = 2; // string
                    } else if (ch == '#') {
                        state = 5; // sharp stuff
                    } else {
                        state = 3; // symbol
                        token.append(ch);
                    }
                    break;

                case 1: // number, +,  -
                    if ('0' <= ch && ch <= '9') {
                        token.append(ch);
                    } else if (ch == '.') {
                        token.append(ch);
                        state = 11; // real
                    } else if (delim.indexOf(ch) != -1) {
                        in.unread(c);
                        if (token.toString().equals("+")) return new Token("+", TokenType.SYMBOL);
                        else if (token.toString().equals("-")) return new Token("-", TokenType.SYMBOL);
                        else return new Token(token.toString(), TokenType.INTEGER);
                    } else {
                        token.append(ch);
                        state = 3; // symbol
                    }
                    break;

                case 11: // real
                    if ('0' <= ch && ch <= '9') {
                        token.append(ch);
                    } else {
                        in.unread(c);
                        if (token.toString().equals("+.")) return new Token("+", TokenType.SYMBOL);
                        else if (token.toString().equals("-.")) return new Token("-", TokenType.SYMBOL);
                        else return new Token(token.toString(), TokenType.REAL);
                    }
                    break;

                case 12: // dot or real
                    if ('0' <= ch && ch <= '9') {
                        token.append(ch);
                        state = 11;
                    } else {
                        in.unread(c);
                        return new Token(".", TokenType.DOT);
                    }
                    break;

                case 2: // string
                    if (ch == '\\') {
                        state = 21; // string escape
                    } else if (ch == '\"') {
                        return new Token(token.toString(), TokenType.STRING);
                    } else {
                        token.append(ch);
                    }
                    break;

                case 21: // string escape
                    if (ch == '"' || ch == '\\') {
                        token.append(ch);
                    } else if (ch == 'n') {
                        token.append("\n");
                    } else if (ch == 'r') {
                        token.append("\r");
                    } else if (ch == 't') {
                        token.append("\t");
                    }
                    state = 2;
                    break;

                case 3: // symbol
                    if (delim.indexOf(ch) != -1) {
                        in.unread(c);
                        return new Token(token.toString(), TokenType.SYMBOL);
                    } else {
                        token.append(ch);
                    }
                    break;

                case 4: // unquote, unquote-splicing
                    if (ch == '@') {
                        return new Token(",@", TokenType.COMMAAT);
                    } else {
                        in.unread(c);
                        return new Token(",", TokenType.COMMA);
                    }

                case 5: // sharp stuff
                    if (ch == 't') return new Token("#t", TokenType.TRUE);
                    if (ch == 'f') return new Token("#f", TokenType.FALSE);
                    if (ch == '(') return new Token("#(", TokenType.SHARPOPENPAR);
                    if (ch == '\\') state = 6; // character
                    break;

                case 6: // character
                    if (delim.indexOf(ch) != -1) {
                        in.unread(c);
                        return asCharacterToken(token.toString());
                    } else {
                        token.append(ch);
                    }
                    break;

                case 7: // comment
                    if (ch == '\n') state = 0;
                    break;
            }
            c = in.read();
        }
        if (token.length() == 0) return null;
        if (state == 1) {
            if (token.toString().equals("+") || token.toString().equals("-")) return new Token(token.toString(), TokenType.SYMBOL);
            return new Token(token.toString(), TokenType.INTEGER);
        }
        if (state == 11) return new Token(token.toString(), TokenType.REAL);
        if (state == 2) throw new SchemeError("read", "String not closed");
        if (state == 3) return new Token(token.toString(), TokenType.SYMBOL);
        if (state == 6) return asCharacterToken(token.toString());
        throw new SchemeError("read", "Unexpected end of input");
    }

    private Token asCharacterToken(String result) {
        if (result.equals("newline")) {
            result = "\n";
        } else if (result.equals("return")) {
            result = "\r";
        } else if (result.equals("space")) {
            result = " ";
        } else if (result.equals("tab")) {
            result = "\t";
        } else if (result.matches("x[0-9a-fA-F]{4}")) {
            result = Character.toString((char) Integer.parseInt(result.substring(1), 16));
        }
        return new Token(result, TokenType.CHARACTER);
    }

    public Object read(PushbackReader in) throws IOException {
        return read(readToken(in), in);
    }

    public Object read(Token token, PushbackReader in) throws IOException {
        if (token == null) return null;
        if (token.type == TokenType.INTEGER) {
            return Long.parseLong(token.value);
        } else if (token.type == TokenType.REAL) {
            return Double.parseDouble(token.value);
        } else if (token.type == TokenType.STRING) {
            return token.value.toCharArray();
        } else if (token.type == TokenType.CHARACTER) {
            return token.value.charAt(0);
        } else if (token.type == TokenType.TRUE) {
            return Value.T;
        } else if (token.type == TokenType.FALSE) {
            return Value.F;
        } else if (token.type == TokenType.SYMBOL) {
            return Value.intern(token.value);
        } else if (token.type == TokenType.QUOTE) {
            return Pair.list(Value.intern("quote"), read(in));
        } else if (token.type == TokenType.BACKQUOTE) {
            return Pair.list(Value.intern("quasiquote"), read(in));
        } else if (token.type == TokenType.COMMA) {
            return Pair.list(Value.intern("unquote"), read(in));
        } else if (token.type == TokenType.COMMAAT) {
            return Pair.list(Value.intern("unquote-splicing"), read(in));
        } else if (token.type == TokenType.OPENPAR) {
            token = readToken(in);
            if (token.type == TokenType.CLOSEPAR) {
                return Value.NIL;
            } else {
                Pair result = new Pair(read(token, in), Value.NIL);
                Pair current = result;
                token = readToken(in);
                while (token.type != TokenType.CLOSEPAR && token.type != TokenType.DOT) {
                    Object value = read(token, in);
                    Pair next = new Pair(value, Value.NIL);
                    current.cdr = next;
                    current = next;
                    token = readToken(in);
                }
                if (token.type == TokenType.DOT) {
                    current.cdr = read(in);
                    token = readToken(in);
                }
                if (token.type != TokenType.CLOSEPAR) throw new SchemeError("read", "Expected )");
                return result;
            }
        } else if (token.type == TokenType.SHARPOPENPAR) {
            List<Object> vector = new ArrayList<>();
            token = readToken(in);
            while (token.type != TokenType.CLOSEPAR) {
                vector.add(read(token, in));
                token = readToken(in);
            }
            return vector;
        } else {
            throw new SchemeError("read", "Cannot handle token ~s", token);
        }
    }

}
