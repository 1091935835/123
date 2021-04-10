
#include "playPanel.h"
#include "playFunctions.h"

int playList(struct songList* head) {
	system("CLS");
	struct songList* p;
	p = head;
	int option = 0;
	printf("---------播放列表----------\n");
	while (p->next != NULL) {
		printf("%d、%s\n", p->id, p->songName);
		p = p->next;
	}
	printf("---------------------------\n");
	printf("\n");
	printf("请选择想播放的歌曲:\n");
	scanf("%d", &option);
	while (option < 1 || option >getLength(head)) {
		printf("输入有误,请重新输入:\n");
		scanf("%d", &option);
	}
	Switch(head, option);
}