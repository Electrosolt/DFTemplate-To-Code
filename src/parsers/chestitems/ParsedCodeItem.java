package parsers.chestitems;

public class ParsedCodeItem {

    public enum Type {
        TAG,
        NOTE,
        VALUE
    }

    private final String textValue;
    private final Type itemType;

    public ParsedCodeItem(String textValue, Type itemType) {
        this.textValue = textValue;
        this.itemType = itemType;
    }

    public String get() {
        return textValue;
    }

    public Type type() {
        return itemType;
    }

}
