package io.github.nomeyho.molcule.parser;

import java.util.Optional;

class FormulaLexer {

    private final String formula;
    private final int formulaLength;
    private int i;

    FormulaLexer(final String formula) {
        this.formula = formula;
        this.formulaLength = formula != null ? formula.length() : 0;
        this.i = 0;
    }

    Token nextToken() {
        if(this.formula == null || this.i >= this.formulaLength) {
            return new Token(Token.Type.TERMINATOR);
        }

        final char c = this.formula.charAt(this.i);
        this.i++;

        if(isLetter(c)) {
            return this.readElement(c)
                    .map(e -> new Token(Token.Type.ATOM, e))
                    .orElse(new Token(Token.Type.UNKNOWN, String.valueOf(c)));
        } else if(isDigit(c)) {
            return new Token(Token.Type.NUMBER, this.readAllDigits(c));
        } else if(c == '(') {
            return new Token(Token.Type.GROUP_INITIATOR);
        } else if(c == ')') {
            return new Token(Token.Type.GROUP_TERMINATOR);
        }

        return new Token(Token.Type.UNKNOWN, String.valueOf(c));
    }

    private static boolean isLetter(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(final char c) {
        return (c >= '0') && (c <= '9');
    }

    private static boolean isElement(final String s) {
        return Element.from(s).isPresent();
    }

    private String readAllDigits(final char digit) {
        final StringBuilder builder = new StringBuilder(String.valueOf(digit));

        while(this.i < this.formulaLength) {
            final char c = this.formula.charAt(i);

            if(isDigit(c)) {
                builder.append(c);
                this.i++;
            } else {
                break;
            }
        }

        return builder.toString();
    }

    private Optional<String> readElement(final char c) {
        // single-character element
        final String s1 = String.valueOf(c);

        if(this.i >= this.formulaLength) {
            if(isElement(s1)) {
                return Optional.of(s1);
            } else {
                return Optional.empty();
            }
        }

        // two-character element
        final String s2 = s1 + this.formula.charAt(this.i);

        if(isElement(s2)) {
            this.i++;
            return Optional.of(s2);
        } else if(isElement(s1)) {
            return Optional.of(s1);  // first char might still be an element
        } else {
            return Optional.empty();
        }
    }
}
