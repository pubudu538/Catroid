package org.catrobat.catroid.robot.albert;

public class ControlCommands {

	//for Albert-Robot-Test
	private byte[] buffer = new byte[22];

	private int speed_motor_left = 0; // -100...+100
	private int speed_motor_right = 0; // -100...+100
	private int buzzer = 0;
	private int left_led_green = 0; // 0...255
	private int left_led_blue = 0; // 0...255
	private int left_led_red = 0; // 0...255
	private int right_led_green = 0; // 0...255
	private int right_led_blue = 0; // 0...255
	private int right_led_red = 0; // 0...255
	private int front_led = 0; //Front-LED 0...1
	private int body_led = 0; //Body-LED 0...255
	private byte sendFrameNumber = 0;

	public ControlCommands() {
		buffer[0] = (byte) 0xAA;
		buffer[1] = (byte) 0x55;
		buffer[2] = (byte) 20;
		buffer[3] = (byte) 6;
		buffer[4] = (byte) 0x11;
		buffer[5] = sendFrameNumber;
		buffer[6] = (byte) 0;
		buffer[7] = (byte) 0xFF;
		buffer[8] = (byte) 0; //Left motor
		buffer[9] = (byte) 0; //Right motor
		buffer[10] = (byte) 0; //Buzzer
		buffer[11] = (byte) 0; //Left LED Red
		buffer[12] = (byte) 0; //Left LED Green
		buffer[13] = (byte) 0; //Left LED Blue
		buffer[14] = (byte) 0; //Right LED Red
		buffer[15] = (byte) 0; //Right LED Green
		buffer[16] = (byte) 0; //Right LED Blue
		buffer[17] = (byte) 0; //Front-LED 0...1
		buffer[18] = (byte) 0; //Reserved
		buffer[19] = (byte) 0; //Body-LED 0...255
		buffer[20] = (byte) 0x0D;
		buffer[21] = (byte) 0x0A;
	}

	public void setSpeedOfLeftMotor(int speed) {
		speed_motor_left = speed;
	}

	public void setSpeedOfRightMotor(int speed) {
		speed_motor_right = speed;
	}

	public void setBuzzer(int buzz) {
		buzzer = buzz;
	}

	public void setLeftEye(int red, int green, int blue) {
		left_led_red = red;
		left_led_green = green;
		left_led_blue = blue;
	}

	public void setRightEye(int red, int green, int blue) {
		right_led_red = red;
		right_led_green = green;
		right_led_blue = blue;
	}

	public byte[] getCommandMessage() {
		buffer[5] = (byte) (sendFrameNumber + 1);
		sendFrameNumber = (byte) (sendFrameNumber + 2);
		buffer[8] = (byte) speed_motor_left;
		buffer[9] = (byte) speed_motor_right;
		buffer[10] = (byte) buzzer;
		buffer[11] = (byte) left_led_red;
		buffer[12] = (byte) left_led_green;
		buffer[13] = (byte) left_led_blue;
		buffer[14] = (byte) right_led_red;
		buffer[15] = (byte) right_led_green;
		buffer[16] = (byte) right_led_blue;
		buffer[17] = (byte) front_led;
		buffer[19] = (byte) body_led;
		return buffer;
	}

	public void resetRobotAlbert() {
		buffer[8] = 0;
		buffer[9] = 0;
		buffer[10] = 0;
		buffer[11] = 0;
		buffer[12] = 0;
		buffer[13] = 0;
		buffer[14] = 0;
		buffer[15] = 0;
		buffer[16] = 0;
		buffer[17] = 0;
		buffer[19] = 0;
	}

