#include"basic.h"
#include <sstream>
using namespace std;


LStack<double> OPND;
LStack<char> OPTR;
char input[50];
char tempStr[40];
int current;

//获取并返回操作符 ch 的栈内优先级
int isp(char ch) {
	if (ch == '=') return 0;
	if (ch == '+') return 3;
	if (ch == '-') return 3;
	if (ch == '*') return 5;
	if (ch == '/') return 5;
	if (ch == '%') return 5;
	if (ch == '(') return 1;
	if (ch == ')') return 8;
	if (ch == '^') return 7;
	if (ch == '&') return 7;
	printf("isp:无效字符");
}

// 获取并返回操作符 ch 的栈外优先级
int osp(char ch) {
	if (ch == '=') return 0;
	if (ch == '+') return 2;
	if (ch == '-') return 2;
	if (ch == '*') return 4;
	if (ch == '/') return 4;
	if (ch == '%') return 4;
	if (ch == '(') return 8;
	if (ch == ')') return 1;
	if (ch == '^') return 6;
	if (ch == '&') return 6;
	printf("osp:无效字符");
}

// 计算r = x op y， 计算成功，返回true.
int  cal(char op, double x, double y, double& r) {
	double* p = &r;
	switch (op) {
	case '+':
		*p = x + y;
		break;
	case '-':
		*p = x - y;
		break;
	case '*':
		*p = x * y;
		break;
	case '/':
		if (y == 0) {
			puts("error, divisor is 0\n");
			return false;
		}
		else {
			*p = x / y;
			break;
		}
	case '%':
		if (y == 0) {
			puts("error,divisor is 0\n");
			return false;
		}
		else {
			*p = fmod(x, y);
			break;
		}
	case '^':
		if (x == 0 && y <= 0) {
			puts("error, 0 can't operate on negative powers or 0 power.\n");
			return false;
		}
		else {
			*p = pow(x, y);
			break;
		}
	case '&':
		if (y <= 0) {
			puts("error, negative roots and 0 root are meaningless\n");
			return false;
		}
		else if (x < 0 && fmod(y, 2) < 0.000001) {
			puts("error, Negative Numbers don't have even root.\n");
			return false;
		}
		else {
			if (x < 0) {
				*p = -pow(-x, 1 / y);
			}
			else {
				*p = pow(x, 1 / y);
			}
			break;
		}
	default:
		return false;
	}
	return true;
}

// 从输入的表达式中获取一个字符
void GetNextChar(char& ch){
	ch = input[current++];
}

//判断输入是否已经读取完毕
int inputEnd() {
	if (current < strlen(input)) return 1;
	return 0;
}

// 判断ch是否为数字0-9
int isDigit(char ch) {
	if ((ch >= '0') && (ch <= '9'))
	{
		return true;
	}
	return false;
}

//从input的current位开始读取一个数并返回.
double readOperand() {
	double operand;
	current--;
	istringstream istr1(input + current);
	istr1 >> operand;
	char ch = input[current];
	// 移动current
	while (isDigit(ch) || ch == '.') ch = input[++current];
	return operand;
}

// 判断ch是否为操作符
int IsOperator(char ch) {
	char opt_set[13] = "+-*/%&^()=.";
	for (int i = 0; i < 15; i++) {
		if (ch == opt_set[i])
			return true;
	}
	return false;
}

// 从操作数栈中取2个操作数
int Get2Operands(LStack<double>& OPND, double& x, double& y) {
	if (OPND.length() < 2)
	{
		cerr << "Get2Operands error" << endl;
		return false;
	}
	else
	{
		y = OPND.pop();
		x = OPND.pop();
		return true;
	}
}

// 打印错误信息
void printWrongInput(char* wrongMessagge) {
	printf(wrongMessagge);

}

//检查表达式中是否有不合法的字符输入
int checkChar(char* input)
{

	for (int i = 0; i < strlen(input); i++) {
		if (!IsOperator(input[i]) && !isdigit(input[i])) {
			sprintf(tempStr, "您的输入第%d位中有错误的操作数或操作符\n", i + 1);
			printWrongInput(tempStr);
			return false;
		}
	}
	return true;
}

