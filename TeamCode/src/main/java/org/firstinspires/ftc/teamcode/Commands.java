package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.CommandBuilder;
import org.firstinspires.ftc.teamcode.CommandFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.core.commands.delays.Delay;


public class Commands {

    private static final Map<String, CommandFactory> commands = new HashMap<>(Map.of(
            "wait", args ->
                    new Delay(((Number) args.get("duration")).doubleValue() / 1000
                    )
    ));

    public static Map<String, CommandFactory> getCommands() {
        return commands;
    }

    public static void addCommands(Map<String, CommandFactory> newCommands) {
        commands.putAll(newCommands);
    }

    public static void addCommands(String name, CommandFactory factory) {
        commands.put(name, factory);
    }
}