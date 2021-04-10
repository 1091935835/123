
#include "playPanel.h"
#include "playFunctions.h"

int setTimeClose() {
	int ifOpen = 0;
	int minute = 0;
	system("CLS");
	printf("------------定时关闭-----------\n");
	printf("0-关闭定时关闭\n");
	printf("1-开启定时关闭\n"); printf("\n");
	printf("---------------------------\n");
	printf("请选择:\n");
	scanf("%d", &ifOpen);
	if (ifOpen == 1) {
		printf("请输入定时关闭的时长(分),若想返回，请输入-1:\n");
		scanf("%d", &minute);
		while (minute < -1||minute ==0)
		{
			printf("输入错误，请重新输入:\n");
		}
	}
	return minute;
}