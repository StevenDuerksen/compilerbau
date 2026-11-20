package de.compilerbau.main.parser;

import de.compilerbau.main.token.CToken;
import de.compilerbau.main.token.CTokenKind;

import java.util.List;

public class CParser {

    public void parse(List<CToken> tokens) {

        for(CToken token : tokens) {
            switch (token.kind()) {
                case CTokenKind.INT -> {
                    return 
                }
                case CTokenKind.PLUS -> IO.println("Plus mit links " + add.left());
                case CExpr.SubCExpr sub    -> IO.println("Minus");
            }
        }

    }

}
