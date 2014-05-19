#include <Bounce.h> // Debouncing buttons

const int BUTTON_SIZE = 11; // Amount of buttons
const int DEBOUNCE_TIME = 10; // Amount of time the buttons should take to debounce

int buttonPins[BUTTON_SIZE] = {
  2,  // Button 5  
  7,  // Button 3
  8,  // Button 1
  11, // Right  
  12, // LEFT
  14, // UP
  15, // Down
  16, // Button 0
  17, // Button 2
  18, // Button 4
  19  // Button Menu
};


// TODO M2 : Build dynamic allocation via serial input
int buttonFunctions[BUTTON_SIZE] = {
  KEY_E, // Button 5
  KEY_F, // Button 3
  MODIFIERKEY_SHIFT, // Button 1
  KEY_D, // Right
  KEY_A, // LEFT
  KEY_W, // UP
  KEY_S, // DOWN
  KEY_SPACE, // Button 0
  KEY_L, // Button 2
  KEY_O, // Button 4
  KEY_Y, // Button Menu
};


// Debounce all the pins
// TODO M3: build a factory for this
Bounce buttons[BUTTON_SIZE] = {
  Bounce(buttonPins[0], DEBOUNCE_TIME),
  Bounce(buttonPins[1], DEBOUNCE_TIME),
  Bounce(buttonPins[2], DEBOUNCE_TIME),
  Bounce(buttonPins[3], DEBOUNCE_TIME),
  Bounce(buttonPins[4], DEBOUNCE_TIME),
  Bounce(buttonPins[5], DEBOUNCE_TIME),
  Bounce(buttonPins[6], DEBOUNCE_TIME),
  Bounce(buttonPins[7], DEBOUNCE_TIME),
  Bounce(buttonPins[8], DEBOUNCE_TIME),
  Bounce(buttonPins[9], DEBOUNCE_TIME),
  Bounce(buttonPins[10], DEBOUNCE_TIME)
};

void setup() {
  initPullups();
  Serial.begin(9600);
}

void loop() {
  int i;
  for(i=0; i < BUTTON_SIZE; i = i + 1){       // for all buttons do:
    buttons[i].update();                      // update state of button
    if (buttons[i].fallingEdge()) {           // normally input (button pin) is high due to the internal pull up, so a falling edge means that the button is pressed
      Keyboard.press(buttonFunctions[i]);     // execute corresponding keycode
    }
    else if (buttons[i].risingEdge()) {       // when button is released
      Keyboard.release(buttonFunctions[i]);   // release corresponding keycode
    }
  } 
}

// Pulls up the button pins
void initPullups(){  
  int i;
  for(i = 0; i < BUTTON_SIZE; i = i + 1){
    pinMode(buttonPins[i], INPUT_PULLUP);
  }
}


