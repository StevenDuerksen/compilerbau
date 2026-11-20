package de.compilerbau.main.token;

public enum CTokenKind {
    PLUS("+"),
    MINUS("-"),
    LPAREN("("),
    RPAREN(")"),
    SEMI(";"),
    INT,
    EOF;

    /** fester Text des Tokens, null wenn der Lexer ihn selbst bestimmt (z.B. INT) */
    public final String symbol;

    CTokenKind() {
        this(null);
    }

    CTokenKind(String symbol) {
        this.symbol = symbol;
    }
}
