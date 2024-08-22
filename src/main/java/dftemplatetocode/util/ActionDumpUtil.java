package dftemplatetocode.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ActionDumpUtil {

    private static Map<String, JSONObject> actionDump;

    public static void loadActionDump() {

        actionDump = new HashMap<>();
        JSONArray actions = null;

        try (InputStream inputStream = ActionDumpUtil.class.getClassLoader().getResourceAsStream("actiondump.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                try {
                    //System.out.println(line);
                    actions = new JSONObject(line).getJSONArray("actions");
                } catch (JSONException exception) {
                    System.out.println("Could not parse actiondump json.");
                }

            }
        } catch (Exception ignored) {
            System.out.println("Could not set up actiondump reader");
        }

        if (actions == null) {
            System.out.println("Error setting up actions array");
            return;
        }

        for (int i = 0; i < actions.length(); i++) {
            JSONObject action = actions.getJSONObject(i);
            String actionName = action.getString("name");
            String codeBlock = action.getString("codeblockName").replace(" ", "_").toLowerCase();
            actionDump.put(getDumpKey(codeBlock, actionName), action);

            JSONArray aliases = action.getJSONArray("aliases");
            for (int j = 0; j < aliases.length(); j++) {
                String aliasKey = getDumpKey(codeBlock, aliases.getString(j));
                if (actionDump.containsKey(aliasKey)) {
                    System.out.println("Duplicate alias key! " + aliasKey);
                }
                actionDump.put(aliasKey, action);
            }

        }

//        for (var entry : actionDump.entrySet()) {
//            System.out.println(entry.getKey() + " ->\n" + entry.getValue().toString(2));
//        }
    }

    private static String getDumpKey(String codeBlock, String actionName) {
        return "[" + codeBlock  + "]" + actionName;
    }

    public static String getTagDefault(String codeblock, String action, String tagName) {
        //System.out.println("Getting key " + getDumpKey(codeblock, action));
        JSONObject actionData = actionDump.get(getDumpKey(codeblock, action));
        //System.out.println(actionData.toString(2));
        JSONArray tagArray = actionData.getJSONArray("tags");

        JSONObject tagData = null;
        for (int i = 0; i < tagArray.length(); i++) {
            JSONObject tag = tagArray.getJSONObject(i);
            if (tag.getString("name").equals(tagName)) {
                tagData = tag;
                break;
            }
        }

        if (tagData == null) {
            return null;
        }

        return tagData.getString("defaultOption");

    }

    public static String mapToDumpCodeblock(String codeblock) {
        return switch (codeblock) {
            case "event" -> "player_event";
            case "entity_event" -> "entity_event";
            case "func" -> "function";
            case "process" -> "process";
            case "call_func" -> "call_function";
            case "start_process" -> "start_process";
            case "player_action" -> "player_action";
            case "entity_action" -> "entity_action";
            case "game_action" -> "game_action";
            case "set_var" -> "set_variable";
            case "if_player" -> "if_player";
            case "if_entity" -> "if_entity";
            case "if_game" -> "if_game";
            case "if_var" -> "if_variable";
            case "else" -> "else";
            case "control" -> "control";
            case "repeat" -> "repeat";
            case "select_obj" -> "select_object";
            default -> codeblock;
        };
    }

}
