#include "playPanel.h"
#include "playFunctions.h"

void addToList(char songName[]) {
	system("CLS");
	int option = 0;
	char choose[30];

	strcpy(choose,DisplayList());
	addSong(choose,songName);
}