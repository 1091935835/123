#pragma once
#include"huff.h"
#define NUMBER 256

struct Buffer {                 //×Ö½Ú»º³å       
	char ch;
	unsigned int bits;
};



void printIntroduction();

void char_count();

void file_code();

void file_decode();

void  code_print();

void stat(char* s, int* w, int& num);

template <class Elem>
HuffTree<Elem>* HuffmanBuild(char* s, int* w, int num);

void  HuffmanCode(HuffNode<char>* ht1, char* Code, int length);

void stat(char* s, int* w, int& type_num);

void Write(unsigned int bit);

void WriteToOutfp();

void Read(unsigned int& bit);
