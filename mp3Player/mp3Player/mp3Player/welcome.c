
#include "playPanel.h"
#include "playFunctions.h"

void welcome() {
	int option = 0;
	struct songList* p,*head;
	char choose[30];
	system("CLS");
	printf("----------welcome----------\n");
	printf("1-从播放列表播放\n");
	printf("2-从歌单播放\n");
	printf("---------------------------\n");
	printf("\n");
	printf("请选择:\n");
	scanf("%d", &option);
	while (option < 1 || option > 2) {
		printf("输入有误,请重新输入:\n");
		scanf("%d", &option);
	}
	if (option == 1) {
		p = BuildPlayList();
		head = BuildPlayList();
	}
	else {
		strcpy(choose,DisplayList());
		p = BuildList(choose);
		head = BuildList(choose);
	}

	system("CLS");
	printf("---------歌曲选择--------\n");
	while (p->next != NULL) {
		printf("%d、%s\n", p->id, p->songName);
		p = p->next;
	}

	printf("---------------------------\n");
	printf("\n");
	if (getLength(head) > 0) {
		printf("请选择:\n");
		scanf("%d", &option);
		while (option < 1 || option >getLength(head)) {
			printf("输入有误,请重新输入:\n");
			scanf("%d", &option);
		}
		Switch(head, option);
	}
	else {
		printf("当前歌单无歌曲\n");
		printf("即将返回...\n");
		Sleep(1000);
		welcome();
	}

}