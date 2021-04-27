float x=40;
float m=0;
float y=0;
int change = 20;
void setup()
{
size(532,400);
}
void draw(){
background(255);
fill(219+change,251+change,226+change);
noStroke();
ellipse(265,178,280,280);

//taiyang
fill(246+change,236,173);
noStroke();
ellipse(200+change,100,60,60);

fill(255+change,221,138);
noStroke();
ellipse(200,100,48,48);

//yunduo1
fill(255);
noStroke();
ellipse(150,120,28,28);

fill(255);
noStroke();
ellipse(170,118,36,36);

fill(255);
noStroke();
ellipse(185,120,18,26);

//yunduo2
fill(255);
noStroke();
ellipse(235,85,20,20);

fill(255);
noStroke();
ellipse(250,85,28,28);

fill(255);
noStroke();
ellipse(262,85,16,18);

//shan
fill(160,244,220);
noStroke();
ellipse(255,200,70,160);

fill(131,235,203);
noStroke();
ellipse(228,200,70,120);

fill(104,221,185);
noStroke();
ellipse(156,200,125,120);

fill(158,245,220);
noStroke();
ellipse(356,200,86,228);

fill(104,221,185);
noStroke();
ellipse(296,200,108,150);

fill(104,221,185);
noStroke();
ellipse(358,190,30,30);

fill(104,221,185);
noStroke();
ellipse(404,200,80,88);

fill(255);
noStroke();
rect(0,195,532,134);

//he
fill(192,249,210);
noStroke();
rect(60,195,410,10,5);

fill(192,249,210);
noStroke();
rect(100,205,324,10,5);

fill(192,249,210);
noStroke();
rect(90,215,296,10,5);

fill(192,249,210);
noStroke();
rect(165,225,185,10,5);

fill(192,249,210);
noStroke();
rect(140,235,245,10,5);

fill(192,249,210);
noStroke();
rect(185,245,150,10,5);

fill(192,249,210);
noStroke();
rect(100,255,265,10,5);

fill(192,249,210);
noStroke();
rect(200,265,110,10,5);

fill(192,249,210);
noStroke();
rect(395,228,45,10,5);

fill(255);
noStroke();
rect(140,205,30,10,5);

fill(255);
noStroke();
rect(280,225,35,10,5);

fill(255);
noStroke();
ellipse(315,250,10,10);
translate(y,0);
chuan();
y=80*sin(m);
m=m+0.005;

}

void chuan()
{
fill(237,253,255);
noStroke();
triangle(165+x,215,195+x,165,195+x,215);
triangle(195+x,220,195+x,160,225+x,205);

fill(60,162,176);
noStroke();
ellipse(210+x,225,88,2);
ellipse(210+x,230,60,2);
ellipse(195+x,193,3,70);
ellipse(180+x,215,25,2);

//ren
fill(126+change,94,62);
noStroke();
ellipse(230+x,213,8,8);

fill(60,162,176);
noStroke();
rect(225+x,213,10,12,3);

fill(247,194,112);
noStroke();
ellipse(230+x,207,8,5);
ellipse(230+x,209,20,3);
}
