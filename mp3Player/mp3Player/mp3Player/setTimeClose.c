
#include "playPanel.h"
#include "playFunctions.h"

int setTimeClose() {
	int ifOpen = 0;
	int minute = 0;
	system("CLS");
	printf("------------��ʱ�ر�-----------\n");
	printf("0-�رն�ʱ�ر�\n");
	printf("1-������ʱ�ر�\n"); printf("\n");
	printf("---------------------------\n");
	printf("��ѡ��:\n");
	scanf("%d", &ifOpen);
	if (ifOpen == 1) {
		printf("�����붨ʱ�رյ�ʱ��(��),���뷵�أ�������-1:\n");
		scanf("%d", &minute);
		while (minute < -1||minute ==0)
		{
			printf("�����������������:\n");
		}
	}
	return minute;
}