
#include "playPanel.h"
#include "playFunctions.h"

int playList(struct songList* head) {
	system("CLS");
	struct songList* p;
	p = head;
	int option = 0;
	printf("---------�����б�----------\n");
	while (p->next != NULL) {
		printf("%d��%s\n", p->id, p->songName);
		p = p->next;
	}
	printf("---------------------------\n");
	printf("\n");
	printf("��ѡ���벥�ŵĸ���:\n");
	scanf("%d", &option);
	while (option < 1 || option >getLength(head)) {
		printf("��������,����������:\n");
		scanf("%d", &option);
	}
	Switch(head, option);
}