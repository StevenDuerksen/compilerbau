package de.compilerbau.main;

import de.compilerbau.main.token.CToken;
import de.compilerbau.main.token.CTokenKind;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.misc.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

public class CLexer {

    private CodePointCharStream stream;

    public CLexer(CodePointCharStream stream) {
        this.stream = stream;
    }

    public List<CToken> scan() {
        List<CToken> tokens = new ArrayList<>();

        do tokens.add(nextToken());
        while (tokens.getLast().kind() != CTokenKind.EOF);

        return tokens;
    }

    protected CToken nextToken() {
        skipIrrelevant();

        if (stream.LA(1) == IntStream.EOF) {
            return CToken.EOF;
        }

        if (Character.isDigit(stream.LA(1))) {
            return new CToken(CTokenKind.INT, readWhile(Character::isDigit));
        }

        String s = Character.toString(stream.LA(1));
        for (CTokenKind kind : CTokenKind.values()) {
            if (s.equals(kind.symbol)) {
                stream.consume();
                return new CToken(kind, s);
            }
        }

        throw new RuntimeException("unexpected character '" + s + "'");
    }

    private boolean lookingAt(String s) {
        int i = stream.index();
        return stream.getText(Interval.of(i, i + s.length() - 1)).equals(s);
    }

    protected void skipIrrelevant() {
        while (true) {
            readWhile(Character::isWhitespace);

            if (lookingAt("//")) {
                readWhile(c -> c != '\n');
            } else if (lookingAt("/*")) {
                stream.consume();
                stream.consume();
                while (!lookingAt("*/")) {
                    if (stream.LA(1) == IntStream.EOF) {
                        throw new RuntimeException("unterminated comment");
                    }
                    stream.consume();
                }
                stream.consume();
                stream.consume();
            } else {
                return;
            }
        }
    }

    protected String readWhile(IntPredicate pred) {
        int start = stream.index();
        while (stream.LA(1) != IntStream.EOF && pred.test(stream.LA(1))) {
            stream.consume();
        }
        return stream.getText(Interval.of(start, stream.index() - 1));
    }


}
