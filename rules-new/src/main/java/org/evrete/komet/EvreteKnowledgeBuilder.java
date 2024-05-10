package org.evrete.komet;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.dsl.Constants;
import org.evrete.komet.ruleset.AxiomFocusedRules;
import org.evrete.komet.ruleset.ComponentFocusRules;
import org.evrete.komet.ruleset.NewConceptRules;
import org.evrete.komet.ruleset.NewPatternRules;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;

public class EvreteKnowledgeBuilder implements KnowledgeBuilder {
    @Override
    public KnowledgeImplementation build(Collection<Class<?>> sources) {
        try {
            KnowledgeService service = new KnowledgeService();
            Knowledge knowledge = service.newKnowledge(Constants.PROVIDER_JAVA_CLASS, sources.toArray(new Class<?>[0]));
            return new EvreteKnowledgeImplementation(knowledge);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Collection<String> unitClassNames() {
        return List.of(
                AxiomFocusedRules.class.getName(),
                ComponentFocusRules.class.getName(),
                NewConceptRules.class.getName(),
                NewPatternRules.class.getName()
        );
    }
}
