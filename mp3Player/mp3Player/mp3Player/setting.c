
#include "playPanel.h"
#include "playFunctions.h"

int setting() {
	system("CLS");
	int option =0;
	printf("------------����-----------\n");
	printf("1-��ʱ�ر�\n");
	printf("2-�赥����\n");
	printf("\n");
	printf("---------------------------\n");
	printf("��ѡ��:\n");
	scanf("%d", &option);
	if (option == 1) {
	 return setTimeClose();
	}
	else {
		setList();
		return -1;
	}
}