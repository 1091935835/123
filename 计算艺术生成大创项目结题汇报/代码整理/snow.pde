Snow snow;

void setup(){
  size(1000,1000);
  PVector p= new PVector(width/2,height/2);
  snow =new Snow(400,0.618,p);

}
void draw(){
  background(0);
  stroke(255);
  snow.run();
  
}

class Snow{
  float radius,ratio;
  PVector pos;   //initial position
  
  Snow( float ra, float rat ,PVector p){
    radius=ra;
    ratio=rat;
    pos=p;
  }
  void run(){
    for(int i=0;i<6;i++){
      //pushMatrix();
      //translate(pos.x,pos.y);
      generate(i);
      //rotate(radians(60));
     // popMatrix();
    }
  }
    
  void generate(int i){
    pushMatrix();
    translate(pos.x,pos.y);
    rotate(radians(60)*i);
    float len=getLen(radius,ratio);
    recursion(len);
    popMatrix();
  }
  void recursion(float len){
    strokeWeight(len*0.1);
    strokeCap(ROUND);  //make the head of line round
    line (0,0,0,-len);
    translate(0,-len);
    
    if(len<=2) return; //len>2 it can be seen
    else{
      pushMatrix();recursion(len*ratio); popMatrix();                //draw the middle
      pushMatrix();rotate(radians(-60));recursion(getLen(len,ratio));popMatrix();//draw the left
      pushMatrix();rotate(radians(60));recursion(getLen(len,ratio));popMatrix();// right;
    }
  }
  
  float getLen(float rad,float rat){
    return (1-rat)*rad;
  }
}
