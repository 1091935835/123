
#include "playPanel.h"
#include "playFunctions.h"


void del(struct songList* head) {
	struct songList* p, * temp;
	int id = 0;
	p = head;
	system("CLS");
	printf("------------ɾ��----------\n");
	if (getLength(p) > 1) {
		while (p->next != NULL) {
			printf("%d��%s\n", p->id, p->songName);
			p = p->next;
		}
		printf("---------------------------\n");
		printf("\n");
		printf("��������Ҫɾ�����������:\n");
		scanf("%d", &id);

		p = head;
		while (p->next != NULL) {
			temp = p;
			p = p->next;

			if (p->id == id && (id != getLength(head))) {
				temp->next = p->next;
				free(p);
				p = temp->next;
				break;
			}
			if (p->id == id && (id == getLength(head))) {
				temp->next = NULL;
				free(p);
				p = temp->next;
				break;
			}
		}


		p = head;
		id = 1;
		while (p->next != NULL) {
			p->next->id = id;
			id++;
			p = p->next;
		}
		system("CLS");
		printf("ɾ���ɹ�!�����򿪲����б�....\n");
		Sleep(3000);
		playList(head);
	}
	else {
		printf("�б������Ŀ���㣬�޷�ɾ��....\n");
		Sleep(2000);
		playList(head);
	}
}