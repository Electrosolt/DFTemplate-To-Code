package parsers.codeblocks;

import parsers.codeblocks.impl.*;

public enum CodeblockParsers {

    PLAYER_EVENT(new EventParser("PlayerEvent")),
    ENTITY_EVENT(new EventParser("EntityEvent")),
    FUNCTION(new ParamEventParser("Function")),
    PROCESS(new ParamEventParser("Process")),
    CALL_FUNCTION(new CallParser("CallFunction")),
    START_PROCESS(new CallParser("StartProcess")),
    PLAYER_ACTION(new GeneralParser("PlayerAction")),
    ENTITY_ACTION(new GeneralParser("EntityAction")),
    GAME_ACTION(new GeneralParser("GameAction")),
    SET_VAR(new GeneralParser("SetVar")),
    IF_PLAYER(new BracketedParser("IfPlayer")),
    IF_ENTITY(new BracketedParser("IfEntity")),
    IF_GAME(new BracketedParser("IfGame")),
    IF_VAR(new BracketedParser("IfVar")),
    ELSE(new CodeblockParser("Else")),
    CONTROL(new GeneralParser("Control")),
    REPEAT(new BracketedParser("Repeat")),
    SELECT_OBJECT(new GeneralParser("SelectObject")),
    OPEN_BRACKET(new BracketParser("{", true)),
    CLOSE_BRACKET(new BracketParser("}", false));

    private CodeblockParser parser;

    CodeblockParsers(CodeblockParser parser) {
        this.parser = parser;
    }

    public CodeblockParser getParser() {
        return parser;
    }

    public static CodeblockParser getCodeblockParser(String blockName) {
        CodeblockParsers parser = switch (blockName) {
            case "event" -> PLAYER_EVENT;
            case "entity_event" -> ENTITY_EVENT;
            case "func" -> FUNCTION;
            case "process" -> PROCESS;
            case "call_func" -> CALL_FUNCTION;
            case "start_process" -> START_PROCESS;
            case "player_action" -> PLAYER_ACTION;
            case "entity_action" -> ENTITY_ACTION;
            case "game_action" -> GAME_ACTION;
            case "set_var" -> SET_VAR;
            case "if_player" -> IF_PLAYER;
            case "if_entity" -> IF_ENTITY;
            case "if_game" -> IF_GAME;
            case "if_var" -> IF_VAR;
            case "else" -> ELSE;
            case "control" -> CONTROL;
            case "repeat" -> REPEAT;
            case "select_obj" -> SELECT_OBJECT;
            case "open_bracket" -> OPEN_BRACKET;
            case "close_bracket" -> CLOSE_BRACKET;

            default -> throw new IllegalStateException("Unexpected codeblock type: " + blockName);
        };

        return parser.getParser();
    }
}
