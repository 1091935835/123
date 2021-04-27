#include "playPanel.h"
#include "playFunctions.h"

int loadListFile();

char* BuildNewList() {
	FILE* p;
	char temp[30];
	char address[40];
	printf("�������¸赥���ƣ�\n");
	scanf("%s", temp);
	strcat(temp, ".txt");
	if ((p = fopen(temp,"w"))==NULL)
	{
		printf("����ʧ��\n");
		return NULL;

	}
	else
	{
		printf("�Ѵ����赥��%s\n",temp );
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
	printf("��ӳɹ�!\n");
	Sleep(1000);
}

void deleteList(char ListName[]) {
	remove(ListName);
	loadListFile();
}


//����赥��Ϣ����һ��
int loadListFile()
{
	char catalog[150] = "C:\\Users\\25409\\Desktop\\mp3Player\\mp3Player\\mp3Player";
	char type[20] = "txt";  //�ļ�����

	FILE* fp;
	long handle;    //���
	struct _finddata_t fileinfo;   //�ļ���Ϣ�ṹ��

	char save[150];  //�����ļ���ַ 
	strcpy(save, catalog);
	strcat(save, "\\\\ListTotal.dat"); 

	char search[150];  
	strcpy(search, catalog);
	strcat(search, "\\\\*.");
	strcat(search, type);  

	fp = fopen(save, "w+");  // ���ļ� 
	if (fp == 0)
	{
		printf("�ļ���ʧ�ܣ�\n");
		return 0;
	}

	handle = _findfirst(search, &fileinfo);    //��һ�β���
	if (-1 == handle)
	{
		fclose(fp);
		printf("δ�ҵ������ļ���\n");
		return -1;
	}

	fprintf(fp, "%s\n", fileinfo.name);   //��ӡ���ҵ����ļ����ļ���
	while (!_findnext(handle, &fileinfo)) //ѭ�������������ϵ��ļ���ֱ���Ҳ���������Ϊֹ
	{
		fprintf(fp, "%s\n", fileinfo.name);
	}
	_findclose(handle); //�رվ��

	fclose(fp);
	return 0;

}

//չʾ�赥��
char* DisplayList() {
	system("CLS");
	FILE* fp;
	int Arraynum = 0;
	char base[50][50];
	char choose[30];
	int option = -1;
	loadListFile();
	printf("------------�赥�б�-------------\n");
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
	printf("��ѡ��Ҫ����ĸ赥:\n");
	scanf("%d", &option);
	while (option < 0) {
		printf("�����������������:\n");
		scanf("%d", &option);
	}
	strcpy(choose, base[option-1]);
	return choose;

}
