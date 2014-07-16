/*
Simple LED ON/OFF/BLINK Commands

Protocol = PIN CURRENT-TIME TURN-OFF-TIME TURN-ON-AND-REPEAT-TIME

e.g.

u 2 0 100 200		LED 0 Off after 100, On after 100

u 3 0 0 0			LED 1 Always OFF

u 4 1 0 0 			LED 2 Always ON
*/

const int PINS = 14;
const int ON = LOW;
const int OFF = HIGH;

// holds the current time for the LED
int ledState[PINS];

// holds the time when the LED should turn OFF
// 0 < ledSwitch < ledRepeat
int ledSwitch[PINS];

// holds the time when the LED should turn ON 
// and the timer should start again
// 0 < ledSwitch < ledRepeat
int ledRepeat[PINS];

void setup() {
	Serial.begin(9600);

	for (int i=2; i<PINS; ++i) {
		pinMode(i, OUTPUT); 
		ledState[i] = 0;
		ledSwitch[i] = 0;
		ledRepeat[i] = 0;
		digitalWrite(i, OFF);
	}
}

void loop() {
	// adjust the leds
	for (int i=2; i<PINS; ++i) {
		// if ledRepeat is <= 0 the light does not change state
		if (ledRepeat[i] <= 0) {
			continue;
		}

		++ledState[i];

		if (ledState[i] >= ledRepeat[i]) {
			digitalWrite(i, ON);
			ledState[i] = 0;
		}
		else if (ledState[i] == ledSwitch[i]) {
			digitalWrite(i, OFF);
		}
	}

	// read commands
	while (Serial.available() > 0) {
		char c = Serial.read();

		if (c == 'u') {		
			int pin = Serial.parseInt();
			int state = Serial.parseInt();
			int swtch = Serial.parseInt();
			int repeat = Serial.parseInt();

			if ((state > repeat) || (state < swtch)) {
				digitalWrite(pin, ON);
			}
			else {
				digitalWrite(pin, OFF);
			}

			ledState[pin] = state;
			ledSwitch[pin] = swtch;
			ledRepeat[pin] = repeat;

			Serial.print("Updated LED ");
			Serial.println(pin);
		}
		else if (c > ' ') {
			Serial.print("Invalid character: ");
			Serial.println(c);
		}
	}

	delay(10);
}