#include <Bounce.h> // Debouncing buttons

const int BUTTON_SIZE = 11; // Amount of buttons
const int DEBOUNCE_TIME = 10; // Amount of time the buttons should take to debounce
int buttonFunctions[BUTTON_SIZE];

boolean enterPressed = false;
boolean handshaked = false;
boolean emulatorGame = true;
boolean keysPressable = true;
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

// Debounce all the pins

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
  initStartUpButtonConfig(); 
  initPullups();
  Serial.begin(115200);
}

/* Old Standardlayout 
void initStartUpButtonConfig(){
  buttonFunctions[0] = KEY_0; // Button 5
  buttonFunctions[1] = KEY_0; // Button 3
  buttonFunctions[2] = KEY_D; // Button 1
  buttonFunctions[3] = KEY_A; // LEFT
  buttonFunctions[4] = KEY_ENTER; // Right
  buttonFunctions[5] = KEY_ENTER; // DOWN
  buttonFunctions[6] = KEY_ENTER; // UP
  buttonFunctions[7] = KEY_ENTER; // Button 0
  buttonFunctions[8] = KEY_ENTER; // Button 2
  buttonFunctions[9] = KEY_ENTER; // Button 4
  buttonFunctions[10] = KEY_ESC; // Button Menu  
}
*/

void initStartUpButtonConfig(){
  buttonFunctions[0] = KEY_W; // Button 5
  buttonFunctions[1] = KEY_S; // Button 3
  buttonFunctions[2] = KEY_A; // Button 1
  buttonFunctions[3] = KEY_D; // LEFT
  buttonFunctions[4] = KEY_J; // Right
  buttonFunctions[5] = KEY_U; // DOWN
  buttonFunctions[6] = KEY_K; // UP
  buttonFunctions[7] = KEY_I; // Button 0
  buttonFunctions[8] = KEY_L; // Button 2
  buttonFunctions[9] = KEY_O; // Button 4
  buttonFunctions[10] = KEY_ESC; // Button Menu  
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
          if(keysPressable){
            Keyboard.press(buttonFunctions[i]);     // execute corresponding keycode
            if(emulatorGame){
              Keyboard.press(KEY_1);
              Keyboard.press(KEY_5);
            }
          }
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
          if(emulatorGame){
            Keyboard.release(KEY_1);
            Keyboard.release(KEY_5);
          }  
        }
      }
    }
    
    if(enterPressed){
      enterCount++;
  
      if(enterCount == 30000){
        Serial.println("spFunc:1");
        releaseAllKeys();
        initStartUpButtonConfig();
        emulatorGame = true;        
        enterPressed = false;
        enterCount = 0;
      }
    }
 }


void releaseAllKeys(){
    int i;
    for(i=0; i < BUTTON_SIZE; i = i + 1) // for all buttons do:
    {  
      Keyboard.release(buttonFunctions[i]);
    }
     Keyboard.release(KEY_1);
     Keyboard.release(KEY_5);
}

void serialDelegate(){
  if(Serial.available() > 0){
    char firstChar = (char)Serial.read();
    if(firstChar == 'h'){
      doHandShake();
    }
    else if(firstChar == 'b'){
      keysPressable = false;
      releaseAllKeys();
      emulatorGame = false;
      serialButtonInput();
    }
    else if(firstChar == 'e'){
      keysPressable = false;
      releaseAllKeys();
      emulatorGame = true;
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
   keysPressable = true;
}

// Pulls up the button pins
void initPullups(){  
  int i;
  for(i = 0; i < BUTTON_SIZE; i = i + 1){
    pinMode(buttonPins[i], INPUT_PULLUP);
  }
}


