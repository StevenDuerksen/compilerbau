package de.compilerbau.main.parser;

/**

 expr := term (OP term)*
 term := INT | '(' expr ')'
 OP   := '+' | '-'
 INT  := [0-9]+

 */
public sealed interface CExpr {
    record CIntLiteral(int value) implements CExpr {}
    record AddCExpr(CExpr left, CExpr right) implements CExpr {}
    record SubCExpr(CExpr left, CExpr right) implements CExpr {}
    record MulCExpr(CExpr left, CExpr right) implements CExpr {}
    record DivCExpr(CExpr left, CExpr right) implements CExpr {}
    record NegCExpr(CExpr expr) implements CExpr {}
    record PosCExpr(CExpr expr) implements CExpr {}
}
