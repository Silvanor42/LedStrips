int Red1=5,Red2=4,Red3=10,Red4=11,Green1=7,Green2=6,Green3=8,Green4=9,Blue1=3,Blue2=2,Blue3=12,Blue4=13,i,k;
int data[50];
int pinArray[]={Red1,Green1,Blue1,Red2,Green2,Blue2,Red3,Green3,Blue3,Red4,Green4,Blue4};

#include <SoftwareSerial.h>
SoftwareSerial Bluetooth(A8, A9); //tx,rx

void setup(){
 Bluetooth.begin(9600);
  for (i=2;i<14;i++){
    pinMode(i,OUTPUT);
  }
}

void loop(){
  while(Bluetooth.available()){
    data[k] = Bluetooth.read();
    k=k+1;
  }
  k=0;
  readParameter(data[0]);
}

void readParameter(int leds){
    if (leds & 1)
      setColor(0);
    if (leds & 2)
      setColor(1);
    if (leds & 4)
      setColor(2);
    if (leds & 8)
      setColor(3);
}

void setColor(int nr){
  for(int y=0; y<3;y++){
    analogWrite(pinArray[nr*3+y],data[y+1]);
  }
}