int checkOpPosition(char* input) {
	int left_bra = 0;
	int right_bra = 0;
	for (int i = 0; i < strlen(input); i++) {
		if (IsOperator(input[i])) {
			if (input[i] == '(') {
				left_bra++;
			}
			else if (input[i] == ')') {
				right_bra++;
			}
			if (i == 0 && (input[i] != '&' && input[i] != '(' && input[i] != '-')) {
				sprintf(tempStr, "你的表达式首位不规范地使用了操作符\n");

				return false;
			}
			else if (i == strlen(input) - 1 && input[i] != '=') {
				printf("你的表达式末位不是等号\n");
				return false;
			}
			else if (i != 0 && i != strlen(input) - 1)
			{
				if (input[i] == '.')//判断小数
				{
					if (!isdigit(input[i - 1]) || !isdigit(input[i + 1]))
					{
						int pos = i + 1;
						printf("你的表达式第%d位有不规范的'.'\n", pos);
						return false;
					}
				}
				else if (input[i] == '(')
				{
					if (isdigit(input[i - 1]) || (!isdigit(input[i + 1]) && input[i + 1] != '-' && input[i + 1] != '('))
					{
						int pos = i + 1;
						printf("你的表达式第%d位有不规范的'('\n", pos);
						return false;
					}
				}
				else if (input[i] == ')')
				{
					if ((!isdigit(input[i - 1]) && input[i - 1] != ')') || isdigit(input[i + 1]))
					{
						int pos = i + 1;
						printf("你的表达式第%d位有不规范的')'\n", pos);
						return false;
					}
				}
				else if (input[i] == '-') {
					if ((!isdigit(input[i - 1]) && (input[i - 1] != ')') && input[i - 1] != '(') || ((input[i + 1] != '(') && !isdigit(input[i + 1]))) {
						printf("你的表达式第%d位有不规范的连续两运算符出现\n", i + 1);
						return false;
					}
				}
				else
				{
					if ((!isdigit(input[i - 1]) && (input[i - 1] != ')')) || ((input[i + 1] != '(') && !isdigit(input[i + 1])))
					{

						printf("你的表达式第%d位有不规范的连续两运算符出现\n", i + 1);
						return false;
					}
				}
			}


		}
	}
	if (left_bra != right_bra)
	{
		printf("您输入的表达式中，括号的对数不匹配\n");
		printf("%d\n", left_bra);
		printf("%d\n", right_bra);
		return false;
	}

	return true;
}

//判断输出是否合法。合法返回1；非法则调用printWrongInput方法打印错误信息，并返回0
int isLegalInput(char* input) {
	return checkChar(input) && checkOpPosition(input);
}

//打印提示信息
void printIntroduction() {
	//green on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_GREEN);

	cout << "**********************欢迎使用计算器1.0************************************" << endl;
	//Red on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_RED);

	cout << "使用说明：" << endl;
	cout << "1：本计算器支持以下运算符(括号后面为对应应输入字符)" << endl;
	cout << "加法（+）\t减法（-）\t乘法（*）\t除法（/）\t乘方（^）\n开方（&）\t求模（%）\t括号（（、））" << endl;
	cout << "2:本计算器的所有运算符需要在英文模式下输入" << endl;
	cout << "3:退出请输入连续的两个'&'" << endl;

	//green on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_GREEN);
	cout << "***************************************************************************" << endl;

	//white on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);

}

//请求输入表达式
void getInput() {

	puts("输入表达式:");
	//black on white
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), BACKGROUND_INTENSITY |
		FOREGROUND_INTENSITY | BACKGROUND_RED | BACKGROUND_GREEN | BACKGROUND_BLUE);

	scanf(" %[^\n]", input);

	//white on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);

	if (input[0] == '&' && input[1] == '&') return;//结束条件

	for (int i = 0; i < 50; i++) {
		if (input[i] == '\0') {
			if (input[i - 1] != '=') {
				input[i] = '=';
				input[i + 1] = '\0';
			}
			break;
		}
	}

	int i, j;
	int count = 0;
	for (i = 0, j = 0; input[i]; i++)
	{
		if (input[i] != ' ') input[j++] = input[i];
	}
	input[j] = '\0';

	if (!isLegalInput(input)) {
		printf("请重新");
		getInput();
	}

	current = 0;


}