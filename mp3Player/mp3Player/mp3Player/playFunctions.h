
#pragma once
#define _CRT_SECURE_NO_WARNINGS
#include<stdio.h>
#include<windows.h>
#include<time.h>
#include <io.h>

typedef struct songList {
	char songName[60];
	int id;
	struct songList* next;
	struct songList* last;

}songList;
void addSong(char listName[],char songName[]);
void addToList(char songName[]);
char* BuildNewList();
void deleteList(char ListName[]);
char* DisplayList();
char* getSongName(struct songList* head, int id);
songList* SearchPos(char songName[], songList* head);
void stop(char SongName[]);
void play(char songName[]);
void pause(char songName[]);
void resume(char SongName[]);
void next(songList* head, char songName[]);
void last(songList* head, char songName[]);
songList* BuildPlayList();
songList* BuildList(const char listName[]);
void del(struct songList* head);
char* showCloseTime(int minute, int second);
int setting();
int playList(struct songList* head);
void addVolume(songList* head, char songName[]);
void reduceVolume(songList* head, char songName[]);
float locateTime(songList* head, char songName[]);
float allTime(songList* head, char songName[]);