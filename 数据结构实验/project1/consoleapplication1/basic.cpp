#include"basic.h"
#include <sstream>
using namespace std;


LStack<double> OPND;
LStack<char> OPTR;
char input[50];
char tempStr[40];
int current;

//��ȡ�����ز����� ch ��ջ�����ȼ�
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
	printf("isp:��Ч�ַ�");
}

// ��ȡ�����ز����� ch ��ջ�����ȼ�
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
	printf("osp:��Ч�ַ�");
}

// ����r = x op y�� ����ɹ�������true.
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

// ������ı��ʽ�л�ȡһ���ַ�
void GetNextChar(char& ch){
	ch = input[current++];
}

//�ж������Ƿ��Ѿ���ȡ���
int inputEnd() {
	if (current < strlen(input)) return 1;
	return 0;
}

// �ж�ch�Ƿ�Ϊ����0-9
int isDigit(char ch) {
	if ((ch >= '0') && (ch <= '9'))
	{
		return true;
	}
	return false;
}

//��input��currentλ��ʼ��ȡһ����������.
double readOperand() {
	double operand;
	current--;
	istringstream istr1(input + current);
	istr1 >> operand;
	char ch = input[current];
	// �ƶ�current
	while (isDigit(ch) || ch == '.') ch = input[++current];
	return operand;
}

// �ж�ch�Ƿ�Ϊ������
int IsOperator(char ch) {
	char opt_set[13] = "+-*/%&^()=.";
	for (int i = 0; i < 15; i++) {
		if (ch == opt_set[i])
			return true;
	}
	return false;
}

// �Ӳ�����ջ��ȡ2��������
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

// ��ӡ������Ϣ
void printWrongInput(char* wrongMessagge) {
	printf(wrongMessagge);

}

//�����ʽ���Ƿ��в��Ϸ����ַ�����
int checkChar(char* input)
{

	for (int i = 0; i < strlen(input); i++) {
		if (!IsOperator(input[i]) && !isdigit(input[i])) {
			sprintf(tempStr, "���������%dλ���д���Ĳ������������\n", i + 1);
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
				sprintf(tempStr, "��ı��ʽ��λ���淶��ʹ���˲�����\n");

				return false;
			}
			else if (i == strlen(input) - 1 && input[i] != '=') {
				printf("��ı��ʽĩλ���ǵȺ�\n");
				return false;
			}
			else if (i != 0 && i != strlen(input) - 1)
			{
				if (input[i] == '.')//�ж�С��
				{
					if (!isdigit(input[i - 1]) || !isdigit(input[i + 1]))
					{
						int pos = i + 1;
						printf("��ı��ʽ��%dλ�в��淶��'.'\n", pos);
						return false;
					}
				}
				else if (input[i] == '(')
				{
					if (isdigit(input[i - 1]) || (!isdigit(input[i + 1]) && input[i + 1] != '-' && input[i + 1] != '('))
					{
						int pos = i + 1;
						printf("��ı��ʽ��%dλ�в��淶��'('\n", pos);
						return false;
					}
				}
				else if (input[i] == ')')
				{
					if ((!isdigit(input[i - 1]) && input[i - 1] != ')') || isdigit(input[i + 1]))
					{
						int pos = i + 1;
						printf("��ı��ʽ��%dλ�в��淶��')'\n", pos);
						return false;
					}
				}
				else if (input[i] == '-') {
					if ((!isdigit(input[i - 1]) && (input[i - 1] != ')') && input[i - 1] != '(') || ((input[i + 1] != '(') && !isdigit(input[i + 1]))) {
						printf("��ı��ʽ��%dλ�в��淶�����������������\n", i + 1);
						return false;
					}
				}
				else
				{
					if ((!isdigit(input[i - 1]) && (input[i - 1] != ')')) || ((input[i + 1] != '(') && !isdigit(input[i + 1])))
					{

						printf("��ı��ʽ��%dλ�в��淶�����������������\n", i + 1);
						return false;
					}
				}
			}


		}
	}
	if (left_bra != right_bra)
	{
		printf("������ı��ʽ�У����ŵĶ�����ƥ��\n");
		printf("%d\n", left_bra);
		printf("%d\n", right_bra);
		return false;
	}

	return true;
}

//�ж�����Ƿ�Ϸ����Ϸ�����1���Ƿ������printWrongInput������ӡ������Ϣ��������0
int isLegalInput(char* input) {
	return checkChar(input) && checkOpPosition(input);
}

//��ӡ��ʾ��Ϣ
void printIntroduction() {
	//green on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_GREEN);

	cout << "**********************��ӭʹ�ü�����1.0************************************" << endl;
	//Red on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_RED);

	cout << "ʹ��˵����" << endl;
	cout << "1����������֧�����������(���ź���Ϊ��ӦӦ�����ַ�)" << endl;
	cout << "�ӷ���+��\t������-��\t�˷���*��\t������/��\t�˷���^��\n������&��\t��ģ��%��\t���ţ���������" << endl;
	cout << "2:���������������������Ҫ��Ӣ��ģʽ������" << endl;
	cout << "3:�˳�����������������'&'" << endl;

	//green on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_GREEN);
	cout << "***************************************************************************" << endl;

	//white on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);

}

//����������ʽ
void getInput() {

	puts("������ʽ:");
	//black on white
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), BACKGROUND_INTENSITY |
		FOREGROUND_INTENSITY | BACKGROUND_RED | BACKGROUND_GREEN | BACKGROUND_BLUE);

	scanf(" %[^\n]", input);

	//white on black
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY |
		FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);

	if (input[0] == '&' && input[1] == '&') return;//��������

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
		printf("������");
		getInput();
	}

	current = 0;


}