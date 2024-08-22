package dftemplatetocode.parsers.chestitems;

public class ParsedCodeItem {

    public enum ElementType {
        TAG,
        NOTE,
        VALUE
    }

    private final String textValue;
    private final ElementType elementType;
    private final String dataType;

    public ParsedCodeItem(String textValue, ElementType elementType, String dataType) {
        this.textValue = textValue;
        this.elementType = elementType;
        this.dataType = dataType;
    }

    public String get() {
        return textValue;
    }

    public ElementType elementType() {
        return elementType;
    }

    public String type() {
        return dataType;
    }

}
