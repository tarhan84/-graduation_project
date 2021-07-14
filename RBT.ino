#include <math.h> 
#include <Servo.h>
#define L_A 6
#define L_B 11
#define R_A 3
#define R_B 5
int hiz=170;
int rd;
int pos=90;
int trigPin = 7;
int echoPin = 8;
int reset_pin=4;
long sure;
long uzaklik;
boolean l_is_on=false;

boolean ileri_b=false;
boolean geri_b=false;
Servo myservo; 

void(* resetFunc) (void)=0;

void setup() {
  Serial.begin(9600);
  myservo.attach(2);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin,INPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
  pinMode(reset_pin,OUTPUT);
  digitalWrite(reset_pin,LOW);
  myservo.write(pos);
}

void loop() {
   rd=Serial.read();
   if(rd=='r')
   sag();
   if(rd=='R')
   sag_1();
   if(rd=='l')
   sol();
   if(rd=='L')
   sol_1();
   if(rd=='i')
   ileri();
   if(rd=='g')
   geri();
   if(rd=='s')
   temp();
   if(rd=='m')
   mesafe();
   if(rd=='x')
   servo_sag();
   if(rd=='y')
   servo_sol();
   if(rd=='t')
    stop_m();
   if(rd=='(')
    dec_hiz();
   if(rd==')')
    inc_hiz();
   if(rd=='q')
     l_on_off();  
   if(rd=='p')
     sinyal();
   if(rd=='*')
     korna();
   if(rd=='5')
     sendPowerLevel();
   if(rd=='Z'){
    l_is_on=false;
    l_on_off();
   }
   if(rd=='X'){
    l_is_on=true;
    l_on_off();
   }
   if(rd=='C'){
    l_is_on=false;
    korna_1();
   }
   int tmp=analogRead(A1);
   if(rd=='!'||tmp<100)
      reset();  
}
void l_on_off(){
  l_is_on=!(l_is_on);
  if(l_is_on){
    digitalWrite(12,HIGH);
  }
  else
    digitalWrite(12,LOW);
}
void dec_hiz(){
  if(hiz-5>=0)
     hiz-=5;
}
void inc_hiz(){
  if(hiz+5<=255)
     hiz+=5;
}
void servo_sag(){
  if(pos-5>=0)
    pos-=5;
    delay(100);
    myservo.write(pos);  
  int tmp=Serial.read();
  if(tmp!='z')
     servo_sag(); 
}
void servo_sol(){
   if(pos+5<=180)
    pos+=5;
    delay(100);
    myservo.write(pos); 
    int tmp=Serial.read();
    if(tmp!='z')
     servo_sol();
}
void mesafe(){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(5);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);  
  sure = pulseIn(echoPin, HIGH); 
  uzaklik= sure /29.1/2;
  if(uzaklik<0)
     uzaklik=0;
  if(uzaklik>400)
     uzaklik=400;
  Serial.println(uzaklik);
}
double Termistor(int analogOkuma){

 double sicaklik;
 sicaklik = log(((10240000 / analogOkuma) - 10000));
 sicaklik = 1 / (0.001129148 + (0.000234125 + (0.0000000876741 * sicaklik * sicaklik)) * sicaklik);
 sicaklik = sicaklik - 273.15;
 return sicaklik;
}


void sendPowerLevel(){
  int tmp=map(hiz,0,255,0,100);
  Serial.println(tmp);
}

void temp(){
  int sc=analogRead(A0);
  double temp=Termistor(sc);
  Serial.println(temp);
}
void geri(){
  ileri_b=false;
  geri_b=true;
  analogWrite(L_A,hiz);
  analogWrite(L_B,0);
  analogWrite(R_A,hiz);
  analogWrite(R_B,0);
}
void ileri(){
  ileri_b=true;
  geri_b=false;
  analogWrite(L_A,0);
  analogWrite(L_B,hiz);
  analogWrite(R_A,0);
  analogWrite(R_B,hiz);
}
void stop_m(){
  ileri_b=false;
  geri_b=false;
  analogWrite(L_A,0);
  analogWrite(L_B,0);
  analogWrite(R_A,0);
  analogWrite(R_B,0);
}
void sag(){
  analogWrite(L_A,0);
  analogWrite(L_B,200);
  analogWrite(R_A,200);
  analogWrite(R_B,0);
}
void sag_1(){
  analogWrite(L_A,0);
  analogWrite(L_B,200);
  analogWrite(R_A,200);
  analogWrite(R_B,0);
  delay(150);
  if(ileri_b)
     ileri();
  else if(geri_b)
     geri();
  else
     stop_m();
}
void sol(){
  analogWrite(L_A,200);
  analogWrite(L_B,0);
  analogWrite(R_A,0);
  analogWrite(R_B,200);
}
void sol_1(){
  analogWrite(L_A,200);
  analogWrite(L_B,0);
  analogWrite(R_A,0);
  analogWrite(R_B,200);
  delay(150);
  if(ileri_b)
     ileri();
  else if(geri_b)
     geri();
  else
     stop_m();
}
void korna(){
  digitalWrite(13,HIGH);
  int rd=Serial.read();
  while(rd!='.'){
    rd=Serial.read();
  }
  digitalWrite(13,LOW);
  
}
void korna_1(){
  digitalWrite(13,HIGH);
  delay(800);
  digitalWrite(13,LOW);
  
}
void sinyal(){
  digitalWrite(12,LOW);
  digitalWrite(13,LOW);
  delay(110);
  digitalWrite(12,HIGH);
  digitalWrite(13,HIGH);
  delay(110);
  digitalWrite(12,LOW);
  digitalWrite(13,LOW);
  delay(110);
  digitalWrite(12,HIGH);
  digitalWrite(13,HIGH);
  delay(110);
  digitalWrite(12,LOW);
  digitalWrite(13,LOW);
  delay(110);
  digitalWrite(12,HIGH);
  digitalWrite(13,HIGH);
  delay(110);
  if(l_is_on)
  digitalWrite(12,HIGH);
  else
  digitalWrite(12,LOW);
  
  digitalWrite(13,LOW);
  int rrd=Serial.read();
  if(rrd!='-')
  sinyal();
  
}
void reset(){
  stop_m();
  digitalWrite(12,LOW);
  digitalWrite(13,LOW);
  l_is_on=false;
  pos=90;
  myservo.write(pos);
}
