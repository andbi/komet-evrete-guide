package org.evrete.komet.benchmark;

import dev.ikm.tinkar.common.sets.ConcurrentHashSet;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Set;

public class CloneRuleProcessor extends AbstractProcessor {
    private final Set<String> processedFiles = new ConcurrentHashSet<>();

    @Override
    public final Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                CloneableRuleClass.class.getCanonicalName()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_21;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processedFiles.clear();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                processRuleClass(element.getEnclosingElement().toString(), element.getSimpleName().toString());
            }
        }
        return true;
    }


    void processRuleClass(String packageName, String className) {
        // Exit if already processed
        if (processedFiles.contains(packageName + "." + className)) {
            return;
        }

        try {
            var filer = processingEnv.getFiler();
            FileObject javaFileObject = filer.getResource(StandardLocation.SOURCE_PATH, packageName, className + ".java");
            String sourceCode = javaFileObject.getCharContent(true).toString();
            var copies = RulesetCopy.copy(sourceCode, className);

            for(var copy : copies) {
                String fqName = packageName + "." + copy.className();
                var fileObject = filer.createSourceFile(fqName);
                try (PrintWriter out = new PrintWriter(fileObject.openWriter())) {
                    out.print(copy.source());
                    this.processedFiles.add(fqName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UncheckedIOException(e);
        }
    }
}
