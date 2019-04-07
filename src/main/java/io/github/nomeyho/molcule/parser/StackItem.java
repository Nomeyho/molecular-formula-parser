package io.github.nomeyho.molcule.parser;

import java.util.HashMap;
import java.util.Map;

class StackItem {

    enum Type {
        DATA,
        SEPARATOR,
        BOTTOM
    }

    private Type type;
    private Map<Element, Integer> atoms;

    StackItem(final Type type) {
        this.type = type;
        this.atoms = new HashMap<>();
    }

    Type getType() {
        return type;
    }

    Map<Element, Integer> getAtoms() {
        return this.atoms;
    }

    void add(final Element element) {
        this.add(element, 1);
    }

    void add(final Element element, final int amount) {
        final int count = this.atoms.getOrDefault(element, 0);
        this.atoms.put(element, count + amount);
    }

    void multiply(final int factor) {
        this.atoms.forEach((key, value) -> this.atoms.put(key, value * factor));
    }

    void merge(final StackItem item) {
        item.getAtoms().forEach(this::add);
    }
}
