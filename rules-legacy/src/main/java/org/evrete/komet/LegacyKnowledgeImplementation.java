package org.evrete.komet;

import org.evrete.api.Knowledge;

public class LegacyKnowledgeImplementation implements KnowledgeImplementation {
    private final Knowledge delegate;

    public LegacyKnowledgeImplementation(Knowledge delegate) {
        this.delegate = delegate;
    }
}
