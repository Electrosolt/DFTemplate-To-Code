package parsers;

import org.json.JSONArray;
import org.json.JSONObject;
import parsers.chestitems.ChestParsers;
import parsers.chestitems.ParsedCodeItem;

import java.util.ArrayList;
import java.util.List;

public class CodeLine {

    private final JSONObject codeblockData;

    public String codeblockName;
    public String action = null;
    private String parameters = null;
    private String target = null;
    private String chestTags = null; // Needs support for var tags
    private String attribute = null;
    private String subaction = null;
    private boolean doSemicolon = false;
    private CodeIndent indent = CodeIndent.NONE;

    public CodeLine(String codeblock, JSONObject codeblockData) {
        this.codeblockData = codeblockData;
        this.codeblockName = codeblock;
    }

    @Override
    public String toString() {
        //return line.indent(indent).stripTrailing();

        StringBuilder builder = new StringBuilder(32);

        builder.append(codeblockName);

        if (action != null) {
            builder.append("<\"").append(action.strip());

            if (subaction != null) {
                builder.append(" | ").append(subaction.strip());
            }
            if (attribute != null) {
                builder.append(" | ").append(attribute);
            }

            builder.append("\">");
        }


        if (parameters != null) {
            builder.append(parameters);
        }

        if (chestTags != null) {
            builder.append(chestTags);
        }

        if (target != null) {
            builder.append("@").append(target);
        }

        if (doSemicolon) {
            builder.append(";");
        }

        return builder.toString();
    }

    /**
     * Tries to parse the codeblock's code chest (arguments and tags).
     * @return this
     */
    public CodeLine parseChest() {

        JSONArray args = codeblockData.getJSONObject("args").getJSONArray("items");
        List<String> parsedArgs = new ArrayList<>();
        List<String> parsedTags = new ArrayList<>();

        for (int i = 0; i < args.length(); i++) {
            JSONObject arg = args.getJSONObject(i).getJSONObject("item");
            ParsedCodeItem parsed = ChestParsers.parse(arg);

            if (parsed.get() == null) continue;

            switch (parsed.type()) {
                case TAG -> parsedTags.add(parsed.get());
                case VALUE -> parsedArgs.add(parsed.get());
            }

        }
        parameters = "(" + String.join(", ", parsedArgs) + ")";
        if (!parsedTags.isEmpty()) {
            chestTags = "#" + String.join("#", parsedTags);
        }

        return this;
    }

    /**
     * Tries to parse the codeblock's action.
     * @return this
     */
    public CodeLine parseAction() {
        this.action = codeblockData.optString("action", null);
        return this;
    }

    /**
     * Tries to parse the codeblock's data field and sets it as the action. Useful for custom text such as function names.
     * @return this
     */
    public CodeLine parseDataAsAction() {
        this.action = codeblockData.optString("data", null);
        return this;
    }

    /**
     * Tries to parse the codeblock's target override. (Sneak + RC)
     * @return this
     */
    public CodeLine parseTarget() {
        this.target = codeblockData.optString("target", null);
        return this;
    }

    /**
     * Tries to parse the codeblock's attribute, such as NOT or LS-CANCEL.
     * @return this
     */
    public CodeLine parseAttribute() {
        this.attribute = codeblockData.optString("attribute", null);
        return this;
    }

    /**
     * Tries to parse the codeblock's subaction, such as when dealing with Select Object by Condition.
     * @return
     */
    public CodeLine parseSubAction() {
        this.subaction = codeblockData.optString("subAction", null);
        return this;
    }

    /**
     * Adds a semicolon after the codeline.
     * @return this
     */
    public CodeLine addSemicolon() {
        this.doSemicolon = true;
        return this;
    }

    /**
     * Sets the indent mode directly. Probably use {@link #increaseIndent()} or {@link #decreaseIndent()} instead.
     * @param indent Indent type
     * @return this
     */
    public CodeLine setIndent(CodeIndent indent) {
        this.indent = indent;
        return this;
    }

    /**
     * Increases the indent level of codelines after this.
     * @return this
     */
    public CodeLine increaseIndent() {
        this.indent = CodeIndent.INCREASE;
        return this;
    }

    /**
     * Decreases the indent level of this codeline and codelines after it.
     * @return this
     */
    public CodeLine decreaseIndent() {
        this.indent = CodeIndent.DECREASE;
        return this;
    }

    /**
     * Attempts to parse the action, chest, subaction, attribute, and target of a codeblock.
     * @return this
     */
    public CodeLine parseAll() {
        parseAction().parseChest().parseSubAction().parseAttribute().parseTarget();
        return this;
    }

    public boolean isDecreasingIndent() {
        return indent == CodeIndent.DECREASE;
    }

    public boolean isIncreasingIndent() {
        return indent == CodeIndent.INCREASE;
    }

}
