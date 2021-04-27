#include"basic.h"


using namespace std;
extern LStack<double> OPND;
extern LStack<char> OPTR;
extern char input[50];


void start();
void testRead();

int main()
{
	printIntroduction();

	//testRead();
	do {
		getInput();
		if (input[0] == '&' && input[1] == '&') break;
		start();
	} while (1);
	printf("感谢使用本计算器\n");
	return 0;
}

//123-4*5+6=
void start(){
	//prior_char 表示当前处理字符的前一个字符，如为数，则其值'0'
	char prior_char, ch, OPTR_top;
	double operand, tempDouble, tempR, tempA, tempB;

	prior_char = '=';
	OPTR.push(prior_char);
	GetNextChar(ch);
	OPTR_top = OPTR.topValue();
	while (OPTR_top != '=' || ch != '=') {
		// 当前字符为数字
		if (isDigit(ch) || ch == '.') {
			operand = readOperand();
			OPND.push(operand);
			prior_char = '0';
			GetNextChar(ch);
		}
		else if (!IsOperator(ch)) {
			cout << "表达式有错!" << endl;
		}
		// 当前字符为运算符
		else {
			//第一个数字带有符号的情况
			if ((prior_char == '=' || prior_char == '(') &&
				(ch == '+' || ch == '-')) {
				tempDouble = 0;
				OPND.push(tempDouble);
				//OPTR.push(ch);
				//GetNextChar(ch);
				//operand = readOperand();
				//OPND.push(operand);
				prior_char = '0';
				//GetNextChar(ch);
			}
			//栈内优先级低于栈外优先级
			if (isp(OPTR_top) < osp(ch)){//内比外低OPTR再压进一个数
				OPTR.push(ch);
				prior_char = ch;
				GetNextChar(ch);
			}
			//栈内优先级高于栈外优先级
			else if (isp(OPTR_top) > osp(ch)) {
				Get2Operands(OPND, tempA, tempB);
				OPTR_top = OPTR.pop();
				if (!cal(OPTR_top, tempA, tempB, tempR)) {//tempR计算结果
					return;
				}
				OPND.push(tempR);


			}
			//栈内优先级等于栈外优先级
			else {
				OPTR.pop();
				GetNextChar(ch);
			}
		}
		OPTR_top = OPTR.topValue();
		if (!OPTR_top) { cout << "表达式有错!" << endl;  return; }
	}

	//  输出答案
	if (OPND.length() != 1) {
		cout << "表达式有错!" << endl;  return;
	}
	else {

		cout << "答案是:";
		//Yellow on Black：
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
			FOREGROUND_RED | FOREGROUND_GREEN);//红加绿
		cout << OPND.pop() << endl;
		cout << endl;
		//White on Black：
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
			FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
	}

}
// 测试读取double数据 done
void testRead() {
	getInput();
	char ch;
	GetNextChar(ch);
	cout << readOperand() << endl;
	GetNextChar(ch);
	GetNextChar(ch);
	cout << readOperand() << endl;
}