	/*
	 * public void forward(View view) {
	 * 
	 * buffer[0] = (byte) 0xAA;
	 * buffer[1] = (byte) 0x55;
	 * buffer[2] = (byte) 20;
	 * buffer[3] = (byte) 6;
	 * buffer[4] = (byte) 0x11;
	 * buffer[5] = (byte) 0;
	 * buffer[6] = (byte) 0;
	 * buffer[7] = (byte) 0xFF;
	 * buffer[8] = (byte) 100; //Left motor
	 * buffer[9] = (byte) 100; //Right motor
	 * buffer[10] = (byte) 0; //Buzzer
	 * buffer[11] = (byte) 0; //Left LED Red
	 * buffer[12] = (byte) 0; //Left LED Green
	 * buffer[13] = (byte) 0; //Left LED Blue
	 * buffer[14] = (byte) 0; //Right LED Red
	 * buffer[15] = (byte) 0; //Right LED Green
	 * buffer[16] = (byte) 0; //Right LED Blue
	 * buffer[17] = (byte) 0; //Front-LED 0...1
	 * buffer[18] = (byte) 0; //Reserved
	 * buffer[19] = (byte) 0; //Body-LED 0...255
	 * buffer[20] = (byte) 0x0D;
	 * buffer[21] = (byte) 0x0A;
	 * //mCommandService.write(buffer);
	 * }
	 * 
	 * public void stop(View view) {
	 * 
	 * buffer[0] = (byte) 0xAA;
	 * buffer[1] = (byte) 0x55;
	 * buffer[2] = (byte) 20;
	 * buffer[3] = (byte) 6;
	 * buffer[4] = (byte) 0x11;
	 * buffer[5] = (byte) 0;
	 * buffer[6] = (byte) 0;
	 * buffer[7] = (byte) 0xFF;
	 * buffer[8] = (byte) 0; //Left motor
	 * buffer[9] = (byte) 0; //Right motor
	 * buffer[10] = (byte) 0; //Buzzer
	 * buffer[11] = (byte) 0; //Left LED Red
	 * buffer[12] = (byte) 0; //Left LED Green
	 * buffer[13] = (byte) 0; //Left LED Blue
	 * buffer[14] = (byte) 0; //Right LED Red
	 * buffer[15] = (byte) 0; //Right LED Green
	 * buffer[16] = (byte) 0; //Right LED Blue
	 * buffer[17] = (byte) 0; //Front-LED 0...1
	 * buffer[18] = (byte) 0; //Reserved
	 * buffer[19] = (byte) 0; //Body-LED 0...255
	 * buffer[20] = (byte) 0x0D;
	 * buffer[21] = (byte) 0x0A;
	 * //mCommandService.write(buffer);
	 * }
	 * 
	 * public void backward(View view) {
	 * 
	 * buffer[0] = (byte) 0xAA;
	 * buffer[1] = (byte) 0x55;
	 * buffer[2] = (byte) 20;
	 * buffer[3] = (byte) 6;
	 * buffer[4] = (byte) 0x11;
	 * buffer[5] = (byte) 0;
	 * buffer[6] = (byte) 0;
	 * buffer[7] = (byte) 0xFF;
	 * buffer[8] = (byte) -100; //Left motor
	 * buffer[9] = (byte) -100; //Right motor
	 * buffer[10] = (byte) 0; //Buzzer
	 * buffer[11] = (byte) 0; //Left LED Red
	 * buffer[12] = (byte) 0; //Left LED Green
	 * buffer[13] = (byte) 0; //Left LED Blue
	 * buffer[14] = (byte) 0; //Right LED Red
	 * buffer[15] = (byte) 0; //Right LED Green
	 * buffer[16] = (byte) 0; //Right LED Blue
	 * buffer[17] = (byte) 0; //Front-LED 0...1
	 * buffer[18] = (byte) 0; //Reserved
	 * buffer[19] = (byte) 0; //Body-LED 0...255
	 * buffer[20] = (byte) 0x0D;
	 * buffer[21] = (byte) 0x0A;
	 * //mCommandService.write(buffer);
	 * }
	 * 
	 * public void left(View view) {
	 * 
	 * buffer[0] = (byte) 0xAA;
	 * buffer[1] = (byte) 0x55;
	 * buffer[2] = (byte) 20;
	 * buffer[3] = (byte) 6;
	 * buffer[4] = (byte) 0x11;
	 * buffer[5] = (byte) 0;
	 * buffer[6] = (byte) 0;
	 * buffer[7] = (byte) 0xFF;
	 * buffer[8] = (byte) 50; //Left motor
	 * buffer[9] = (byte) 0; //Right motor
	 * buffer[10] = (byte) 0; //Buzzer
	 * buffer[11] = (byte) 0; //Left LED Red
	 * buffer[12] = (byte) 0; //Left LED Green
	 * buffer[13] = (byte) 0; //Left LED Blue
	 * buffer[14] = (byte) 0; //Right LED Red
	 * buffer[15] = (byte) 0; //Right LED Green
	 * buffer[16] = (byte) 0; //Right LED Blue
	 * buffer[17] = (byte) 0; //Front-LED 0...1
	 * buffer[18] = (byte) 0; //Reserved
	 * buffer[19] = (byte) 0; //Body-LED 0...255
	 * buffer[20] = (byte) 0x0D;
	 * buffer[21] = (byte) 0x0A;
	 * //mCommandService.write(buffer);
	 * }
	 * 
	 * public void right(View view) {
	 * 
	 * buffer[0] = (byte) 0xAA;
	 * buffer[1] = (byte) 0x55;
	 * buffer[2] = (byte) 20;
	 * buffer[3] = (byte) 6;
	 * buffer[4] = (byte) 0x11;
	 * buffer[5] = (byte) 0;
	 * buffer[6] = (byte) 0;
	 * buffer[7] = (byte) 0xFF;
	 * buffer[8] = (byte) 0; //Left motor
	 * buffer[9] = (byte) 50; //Right motor
	 * buffer[10] = (byte) 0; //Buzzer
	 * buffer[11] = (byte) 0; //Left LED Red
	 * buffer[12] = (byte) 0; //Left LED Green
	 * buffer[13] = (byte) 0; //Left LED Blue
	 * buffer[14] = (byte) 0; //Right LED Red
	 * buffer[15] = (byte) 0; //Right LED Green
	 * buffer[16] = (byte) 0; //Right LED Blue
	 * buffer[17] = (byte) 0; //Front-LED 0...1
	 * buffer[18] = (byte) 0; //Reserved
	 * buffer[19] = (byte) 0; //Body-LED 0...255
	 * buffer[20] = (byte) 0x0D;
	 * buffer[21] = (byte) 0x0A;
	 * //mCommandService.write(buffer);
	 * }
	 * 
	 * public void eyecolor(View view) {
	 * 
	 * buffer[0] = (byte) 0xAA;
	 * buffer[1] = (byte) 0x55;
	 * buffer[2] = (byte) 20;
	 * buffer[3] = (byte) 6;
	 * buffer[4] = (byte) 0x11;
	 * buffer[5] = (byte) 0;
	 * buffer[6] = (byte) 0;
	 * buffer[7] = (byte) 0xFF;
	 * buffer[8] = (byte) 0; //Left motor
	 * buffer[9] = (byte) 0; //Right motor
	 * buffer[10] = (byte) 0; //Buzzer
	 * buffer[11] = (byte) 255; //Left LED Red
	 * buffer[12] = (byte) 0; //Left LED Green
	 * buffer[13] = (byte) 0; //Left LED Blue
	 * buffer[14] = (byte) 255; //Right LED Red
	 * buffer[15] = (byte) 0; //Right LED Green
	 * buffer[16] = (byte) 0; //Right LED Blue
	 * buffer[17] = (byte) 0; //Front-LED 0...1
	 * buffer[18] = (byte) 0; //Reserved
	 * buffer[19] = (byte) 0; //Body-LED 0...255
	 * buffer[20] = (byte) 0x0D;
	 * buffer[21] = (byte) 0x0A;
	 * //mCommandService.write(buffer);
	 * }
	 */

}