package parsers.codeblocks.impl;

import org.json.JSONObject;
import parsers.CodeIndent;
import parsers.CodeLine;
import parsers.codeblocks.CodeblockParser;

public class EventParser extends CodeblockParser {
    public EventParser(String name) {
        super(name);
    }

    @Override
    public CodeLine getCodeLine(JSONObject codeblock) {
        return new CodeLine(name, codeblock)
                .parseAttribute()
                .parseAction()
                .increaseIndent();
    }
}
