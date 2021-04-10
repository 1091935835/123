#include "playPanel.h"
#include "playFunctions.h"

char* getSongName(struct songList* head, int id) {
	struct songList* p;
	p = head;
	char name[30];
	if (p->next == NULL) {
		printf("当前歌单无歌曲\n");
		printf("即将返回...\n");
		Sleep(1000);
		welcome();
	}
	while (p->next != NULL) {
		if (p->id == id) {
			strcpy(name, p->songName);
			break;
		}
		else {
			p = p->next;
		}
	}
	return name;
}