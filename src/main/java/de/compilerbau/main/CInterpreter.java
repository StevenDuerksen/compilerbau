package de.compilerbau.main;

import de.compilerbau.main.parser.CExpr;

public class CInterpreter {

    public interface Value {
        static int asInt(Value v) {
            if (v instanceof IntVal(int val)) return val;
            throw new RuntimeException("number expected");
        }
    }
    public record IntVal(int v) implements Value {}


    public class InterpreterCint {
        public Value eval(CExpr expr) {
            return switch (expr) {
                case CExpr.CIntLiteral(var v) -> new IntVal(v);

                case CExpr.AddCExpr(var l, var r) -> {
                    int a = Value.asInt(eval(l));
                    int b = Value.asInt(eval(r));
                    yield new IntVal(a + b);
                }

                case CExpr.SubCExpr(var l, var r) -> {
                    int a = Value.asInt(eval(l));
                    int b = Value.asInt(eval(r));
                    yield new IntVal(a - b);
                }

                default -> throw new RuntimeException("unexpected expression: " + expr);
            };
        }
    }
}
