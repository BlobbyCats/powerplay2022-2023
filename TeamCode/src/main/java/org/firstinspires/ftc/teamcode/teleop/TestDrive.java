package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard;

@TeleOp(name="test drive")
public class TestDrive extends OpMode {
    ProgrammingBoard board = new ProgrammingBoard();
    private int linearTicks;
    private String stage;
    @Override
    public void init() {
        telemetry.addData("TeleOp Code Running","Wait for hardware initialization");
        board.init(hardwareMap);
        telemetry.addData("Robot Hardware Initialized Successfully in TeleOp", "Press Play to Start");
        board.releaseClaw();
        linearTicks = board.FLOOR;
        stage = "FLOOR";
    }

    /***
     * Called only ONCE after the driver presses the play button
     * */
    @Override
    public void start(){
        telemetry.addData("Start was pressed","Game starts now");
        board.moveSlide(linearTicks);
        // board.releaseClaw();
        // board.moveSlide(board.FLOOR);
    }

    @Override
    /***
     * Called continuously after the driver presses the play button
     * */
    public void loop() {
        double leftStickY = -gamepad1.left_stick_y;
        double leftStickX =  gamepad1.left_stick_x;
        double rightStickX = gamepad1.right_stick_x;
        if (gamepad1.right_bumper) {
            board.speed = 1.25;
        }
        else {
            //snail mode
            if (gamepad1.left_bumper) {
                board.speed = 4;
            }
            else {
                board.speed = 2;
            }
        }
        telemetry.addData("Top Left Power ", leftStickY + leftStickX + rightStickX);
        telemetry.addData("Bottom Left Power ", leftStickY + leftStickX + rightStickX);
        telemetry.addData("Top Right Power ", leftStickY + leftStickX - rightStickX);
        telemetry.addData("Bottom Right Power ", leftStickY + leftStickX - rightStickX);
        board.moveRobot(leftStickY, leftStickX, rightStickX);

        /**
         * Gamepad inputs for manipulating the claw servo and linear slide motor
         * **/

        if(gamepad2.left_bumper) {
            board.releaseClaw();
        } else if (gamepad2.right_bumper) {
            board.closeClaw();
        }

        if (gamepad2.a) {
            linearTicks = board.FLOOR;
            board.moveSlide(linearTicks);
            stage = "FLOOR";
            telemetry.addData("Linear Slide Stage", "FLOOR");
        } else if (gamepad2.x) {
            linearTicks = board.LOWPOLE;
            board.moveSlide(linearTicks);
            stage = "LOWPOLE";
            telemetry.addData("Linear Slide Stage", "LOWPOLE");
        } else if(gamepad2.y) {
            linearTicks = board.MIDPOLE;
            board.moveSlide(linearTicks);
            stage = "MIDPOLE";
            telemetry.addData("Linear Slide Stage", "MIDPOLE");
        } else if(gamepad2.b) {
            linearTicks = board.HIPOLE;
            board.moveSlide(linearTicks);
            stage = "HIPOLE";
        } else if(gamepad2.left_stick_y < -0.02 && linearTicks <= 4300) {
            linearTicks += 5;
            board.moveSlide(linearTicks);
            stage = "In between";
        } else if(gamepad2.left_stick_y > 0.02 && linearTicks >= 0) {
            linearTicks -= 5;
            board.moveSlide(linearTicks);
            stage = "In between";
        }
        telemetry.addData("Linear Ticks", linearTicks);
        telemetry.addData("Servo Position", board.getPosition());
        telemetry.addData("Linear Slide Stage", stage);
        /*
        // testing claw servo positions
        if (gamepad1.a) {
            board.moveClaw(0.3);
        } else if (gamepad1.x) {
            board.moveClaw(0.4);
        } else if(gamepad1.y) {
            board.moveClaw(0.65);
        } else if(gamepad1.b) {
            board.moveClaw(0.70);
        }
        */

        /*
        if (gamepad2.left_bumper) {
            board.moveSlide(board.FLOOR);
            telemetry.addData("Linear Slide Stage", "FLOOR");
        }
        else if (gamepad2.x) {
            board.moveSlide(board.JUNCTION);
            telemetry.addData("Linear Slide Stage", "JUNCTION");
        }
        else if (gamepad2.y) {
            board.moveSlide(board.LOWPOLE);
            telemetry.addData("Linear Slide Stage", "LOWPOLE");
        }
        else if (gamepad2.b) {
            board.moveSlide(board.MIDPOLE);
            telemetry.addData("Linear Slide Stage", "MIDPOLE");
        }
        else if (gamepad2.a) {
            board.moveSlide(board.HIPOLE);
            telemetry.addData("Linear Slide Stage", "HIPOLE");
        }
        */

    }
}
