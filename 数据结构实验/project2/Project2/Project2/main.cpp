#include"basic.h"

int main() {
	printIntroduction();
	int option;
	do {
		cout << "��������ѡ��";
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
			printf("���������");
		}
	} while (option != 4);
	printf("�����ѽ���\n");
	printf("��лʹ�ø�ѹ������\n");
}