package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.Commands;

import java.util.Objects;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class motor implements Subsystem {
    public static final motor INSTANCE = new motor();

    private motor() {}

    private final MotorEx motor = new MotorEx("motor");

    public final Command off = new LambdaCommand().setUpdate(() -> motor.setPower(0)).requires(this);
    public final Command on = new LambdaCommand().setStart(() -> motor.setPower(1)).requires(this);

    public Command useMotor(String action) {
        return new LambdaCommand().setStart(() -> {
            switch (action) {
                case "on":
                    on.schedule();
                case "off":
                    off.schedule();
            }
        });
    }

    @Override
    public void initialize() {
        motor.setPower(0);

        Commands.addCommands(
                "motor", args -> useMotor(
                        ((String) Objects.requireNonNull(args.get("action"))
                        ))
        );
    }
}