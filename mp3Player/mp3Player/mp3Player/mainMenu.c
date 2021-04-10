#include "playPanel.h"
#include "playFunctions.h"


void mainMenu(int indexPlay, struct songList* head, int id, int minute, int second) {
	int songId = id;
	char songName[30];
	char countName[80];
	char showT[30];
	int second1 = second;
	int second2 = 0;
	int option = -1;
	strcpy(songName, getSongName(head, songId));
	second1 = (int)locateTime(head, songName) / 1000;
	second2 = (int)allTime(head, songName) / 1000;
	strcpy(countName, showCloseTime(minute, second));
	system("CLS");
	printf("-----------���˵�--------\n");
	printf("1----����(��ͣ)����\n");
	printf("2----��һ��\n");
	printf("3----��һ��\n");
	printf("4----��������\n");
	printf("5----��������\n");
	printf("6----������赥\n");
	printf("7----����\n");
	printf("8----ɾ��\n");
	printf("9----�����б�\n");
	printf("0----�˳�\n");
	printf("------------------------\n");
	if (indexPlay == 0) {
		printf("����ͣ  %s\n", songName);
	}
	else {
		printf("���ڲ���  %s\n", songName);
	}
	printf("%d:%d / %d:%d\n", second1 / 60, (int)second1 % 60, second2 / 60, (int)second2 % 60);
	printf("\n");
	puts(countName);
	printf("------------------------\n");
	printf("��ѡ��:\n");
}

void Switch(struct songList* head, int id) {
	time_t start = time(NULL);
	time_t end = time(NULL);
	int songId = id;
	int minute = -1;
	int length = getLength(head);
	char SongName[60];
	int option = 1;
	int pauseState = 1;


	strcpy(SongName, getSongName(head, id));
	play(SongName);

	while (option != 0)
	{
		end = time(NULL);
		strcpy(SongName, getSongName(head, songId));
		mainMenu(pauseState, head, songId, minute, (end - start));

		if (_kbhit()) {
			scanf("%d", &option);

			switch (option) {
			case 1:
				if (pauseState == 1)
				{
					pause(SongName);//��һ����ͣ���ڶ��λָ�
				}
				else if (pauseState == 0)
				{
					resume(SongName);
				}
				pauseState = (pauseState + 1) % 2;
				break;
			case 2:
				last(head, SongName);
				pauseState = 1;
				songId--;
				if (songId == 0) {
					songId = length;
				}
				break;
			case 3:
				next(head, SongName);
				pauseState = 1;
				songId++;
				if (songId == length + 1) {
					songId = 1;
				}
				break;
			case 4:
				//��������
				addVolume(head, SongName);
				break;
			case 5:
				//��������
				reduceVolume(head, SongName);
				break;
			case 6:
				addToList(SongName);
				break;

			case 7:
				pause(SongName);
				minute = setting();
				if (minute > 0) {
					start = time(NULL);
				}
				minute = minute * 60;
				resume(SongName);
				break;

			case 8:
				option = 0;
				stop(SongName);
				del(head);
				break;

			case 9:
				option = 0;
				stop(SongName);
				playList(head);
				break;

			case 0:
				exit(1);
				break;

			default:
				printf("�޴�ѡ�����������\n");
				break;
			}
		}
		else {
			if (minute > 0) {
				end = time(NULL);
				if ((end - start) == minute) {
					system("CLS");
					printf("�����رճ���...\n");
					Sleep(2000);
					exit(1);
				}
				else {
					Sleep(1000);
					mainMenu(pauseState, head, songId, minute, (end - start));
				}
			}
		}
	}
}
