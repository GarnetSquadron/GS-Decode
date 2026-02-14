hi

The first thing you want to do to learn how to program the robot is to learn java. I have faith that you can find some decent tutorials on youtube. Specifically learn how classes work and what extension and abstract classes are, and what overriding a function is. It may also help to look into annotations, as they are used to add opmodes to the driver station. It wont be hard to just google some videos. or ask chatgpt. 


fine heres some links:

https://www.youtube.com/watch?v=23HFxAPyJ9U

https://www.youtube.com/watch?v=IUqKuGNasdM

https://www.youtube.com/watch?v=HvPlEJ3LHgE

https://www.youtube.com/watch?v=4TO8b8P9H7s

https://www.youtube.com/watch?v=eIrMbAQSU34

https://www.youtube.com/watch?v=r3GGV2TG_vw&list=PL_c9BZzLwBRKIMP_xNTJxi9lIgQhE51rF

https://youtu.be/dQw4w9WgXcQ

I havent watched these but the thumbnails looked good

### Tutorial on making an OPMODE
Now that you know basic java, and are familiar with classes, time to explain how opmodes work. 
There is an abstract class called OpMode, which has a couple of methods. The important ones to remember are init() and loop(). If you want to write a new opmode, you need to make a new class that extends this class. 
Next, in order to make the OpMode appear on the driver station, you need to add the annotation `@TeleOp(name = "name",group = "group")` right before the class.
Next, you need to override the  init() and loop() functions. 
Opmodes are separated into two phases, init and the main loop. init is when you press the init button. the robot is not supposed to move when you press the init button, and then you can press the start button to start the main loop
- init() is what happens the instant you press the init button on the driver station. 
- Optionally, you can override initLoop(), which is run in a loop while in the init phase, which is until start is pressed. if you don't override it it will just do nothing during init.
- also optionally, you can override start(), which is run once the moment that start(not init) is pressed.
- then you have to override loop(), which runs from when start is pressed to when you stop it.


### IF YOU ARE A BEGINNER(OR REALLY ANYONE WHO HASNT SEEN THIS ) THIS WILL SAVE YOU SO MUCH PAIN:

[The_Unintuitive_Rules_Of_Servos.md](..%2F..%2F..%2F..%2FThe_Unintuitive_Rules_Of_Servos.md)

### LIST OF OTHER RANDOM UNINTUITIVE THINGS ABOUT THE SDK
_feel free to add stuff if you find more_




+ (at least for the logitech controllers) gamepad joystick values are reversed from what you would think, if you push the stick forward you get a negative number and if you push the stick right you get a negative number
+ the default sdk Telemetry class is very confusing. Telemetry.clearAll() doesn't seem to clear everything, telemetry.update doesn't seem to actually update anything unless you add `telemetry.addData()` lines. Check out [SimplerTelemetry.java](SimplerTelemetry.java) and see more details about how it works in [list of important classes](https://github.com/GarnetSquadron/GS-Decode/blob/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/README.md#here-are-some-of-the-most-important-classes-in-this-code)
+ 

_there are more I just dont feel like trying to remember them rn_


### here are some of the most important classes in this code

[Bot.java](HardwareControls/Bot.java)
Holds all imports and objects used to import methods. Basically, when you are making a
new opmode, instead of creating a new instance of each object you need, simply create
an instance of this and

Telemetry
This is the class that allows you to display data onto the driver station. The OpMode class contains an instance of it(called `telemetry`), and so when you write a new opmode its basically like `system.out`. So the ftc version of `system.out.println()` is `telemetry.addLine()`, but then you also need to add the line `telemetry.update()`. It also has a method called `telemetry.addData()`. This is used to easily display any non-string thing. it takes a String  and an Object. 

[SimplerTelemetry.java](SimplerTelemetry.java)
I got really fed up with the built in telemetry because some of its functions are counter intuitive. So I made this class, which you can use instead. You initialize it with new SimplerTelemetry(telemetry) The really nice thing about this class is that it stores the output in a static string, which means that you can add output to the telemetry in any method that doesn't have access to the specific instance that 

CRServo:
continuous rotation servo

Servo:
servo class provided by sdk
ServoImplEx:
servo class that can disable the servo

[RAWMOTOR.java](HardwareControls/hardwareClasses/motors/RAWMOTOR.java)
Basic custom motor class with max power and encoder support
[MOTOR.java](HardwareControls/hardwareClasses/motors/MOTOR.java)
This class extends rawmotor and adds positional control using a pid. 

[UpdatableMOTOR.java](HardwareControls/hardwareClasses/motors/UpdatableMOTOR.java)
Uses UpdatePower in a loop to continuously update power

DcMotorEx:
if you want you can use this instead of the motor class I made, it has worse features though

### USEFUL LINKS:

intro to ftc sdk:

https://ftc-docs.firstinspires.org/en/latest/ftc_sdk/overview/index.html

https://ftc-docs.firstinspires.org/en/latest/programming_resources/android_studio_java/Android-Studio-Tutorial.html

pedro docs:
https://pedropathing.com/docs/pathing

visualizer: https://visualizer.pedropathing.com/



roadrunner docs:

https://rr.brott.dev/docs/v1-0