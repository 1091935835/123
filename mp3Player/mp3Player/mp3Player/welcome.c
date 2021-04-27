
#include "playPanel.h"
#include "playFunctions.h"

void welcome() {
	int option = 0;
	struct songList* p,*head;
	char choose[30];
	system("CLS");
	printf("----------welcome----------\n");
	printf("1-�Ӳ����б���\n");
	printf("2-�Ӹ赥����\n");
	printf("---------------------------\n");
	printf("\n");
	printf("��ѡ��:\n");
	scanf("%d", &option);
	while (option < 1 || option > 2) {
		printf("��������,����������:\n");
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
	printf("---------����ѡ��--------\n");
	while (p->next != NULL) {
		printf("%d��%s\n", p->id, p->songName);
		p = p->next;
	}

	printf("---------------------------\n");
	printf("\n");
	if (getLength(head) > 0) {
		printf("��ѡ��:\n");
		scanf("%d", &option);
		while (option < 1 || option >getLength(head)) {
			printf("��������,����������:\n");
			scanf("%d", &option);
		}
		Switch(head, option);
	}
	else {
		printf("��ǰ�赥�޸���\n");
		printf("��������...\n");
		Sleep(1000);
		welcome();
	}

}