package io.github.nomeyho.molcule.parser;

class Token {

    enum Type {
        GROUP_INITIATOR,
        GROUP_TERMINATOR,
        ATOM,
        NUMBER,
        TERMINATOR,
        UNKNOWN
    }

    private final Type type;
    private final String value;

    Token(final Type type) {
        this(type, null);
    }

    Token(final Type type, final String value) {
        this.type = type;
        this.value = value;
    }

    Type getType() {
        return type;
    }

    String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{ type='" + type + "', value='" + value + "' }";
    }
}
