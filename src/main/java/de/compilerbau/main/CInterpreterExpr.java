package de.compilerbau.main;

import de.compilerbau.main.parser.CExpr;

public class CInterpreterExpr extends CInterpreter {

    public class InterpreterCintExpr extends InterpreterCint {

        @Override
        public CInterpreter.Value eval(CExpr expr) {
            return switch (expr) {

                case CExpr.MulCExpr(var l, var r) -> {
                    int a = Value.asInt(eval(l));
                    int b = Value.asInt(eval(r));
                    yield new IntVal(a * b);
                }

                case CExpr.DivCExpr(var l, var r) -> {
                    int a = Value.asInt(eval(l));
                    int b = Value.asInt(eval(r));
                    yield new IntVal(a / b);
                }

                case CExpr.NegCExpr(var i) -> {
                    int a = Value.asInt(eval(i));
                    yield new IntVal(- a);
                }

                case CExpr.PosCExpr(var i) -> {
                    int a = Value.asInt(eval(i));
                    yield new IntVal(a);
                }

                default -> super.eval(expr);
            };
        }
    }
}
