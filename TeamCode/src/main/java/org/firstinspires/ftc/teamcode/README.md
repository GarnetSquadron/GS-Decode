hi

The first thing you want to do to learn how to program the robot is to learn java. I have faith that you can find some decent tutorials on youtube. Specifically learn how classes work and what extension and abstract classes are, and what overriding a function is. It may also help to look into annotations, as they are used to add opmodes to the driver station. It wont be hard to just google some videos. or ask chatgpt. 


fine heres some links:

https://www.youtube.com/watch?v=23HFxAPyJ9U

https://www.youtube.com/watch?v=IUqKuGNasdM

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




- (at least for the logitech controllers) gamepad joystick values are reversed from what you would think, if you push the stick forward you get a negative number and if you push the stick right you get a negative number
- 
_there are more I just dont feel like trying to remember them rn_


### here are some of the most important classes in this code

[Bot.java](HardwareControls/Bot.java)
Holds all imports and objects used to import methods. Basically, when you are making a
new opmode, instead of creating a new instance of each object you need, simply create
an instance of this and

[TeleOpActionScheduler](randomStuffWeArentUsingATM/OpmodeActionSceduling/TeleOpActionScheduler.java)
Adds action supports for non autonomous

[TeleOpAction](randomStuffWeArentUsingATM/OpmodeActionSceduling/TeleOpAction.java)
Used by teleop action scheduler, hotels information

[MotorEx](com/arcrobotics/ftclib/hardware/motors/MotorEx.java)
ftc lib’s motor class, used only in mecanum drive

[PinpointLocalizer](pathing/roadrunner/localizers/PinpointLocalizer.java)
a class that roadrunnerifies the pinpoint
Headless Drive Command:
Takes mecanum drive as a constructor input, headless drive code

[ActionServo.java](hardwareClasses%2FActionServo.java)
Action compatible servo class(probably unneccesary in most cases but idk)

CRServo:
continuous rotation servo

Servo:
servo class provided by sdk
ServoImplEx:
servo class that can disable the servo

REMOVE EXTENDS SUBSYSTEM BASE

DELETE REGULAR DRIVE

Motor:
SDK Motor class
MOTOR:
Custom motor class with controller support

[RAWMOTOR.java](HardwareControls/hardwareClasses/motors/RAWMOTOR.java)
Basic custom motor class with max power and encoder support
[MOTOR.java](HardwareControls/hardwareClasses/motors/MOTOR.java)
This class extends rawmotor and adds positional control using a pid. 

[UpdatableMOTOR.java](HardwareControls/hardwareClasses/motors/UpdatableMOTOR.java)
Uses UpdatePower in a loop to continuously update power

### USEFUL LINKS:

intro to ftc sdk:

https://ftc-docs.firstinspires.org/en/latest/ftc_sdk/overview/index.html

https://ftc-docs.firstinspires.org/en/latest/programming_resources/android_studio_java/Android-Studio-Tutorial.html

pedro docs:
https://pedropathing.com/docs/pathing

visualizer: https://visualizer.pedropathing.com/



roadrunner docs:

https://rr.brott.dev/docs/v1-0