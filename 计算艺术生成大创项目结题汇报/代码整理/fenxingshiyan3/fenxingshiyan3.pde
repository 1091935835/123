size(800, 800);  //画布尺寸
background(0);  //背景色

//相关参数的设定
//double a = 0.50, b = 2.00, c = 0.00;
//int mag=60;
//double a = 0.4, b = 1, c = 0; 
//int mag=100;
//double a = 1, b = 4, c = 60;
//int mag=1;
//double a = -1, b = -2, c = -3;
//int mag=5;
double a = 0.5, b = 2.00, c = 0.00;
int mag=60;

double x1=0, y1=0, temp;  //涉及变量

for (int i = 0; i < 2550000; i++) {
  //壁纸公式：
  //xn+1 = yn - sign(xn) | b xn - c |1/2 
  //yn+1 = a - xn 
  temp = x1;
  x1 = y1 - Math.signum(a * x1) * Math.sqrt(Math.abs(b * x1 - c));
  y1 = a - temp;

  //放大+平移
  int m = (int) (x1 * mag + width/2);
  int n = (int) (y1 * mag + height/2);
  //笔触颜色设置
  stroke(i/10000, i/10000, 255);
  //stroke(255-i/10000,255-i/10000, 0); 
  point(m, n);
}