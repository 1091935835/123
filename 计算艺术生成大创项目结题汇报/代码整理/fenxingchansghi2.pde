size(650, 650);  //画布尺寸
background(0);  //背景色
double a = -1, b = -2, c = -3;
int mag=5;

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
  stroke(255, i/10000, i/10000);
  point(m, n);
}