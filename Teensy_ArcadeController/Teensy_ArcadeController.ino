#include <Bounce.h> // Debouncing buttons

const int BUTTON_SIZE = 11; // Amount of buttons
const int DEBOUNCE_TIME = 10; // Amount of time the buttons should take to debounce


boolean enterPressed = false;
boolean handshaked = false;
int enterCount = 0;

int buttonPins[BUTTON_SIZE] = {
  14, // Down
  15, // UP
  12, // Right
  11, // LEFT
  8,  // Button 1 
  16, // Button 0 
  7,  // Button 3
  17, // Button 2
  18, // Button 4
  2,  // Button 5  
  19  // Button Menu
};


// TODO M2 : Build dynamic allocation via serial input
int buttonFunctions[BUTTON_SIZE] = {
  KEY_0, // Button 5
  KEY_0, // Button 3
  KEY_D, // Button 1
  KEY_A, // LEFT
  KEY_ENTER, // Right
  KEY_ENTER, // DOWN
  KEY_ENTER, // UP
  KEY_ENTER, // Button 0
  KEY_ENTER, // Button 2
  KEY_ENTER, // Button 4
  KEY_ESC, // Button Menu
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
  Serial.begin(115200);
}

void loop() {
    serialDelegate();
    
    int i;
    for(i=0; i < BUTTON_SIZE; i = i + 1) // for all buttons do:
    {       
      buttons[i].update();                      // update state of button
      if (buttons[i].fallingEdge()) {           // normally input (button pin) is high due to the internal pull up, so a falling edge means that the button is pressed
        if(buttonFunctions[i] == KEY_ESC){
          enterPressed = true;
        }
        else
        {
          Keyboard.press(buttonFunctions[i]);     // execute corresponding keycode  
        }
      }
      else if (buttons[i].risingEdge()) // when button is released 
      {       
        if(buttonFunctions[i] == KEY_ESC){
          if(enterCount > 0 && enterCount < 30000)
          {
            Serial.println("spFunc:0");  
          }
          enterPressed = false;
          enterCount = 0;
        }
        else
        {
          Keyboard.release(buttonFunctions[i]);   // release corresponding keycode  
        }
      }
    }
    
    if(enterPressed){
      enterCount++;
  
      if(enterCount == 30000){
        Serial.println("spFunc:1");
        enterPressed = false;
        enterCount = 0;
      }
    }
 }


void serialDelegate(){
  if(Serial.available() > 0){
    char firstChar = (char)Serial.read();
    if(firstChar == 'h'){
      doHandShake();
    }
    else if(firstChar == 'b'){
      serialButtonInput();
    }
  }
}

void doHandShake(){
   String content = "";
   char character;
   
   while(Serial.available() > 0) {
     character = Serial.read();
     content.concat(character);
   }
   content = content.replace("\n","");
   if (content != "") {
     if(content == "HS:1"){ 
       Serial.println("HS:2");
       handshaked = true;
     }
   }
}

void serialButtonInput(){
   int i = 0;
   int incomingValue;
   boolean changed = false;
   while(Serial.available() > 0)
   {
     changed = true;
     
       char buffer[] = {' ',' ',' ',' ',' ',' ',' '}; // Receive up to 7 bytes
       Serial.readBytesUntil(',', buffer, 7);
       incomingValue = atoi(buffer);
       if(incomingValue != 0)
       {
         if(i < 10){
           buttonFunctions[i] = incomingValue;
         }
         else
         {
           Serial.flush();
           delay(10);
         }
         i = i + 1;
       }
   }
   
   if(changed)
   {
     Serial.print("bcSET:");
     int i;
     for(i = 0; i < BUTTON_SIZE-1; i = i + 1)
     {
       Serial.print(buttonFunctions[i]);
       Serial.print(",");
     }
     Serial.println("");
   }
}

// Pulls up the button pins
void initPullups(){  
  int i;
  for(i = 0; i < BUTTON_SIZE; i = i + 1){
    pinMode(buttonPins[i], INPUT_PULLUP);
  }
}


