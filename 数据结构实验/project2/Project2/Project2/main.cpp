#include"basic.h"

int main() {
	printIntroduction();
	int option;
	do {
		cout << "输入您的选择：";
		scanf_s("%d", &option);
		switch (option) {
		case 1:
			char_count();
			break;
		case 2:
			file_code();
			break;
		case 3:
			file_decode();
			break;
		case 4:
			break;
		default:
			break;
			printf("输入错误，请");
		}
	} while (option != 4);
	printf("程序已结束\n");
	printf("感谢使用该压缩程序\n");
}