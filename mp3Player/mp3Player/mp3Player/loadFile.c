#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <io.h>

// 使用前需要修改 catalog， type
char catalog[150] = "C:\\Users\\10919\\Desktop\\mp3Player\\mp3Player\\mp3Player";  
char type[20] = "mp3";  //文件类型


 int loadFile()
{
	FILE* fp;
	long handle;    //句柄
	struct _finddata_t fileinfo;   //文件信息结构体

	char save[150];  //保存文件地址 
	strcpy(save, catalog);
	strcat(save, "\\\\ALL.txt");

	char search[150];  ////欲查找的文件，支持通配符
	strcpy(search, catalog);
	strcat(search, "\\\\*.");
	strcat(search, type);  

	fp = fopen(save, "w+");  // 打开文件 
	if (fp == 0)
	{
		printf("文件打开失败！\n");
		return 0;
	}

	handle = _findfirst(search, &fileinfo);    //第一次查找
	if (-1 == handle)
	{
		fclose(fp);
		printf("未找到所需文件。\n");
		return -1;
	}

	fprintf(fp, "%s\n", fileinfo.name);   //打印出找到的文件的文件名
	while (!_findnext(handle, &fileinfo)) //循环查找其他符合的文件，直到找不到其他的为止
	{
		fprintf(fp, "%s\n", fileinfo.name);
	}
	_findclose(handle); //关闭句柄

	fclose(fp);
	return 0;

}
