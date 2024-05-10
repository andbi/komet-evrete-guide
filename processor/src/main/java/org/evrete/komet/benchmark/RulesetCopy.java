package org.evrete.komet.benchmark;

public record RulesetCopy(String source, String className) {
    public static final int COPIES = 64;

    public static RulesetCopy[] copy(String javaSource, String simpleName) {
        var copies = new RulesetCopy[COPIES];
        for (int i = 0; i < COPIES; i++) {
            copies[i] = copy(javaSource, simpleName, i);
        }
        return copies;
    }

    private static RulesetCopy copy(String javaSource, String simpleName, int copyId) {
        String newClassName = simpleName + copyId;

        // 1. Replace class declaration
        String classNameRegex = "(class\\s+)(\\w+)(\\s+)(.*\\{)";
        String classNameReplacement = "$1" + newClassName + "$3$4";
        String newJavaSource = javaSource.replaceFirst(classNameRegex, classNameReplacement);

        // 2. Replace @RuleSet declarations
        String rulesetRegex = "@RuleSet\\(.*\"(.*?)\"\\)";
        String rulesetReplacement = "@RuleSet(\"$1 (v" + copyId + ")\")";
        newJavaSource = newJavaSource.replaceFirst(rulesetRegex, rulesetReplacement);

        // 3. Replace @Rule declarations
        String ruleRegex = "@Rule\\(.*\"(.*?)\"\\)";
        String ruleReplacement = "@Rule(\"$1 (v" + copyId + ")\")";
        newJavaSource = newJavaSource.replaceAll(ruleRegex, ruleReplacement);


        return new RulesetCopy(newJavaSource, newClassName);
    }
}


// "@RuleSet(\"$1" + numberToAppend + "\")"
