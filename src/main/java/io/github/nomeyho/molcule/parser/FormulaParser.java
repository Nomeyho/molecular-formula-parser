package io.github.nomeyho.molcule.parser;

import java.util.Map;
import java.util.Stack;

public class FormulaParser {

    private final Stack<StackItem> stack = new Stack<>();
    private final String formula;

    public FormulaParser(final String formula) {
        this.formula = formula;
    }

    public Map<Element, Integer> parse() throws FormulaParsingException {
        this.initStack();
        this.readTokens();

        final Map<Element, Integer> atoms = this.processStack();
        if(atoms.isEmpty()) {
            throw new FormulaParsingException("Empty formula");
        }

        return atoms;
    }

    private void initStack() {
        this.stack.clear();
        this.stack.push(new StackItem(StackItem.Type.BOTTOM));
    }

    private void readTokens() throws FormulaParsingException {
        final FormulaLexer lexer = new FormulaLexer(this.formula);

        for(Token token = lexer.nextToken(); token.getType() != Token.Type.TERMINATOR; token = lexer.nextToken()) {
            switch (token.getType()) {
                case ATOM:
                    this.handleAtom(token);
                    break;
                case NUMBER:
                    this.handleNumber(token);
                    break;
                case GROUP_INITIATOR:
                    this.handleGroupInitiator();
                    break;
                case GROUP_TERMINATOR:
                    this.handleGroupTerminator();
                    break;
                default:
                    throw new FormulaParsingException("Invalid element: '" + token.getValue() + "'");
            }
        }
    }

    private void handleAtom(final Token token) {
        final Element element = Element.valueOf(token.getValue());
        final StackItem item = new StackItem(StackItem.Type.DATA);

        item.add(element);
        this.stack.push(item);
    }

    private void handleNumber(final Token token) throws FormulaParsingException {
        final StackItem item = this.stack.pop();

        if(item.getType() != StackItem.Type.DATA) {
            throw new FormulaParsingException("A number should be preceded by an atom or a group");
        }

        item.multiply(Integer.valueOf(token.getValue()));
        this.stack.push(item);
    }

    private void handleGroupInitiator() {
        final StackItem item = new StackItem(StackItem.Type.SEPARATOR);
        this.stack.push(item);
    }

    private void handleGroupTerminator() throws FormulaParsingException {
        final StackItem item = new StackItem(StackItem.Type.DATA);

        while(stack.peek().getType() == StackItem.Type.DATA) {
            item.merge(stack.pop());
        }

        // next item must be a SEPARATOR
        if(stack.pop().getType() == StackItem.Type.BOTTOM) {
            throw new FormulaParsingException("Mismatched right parenthesis");
        } else if(item.getAtoms().isEmpty()) {
            throw new FormulaParsingException("Empty group");
        }

        this.stack.push(item);
    }

    private Map<Element, Integer> processStack() throws FormulaParsingException {
        final StackItem result = new StackItem(StackItem.Type.DATA);

        while(stack.peek().getType() != StackItem.Type.BOTTOM) {
            final StackItem item = stack.pop();

            if(item.getType() == StackItem.Type.DATA) {
                result.merge(item);
            } else if(item.getType() == StackItem.Type.SEPARATOR) {
                throw new FormulaParsingException("Mismatched left parenthesis");
            } else {
                throw new FormulaParsingException("Unexpected error");
            }
        }

        return result.getAtoms();
    }
}
