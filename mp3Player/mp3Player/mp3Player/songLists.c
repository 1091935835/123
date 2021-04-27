#include "playPanel.h"
#include "playFunctions.h"

int loadListFile();

char* BuildNewList() {
	FILE* p;
	char temp[30];
	char address[40];
	printf("请输入新歌单名称：\n");
	scanf("%s", temp);
	strcat(temp, ".txt");
	if ((p = fopen(temp,"w"))==NULL)
	{
		printf("创建失败\n");
		return NULL;

	}
	else
	{
		printf("已创建歌单：%s\n",temp );
		return temp;
	}
	loadListFile();
}

void addSong(char listName[],char songName[]) {
	system("CLS");
	FILE* source;
	int choice = -1;
	
	if ((source = fopen(listName,"a"))==NULL)
	{
		printf("error\n");
	}
	else
	{
		fprintf(source, "%s\n", songName);
	}
	fclose(source);
	printf("添加成功!\n");
	Sleep(1000);
}

void deleteList(char ListName[]) {
	remove(ListName);
	loadListFile();
}


//保存歌单信息在外一层
int loadListFile()
{
	char catalog[150] = "C:\\Users\\25409\\Desktop\\mp3Player\\mp3Player\\mp3Player";
	char type[20] = "txt";  //文件类型

	FILE* fp;
	long handle;    //句柄
	struct _finddata_t fileinfo;   //文件信息结构体

	char save[150];  //保存文件地址 
	strcpy(save, catalog);
	strcat(save, "\\\\ListTotal.dat"); 

	char search[150];  
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

//展示歌单名
char* DisplayList() {
	system("CLS");
	FILE* fp;
	int Arraynum = 0;
	char base[50][50];
	char choose[30];
	int option = -1;
	loadListFile();
	printf("------------歌单列表-------------\n");
	if ((fp=fopen("ListTotal.dat","r"))==NULL)
	{
		printf("error\n");
	}
	else
	{
		while (fscanf(fp, "%s", base[Arraynum]) != EOF)
		while (fscanf(fp, "%s", base[Arraynum]) != EOF)
		{
			printf("%d: ", Arraynum+1);
			printf("%s\n", base[Arraynum]);
			Arraynum++;
		}
	}
	printf("--------------------------------\n");
	printf("\n");
	printf("请选择要处理的歌单:\n");
	scanf("%d", &option);
	while (option < 0) {
		printf("输入错误，请重新输入:\n");
		scanf("%d", &option);
	}
	strcpy(choose, base[option-1]);
	return choose;

}
