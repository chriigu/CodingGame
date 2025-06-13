package org.example.cg.core.input.enums;

public enum InputParamIdentifierEnum {

    INPUT_SOURCE("i", """
            -i FILE | URL | -
            • input, where FILE is a local file, URL an http/s URL, "-" stdin (default)
            """, true),
    OUTPUT_DESTINATION("o", """
            -o FILE | URL | -
            • output, where FILE is a local file, URL an http/s URL, "-" stdin (default). Existing
            output is overwritten.
            """, true),
    INPUT_FORMAT("f", """
            -f csv | json
            • input format csv (default) or a json array
            """, true),
    OUTPUT_FORMAT("F", """
            -F csv | json
            • output format csv (default) or json
            """, true),
    ACTION("a", """
            -a sum | minMax | LT4
            • action
            • sum: prints the sum of the input values
            • minMax: prints the min and the max of the input values
            • lt4: prints the input values less than 4
            """, true),
    HELP("help", """
            """, false);

    private final String paramIdentifier;
    private final String paramDesc;
    private final boolean hasArg;

    InputParamIdentifierEnum(String paramIdentifier, String paramDesc, boolean hasArg) {
        this.paramIdentifier = paramIdentifier;
        this.paramDesc = paramDesc;
        this.hasArg = hasArg;
    }

    public String getParamIdentifier() {
        return paramIdentifier;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public boolean hasArg() {
        return hasArg;
    }
}
