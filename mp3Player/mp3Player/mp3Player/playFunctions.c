#define _CRT_SECURE_NO_WARNINGS

#include<stdlib.h>
#include<windows.h>
#include<mmsystem.h>
#include"playFunctions.h"
#pragma comment(lib, "winmm.lib")

songList* SearchPos(char songName[], songList* head){
	songList* SearchNode;
	songList* ResultNode = NULL;
	SearchNode = head;
	while (SearchNode->next != NULL)
	{
		if (strcmp(songName, SearchNode->songName) == 0)
		{
			ResultNode = SearchNode;
			break;
		}
		SearchNode = SearchNode->next;
	}
	return ResultNode;

}
//��4���������������ʹ�����·���������·��������Ҫ��MP3�ļ���c�ļ�����һ���ļ�����
void play(char SongName[]) {
	char openStr[80] = ""; 
	strcat(openStr, "open ");
	strcat(openStr, SongName);
	char playStr[80] = "";
	strcat(playStr, "play ");
	strcat(playStr, SongName);
	strcat(playStr, " repeat");
	mciSendString(openStr,NULL, 0, NULL);
	mciSendString(playStr,NULL, 0, NULL);
}
void stop(char SongName[]) {
	char stopStr[80] = "";
	strcat(stopStr, "stop ");
	strcat(stopStr, SongName);
	char closeStr[80] = "";
	strcat(closeStr, "close ");
	strcat(closeStr, SongName);
	mciSendString(stopStr, NULL, 0, NULL);
	mciSendString(closeStr, NULL, 0, NULL);
}
void pause(char SongName[]) {
	static int state = 0;
	char pauseStr[80] = "";
	strcat(pauseStr, "pause ");
	strcat(pauseStr, SongName);
	mciSendString(pauseStr, NULL, 0, NULL);
	
}
void resume(char SongName[]){
	static int state = 0;
	char resumeStr[80] = "";
	strcat(resumeStr, "resume ");
	strcat(resumeStr, SongName);
	mciSendString(resumeStr, NULL, 0, NULL);

}
//����һ��������赥ͷ�ڵ��Ŀǰ���ڲ��ŵ�������
void next(songList* head, char songName[]) {
	songList* source;
	source = SearchPos(songName, head);
	source = source->next;
	if (source->next != NULL)
	{
		stop(songName);
		play(source->songName);
	}
	else
	{
		source = head;
		stop(songName);
		play(source->next->songName);
	}

}
void last(songList* head, char songName[]) {
	songList* source;
	source = SearchPos(songName, head);
	source = source->last;
	if (source != NULL)
	{
		stop(songName);
		play(source->songName);
	}
	else
	{
		source = head;
		stop(songName);
		while (source->next != NULL) {
			source = source->next;
		}
		play(source->last->songName);
	}
}


//��txt�ļ��ж�ȡ��������Ŀǰ��Ĭ�ϸ赥���޴��������ʵ����Ӹ赥ʱ����Ҫ�����
songList* BuildList(const char listName[]) {
	songList* head = (songList*)malloc(sizeof(songList));
	head->last = NULL;
	head->next = NULL;
	songList* temp = NULL;
	FILE* p;
	if ((p = fopen(listName, "r")) == NULL)
	{
		printf("δ���ļ�\n");
		return NULL;
	}
	else
	{
		fscanf(p, "%s", head->songName);
		head->id = 1;
		temp = head;
		int i = 2;
		while (!feof(p))
		{
			songList* element = (songList*)malloc(sizeof(songList));
			fscanf(p, "%s", element->songName);
			element->id = i;
			i++;
			temp->next = element;
			element->last = temp;
			element->next = NULL;
			temp = element;
		}
		temp->next = NULL;
		return head;
		free(head);
		fclose(p);
	}
}

void addVolume(songList* head, char songName[]) {
	char path[100];
	strcpy(path,"C:\\Users\\25409\\Desktop\\mp3Player\\mp3Player\\mp3Player\\");
		
	TCHAR volume[256];
	TCHAR changeVolume[256];
	int nVolume;
	strcat(path, songName);
	wsprintf(changeVolume, "status %s volume", path);
	mciSendString(changeVolume, volume, sizeof(volume), 0);// ��ȡ��ǰ������ volume �ַ�����
	
	nVolume = atoi(volume); // �ַ���ת��Ϊ����

	

	wsprintf(changeVolume, "setaudio %s volume", path, nVolume + 200);
	mciSendString(changeVolume, "", 0, NULL);


	//wsprintf(changeVolume, "status %s volume", path);
	//mciSendString(changeVolume, volume, sizeof(volume), 0);
	//mciSendString("status F:\\study\\grade1practice\\ezuoju.mp3 volume",volume,sizeof(volume),0);
	printf("��ǰ���� %d\n", nVolume);
}


void reduceVolume(songList* head, char songName[]) {
	char path[100];
	strcpy(path, "C:\\Users\\25409\\Desktop\\mp3Player\\mp3Player\\mp3Player\\");
	char volume[256];
	char changeVolume[256];
	int nVolume;
	strcat(path, songName);
	
	wsprintf(changeVolume, "status %s volume", path);
	mciSendString(changeVolume, volume, sizeof(volume), 0);
	nVolume = atoi(volume); 
	nVolume -= 200;


	wsprintf(changeVolume, "setaudio %s volume to %d", path, nVolume);
    mciSendString(changeVolume, "", 0, NULL);


	wsprintf(changeVolume, "status %s volume", path);
	mciSendString(changeVolume, volume, sizeof(volume), 0);
	printf("��ǰ���� %s\n", volume);
	
}

float locateTime(songList* head, char songName[]) {
	char path[100] =  "C:\\Users\\25409\\Desktop\\mp3Player\\mp3Player\\mp3Player\\" ;
	char cmd[256];
	char position[50];
	float time;
	songList* source;
	source = SearchPos(songName, head);
	strcat(path, source->songName);
	wsprintf(cmd, "status %s position", path);
	mciSendString(cmd, position, sizeof(position), 0);
	time = atoi(position);
	return time;
}

float allTime(songList* head, char songName[]) {
	char path[100] = "C:\\Users\\25409\\Desktop\\mp3Player\\mp3Player\\mp3Player\\" ;
	char cmd[256];
	char length[50];
	float time;
	songList* source;
	source = SearchPos(songName, head);
	strcat(path, source->songName);
	wsprintf(cmd, "status %s length", path);
	mciSendString(cmd, length, sizeof(length), 0);
	time = atoi(length);
	return time;


}

songList* BuildPlayList() {
	songList* head = (songList*)malloc(sizeof(songList));
	head->last = NULL;
	head->next = NULL;
	songList* temp = NULL;
	FILE* p;
	if ((p = fopen("All.txt", "r")) == NULL)
	{
		printf("δ�ɹ����ļ�\n");
		return NULL;
	}
	else
	{
		fscanf(p, "%s\n", head->songName);
		head->id = 1;
		temp = head;
		int i = 2;
		while (!feof(p))
		{
			songList* element = (songList*)malloc(sizeof(songList));
			fscanf(p, "%s", element->songName);
			element->id = i;
			i++;
			temp->next = element;
			element->last = temp;
			element->next = NULL;
			temp = element;
		}
		temp->next = NULL;
		return head;
		free(head);
		fclose(p);
	}
}