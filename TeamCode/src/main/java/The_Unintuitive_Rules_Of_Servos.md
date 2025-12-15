# Universal things about all servos
If the servo cable only has 3 wires in it(red,black, and white most times, although its not always white), then it is a servo that can only receive commands and cannot tell you about it's state. This means that you have no idea where the servo is before you send it to any position.
If you want to move the servo, use `servo.setPosition(double position)`
(if you're a beginner, that line means replace `double position` with a double(which means any number(yes I use nested parentheses))). All the servos can be given an input of 0 to 1, which covers the entire range it is programmed to cover. Sometimes you can reprogram a servo to have a larger or smaller range, depending on the servo.
See this to learn how to disable a servo:
https://github.com/WestsideRobotics/FTC-Power-Monitoring/wiki#disabling-servos---introduction
# The red servos(https://thinkrobotics.com/products/ds3218-servo?srsltid=AfmBOooEs1ZuQ2KrljeWdWltiaasmZngf-cGJ3I3iOfHy28xhyqEL2M8)
This one has a range of 270 degrees. If (before the opmode) the servo is outside of it's range, and then you send it, it will turn the direction that requires the least distance. if the servo starts within it's range, and you send it, it will turn so that it stays within that range.