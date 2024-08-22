package parsers.chestitems;

import org.json.JSONObject;
import parsers.chestitems.impl.*;

public enum ChestParsers {

    STRING("str", new StringParser()),
    COMPONENT("txt", new ComponentParser()),
    NUMBER("num", new NumberParser()),
    LOCATION("loc", new LocationParser()),
    VECTOR("vec", new VectorParser()),
    SOUND("sfx", new SoundParser()),
    PARTICLE("pfx", new ParticleParser()),
    POTION("pot", new PotionParser()),
    VARIABLE("var", new VariableParser()),
    GAME_VALUE("gv", new GameValueParser()),
    ITEM("item", new ItemParser()),
    PARAMETER("param", new ParameterParser()),
    HINT("hint", new HintParser()), // See: Bottom right of Functions
    TAG("tag", new TagParser());

    private final ChestParser parser;
    private final String id;

    ChestParsers(String id, ChestParser parser) {
        this.parser = parser;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public ChestParser getParser() {
        return parser;
    }

    public ParsedCodeItem.ElementType getElementType() {
        return switch (this) {
            case TAG -> ParsedCodeItem.ElementType.TAG;
            case HINT -> ParsedCodeItem.ElementType.NOTE;
            default -> ParsedCodeItem.ElementType.VALUE;
        };
    }

    public static ChestParsers getChestParserStruct(String itemID) {
        return switch(itemID) {
            case "txt" -> STRING;
            case "comp" -> COMPONENT;
            case "num" -> NUMBER;
            case "loc" -> LOCATION;
            case "vec" -> VECTOR;
            case "snd" -> SOUND;
            case "part" -> PARTICLE;
            case "pot" -> POTION;
            case "var" -> VARIABLE;
            case "g_val" -> GAME_VALUE;
            case "pn_el" -> PARAMETER;
            case "item" -> ITEM;
            case "hint" -> HINT;
            case "bl_tag" -> TAG;
            default -> throw new IllegalArgumentException("Unexpected chest value type: " + itemID);
        };
    }

    public static ParsedCodeItem parse(JSONObject item) {
        String itemID = item.getString("id");
        var parserStruct = getChestParserStruct(itemID);
        ChestParser parser = parserStruct.getParser();

        JSONObject data = item.getJSONObject("data");
        String parsedText = parser.getValue(data);

        return new ParsedCodeItem(parsedText, parserStruct.getElementType(), parserStruct.getID());
    }

}
