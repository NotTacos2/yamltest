package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Commands;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.NullCommand;
import dev.nextftc.ftc.ActiveOpMode;

public class CommandBuilder {
    public static Command buildCommand(Map<String, Object> entry) {
        String name = ((String) entry.get("name")).toLowerCase();
        ActiveOpMode.telemetry().addData("Parsing command", name);
        ActiveOpMode.telemetry().update();

        CommandFactory factory = Commands.getCommands().get(name);

        if (factory == null) {
            ActiveOpMode.telemetry().addLine("Unknown Command: " + name + ". Adding null command");
            ActiveOpMode.telemetry().update();
            return new NullCommand();
        }

        return factory.create(entry);
    }
}