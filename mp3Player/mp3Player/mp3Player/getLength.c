
#include "playPanel.h"
#include "playFunctions.h"

int getLength(struct songList* head) {
	struct songList* p;
	p = head;
	int length = 0;
	while (p->next != NULL) {
		length = p->id;
		p = p->next;
	}
	return length;

}