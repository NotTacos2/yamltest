package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "67 auto lolol", group = "Autonomous")
public class auto extends NextFTCOpMode {

    private final ElapsedTime timer = new ElapsedTime();

    private final String autoFilePath = "yes.yaml";

    private Command allCommands;
    Map<String, CommandFactory> commandFactories;

    boolean isDone = false;

    @Override
    public void onInit() {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try {
            data = yaml.load(hardwareMap.appContext.getAssets().open(autoFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        List<Map<String, Object>> commandList = (List<Map<String, Object>>) data.get("commands");
        commandFactories = Commands.getCommands();

        ArrayList<Command> builtCommands = new ArrayList<>();

        if (commandList == null) {
            telemetry.addLine("No commands found");
            telemetry.update();
        } else {
            for (Map<String, Object> entry : commandList) {
                Command cmd = CommandBuilder.buildCommand(entry);
                builtCommands.add(cmd);
            }
        }

        allCommands = new SequentialGroup(
                builtCommands.toArray(new Command[0])
        );
    }

    @Override
    public void onStartButtonPressed() {
        timer.reset();

        // Start the autonomous program
        allCommands.schedule();
    }

    @Override
    public void onUpdate() {
        telemetry.update();

        if (timer.seconds() >= 30 && !isDone) {
            telemetry.addLine("Out of time, stopping commands");
            allCommands.stop(true);
            isDone = true;
        }
    }

}
