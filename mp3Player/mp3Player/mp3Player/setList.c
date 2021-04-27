#include "playPanel.h"
#include "playFunctions.h"

void setList() {
	int option = 0;
	int option1 = 0;
	char choose[30];
	struct songList* p, * head;
	system("CLS");
	printf("---------歌单设置--------\n");
	printf("1-添加歌单\n");
	printf("2-查看歌单\n");
	printf("\n");
	printf("请选择\n");
	printf("-------------------------\n");
	scanf("%d", &option);
	if (option == 1) {
		BuildNewList();
	}
	else {
		system("CLS");

		printf("------------查看歌单---------\n");
		strcpy(choose, DisplayList());
		system("CLS");
		printf("------------歌单处理----------\n");
		printf("1-删除\n");
		printf("2-播放\n");
		printf("\n");
		printf("请选择\n");
		printf("------------------------------\n");
		scanf("%d", &option1);
		if (option1 == 1) {
			deleteList(choose);
		}
		else {
			p = BuildList(choose);
			head = BuildList(choose);

			system("CLS");
			printf("---------歌曲选择--------\n");
			while (p->next != NULL) {
				printf("%d、%s\n", p->id, p->songName);
				p = p->next;
			}

			printf("---------------------------\n");
			printf("\n");
			printf("请选择:\n");
			scanf("%d", &option);
			while (option < 1 || option >getLength(head)) {
				printf("输入有误,请重新输入:\n");
				scanf("%d", &option);
			}
			Switch(head, option);
		}
	}
}