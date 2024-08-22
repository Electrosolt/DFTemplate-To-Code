package dftemplatetocode.parsers.codeblocks.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.CodeLine;
import dftemplatetocode.parsers.codeblocks.CodeblockParser;

public class GeneralParser extends CodeblockParser {

    public GeneralParser(String name) {
        super(name);
    }

    @Override
    public CodeLine getCodeLine(JSONObject codeblock) {
        return new CodeLine(name, codeblock)
                .parseAll()
                .addSemicolon();
    }
}
