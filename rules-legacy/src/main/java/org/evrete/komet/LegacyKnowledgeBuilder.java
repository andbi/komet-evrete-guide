package org.evrete.komet;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.komet.ruleset.legacy.AxiomFocusedRules;
import org.evrete.komet.ruleset.legacy.ComponentFocusRules;
import org.evrete.komet.ruleset.legacy.NewConceptRules;
import org.evrete.komet.ruleset.legacy.NewPatternRules;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;

public class LegacyKnowledgeBuilder implements KnowledgeBuilder {
    @Override
    public KnowledgeImplementation build(Collection<Class<?>> sources) {
        try {
            KnowledgeService service = new KnowledgeService();
            Knowledge knowledge = service.newKnowledge("JAVA-CLASS", sources.toArray(new Class<?>[0]));
            return new LegacyKnowledgeImplementation(knowledge);
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
