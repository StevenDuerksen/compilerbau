package de.compilerbau.main.parser;

import de.compilerbau.main.token.CToken;
import de.compilerbau.main.token.CTokenKind;

import java.util.Arrays;
import java.util.List;

/**

 parse := expr EOF
 expr := term (OP term)*
 term := INT | '(' expr ')'
 OP   := '+' | '-'
 INT  := [0-9]+

 ----
 Erweiterung für Multiplikation, Division und unäres Minus:

 expr  := term (('+' | '-') term)*
 term  := atom (('*' | '/') atom)*
 atom  := '-' atom | INT | '(' expr ')' | '+' atom

 */

public class CParser {

    private List<CToken> tokens;
    private int index = 0;

    public CExpr parse(List<CToken> tokens) {

        this.tokens = tokens;
        this.index = 0;

        CExpr result = expr();
        match(CTokenKind.EOF);

        return result;
    }

    private CExpr term(){
        CExpr left = atom();

        while (check(CTokenKind.STAR, CTokenKind.SLASH)) {
            CToken operator = advance();
            CExpr right = atom();

            if(operator.kind() == CTokenKind.STAR){
                left = new CExpr.MulCExpr(left, right);
            }

            if(operator.kind() == CTokenKind.SLASH){
                left = new CExpr.DivCExpr(left, right);
            }
        }

        return left;
    }

    private CExpr atom(){
        return switch (peek().kind()){
            case LPAREN -> {
                match(CTokenKind.LPAREN);
                CExpr inner = expr();
                match(CTokenKind.RPAREN);
                yield inner;
            }

            case INT -> {
                CToken number = advance();
                yield new CExpr.CIntLiteral(Integer.parseInt(number.lexeme()));
            }

            case MINUS -> {
                match(CTokenKind.MINUS);
                CExpr atom = atom();
                yield new CExpr.NegCExpr(atom);
            }

            case PLUS -> {
                match(CTokenKind.PLUS);
                CExpr atom = atom();
                yield new CExpr.PosCExpr(atom);
            }

            default -> throw new RuntimeException("unexpected token: " + peek());
        };
    }

    private CExpr expr(){
        CExpr left = term();

        while (check(CTokenKind.PLUS, CTokenKind.MINUS)) {
            CToken operator = advance();
            CExpr right = term();

            if(operator.kind() == CTokenKind.PLUS){
                left = new CExpr.AddCExpr(left, right);
            }

            if(operator.kind() == CTokenKind.MINUS){
                left = new CExpr.SubCExpr(left, right);
            }
        }

        return left;
    }

    protected boolean isAtEnd() { return peek().kind() == CTokenKind.EOF; }

    protected CToken peek() { return peek(0); }
    protected CToken peek(int la) {
        if (la < 0 || index + la >= tokens.size()) return tokens.getLast();  // EOF
        return tokens.get(index + la);
    }

    protected CToken advance() {
        return isAtEnd() ? tokens.getLast() : tokens.get(index++);
    }

    protected boolean check(CTokenKind... expected) {
        return Arrays.stream(expected).anyMatch(e -> e == peek().kind());
    }
    protected CToken match(CTokenKind... expected) {
        if (check(expected)) return advance();
        throw new RuntimeException("unexpected token: " + peek());
    }



}
