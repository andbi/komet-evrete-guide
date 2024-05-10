package org.evrete.komet;

import org.evrete.api.Knowledge;

public class EvreteKnowledgeImplementation implements KnowledgeImplementation {
    private final Knowledge delegate;

    public EvreteKnowledgeImplementation(Knowledge delegate) {
        this.delegate = delegate;
    }
}
