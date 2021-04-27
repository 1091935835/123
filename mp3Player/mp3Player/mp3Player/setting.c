
#include "playPanel.h"
#include "playFunctions.h"

int setting() {
	system("CLS");
	int option =0;
	printf("------------设置-----------\n");
	printf("1-定时关闭\n");
	printf("2-歌单设置\n");
	printf("\n");
	printf("---------------------------\n");
	printf("请选择:\n");
	scanf("%d", &option);
	if (option == 1) {
	 return setTimeClose();
	}
	else {
		setList();
		return -1;
	}
}