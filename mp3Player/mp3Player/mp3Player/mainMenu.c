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
	printf("-----------主菜单--------\n");
	printf("1----播放(暂停)歌曲\n");
	printf("2----上一首\n");
	printf("3----下一首\n");
	printf("4----调高音量\n");
	printf("5----降低音量\n");
	printf("6----添加至歌单\n");
	printf("7----设置\n");
	printf("8----删除\n");
	printf("9----播放列表\n");
	printf("0----退出\n");
	printf("------------------------\n");
	if (indexPlay == 0) {
		printf("已暂停  %s\n", songName);
	}
	else {
		printf("正在播放  %s\n", songName);
	}
	printf("%d:%d / %d:%d\n", second1 / 60, (int)second1 % 60, second2 / 60, (int)second2 % 60);
	printf("\n");
	puts(countName);
	printf("------------------------\n");
	printf("请选择:\n");
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
					pause(SongName);//第一次暂停，第二次恢复
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
				//调高音量
				addVolume(head, SongName);
				break;
			case 5:
				//降低音量
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
				printf("无此选项，请重新输入\n");
				break;
			}
		}
		else {
			if (minute > 0) {
				end = time(NULL);
				if ((end - start) == minute) {
					system("CLS");
					printf("即将关闭程序...\n");
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
