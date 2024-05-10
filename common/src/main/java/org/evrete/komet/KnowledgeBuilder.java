package org.evrete.komet;

import java.util.Collection;

/**
 * Provides an abstraction for building knowledge implementations to be used in benchmarks.
 */
public interface KnowledgeBuilder {

    KnowledgeImplementation build(Collection<Class<?>> sources);

    /**
     * Returns a collection of reference class names. These names represent classes that will be cloned by the
     * Java Annotation Processor and used within benchmarks.
     *
     * @return Collection of class names as Strings.
     */
    Collection<String> unitClassNames();}
