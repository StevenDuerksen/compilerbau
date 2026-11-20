package de.compilerbau.main.token;

public record CToken(CTokenKind kind, String lexeme) {

    public static CToken EOF = new CToken(CTokenKind.EOF, null);

    @Override
    public String toString() {
        if (lexeme == null) {
            return kind.name();
        }
        return String.format("%-8s '%s'", kind, lexeme);
    }
}
