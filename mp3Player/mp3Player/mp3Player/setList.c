#include "playPanel.h"
#include "playFunctions.h"

void setList() {
	int option = 0;
	int option1 = 0;
	char choose[30];
	struct songList* p, * head;
	system("CLS");
	printf("---------�赥����--------\n");
	printf("1-��Ӹ赥\n");
	printf("2-�鿴�赥\n");
	printf("\n");
	printf("��ѡ��\n");
	printf("-------------------------\n");
	scanf("%d", &option);
	if (option == 1) {
		BuildNewList();
	}
	else {
		system("CLS");

		printf("------------�鿴�赥---------\n");
		strcpy(choose, DisplayList());
		system("CLS");
		printf("------------�赥����----------\n");
		printf("1-ɾ��\n");
		printf("2-����\n");
		printf("\n");
		printf("��ѡ��\n");
		printf("------------------------------\n");
		scanf("%d", &option1);
		if (option1 == 1) {
			deleteList(choose);
		}
		else {
			p = BuildList(choose);
			head = BuildList(choose);

			system("CLS");
			printf("---------����ѡ��--------\n");
			while (p->next != NULL) {
				printf("%d��%s\n", p->id, p->songName);
				p = p->next;
			}

			printf("---------------------------\n");
			printf("\n");
			printf("��ѡ��:\n");
			scanf("%d", &option);
			while (option < 1 || option >getLength(head)) {
				printf("��������,����������:\n");
				scanf("%d", &option);
			}
			Switch(head, option);
		}
	}
}