#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <io.h>

// ʹ��ǰ��Ҫ�޸� catalog�� type
char catalog[150] = "C:\\Users\\10919\\Desktop\\mp3Player\\mp3Player\\mp3Player";  
char type[20] = "mp3";  //�ļ�����


 int loadFile()
{
	FILE* fp;
	long handle;    //���
	struct _finddata_t fileinfo;   //�ļ���Ϣ�ṹ��

	char save[150];  //�����ļ���ַ 
	strcpy(save, catalog);
	strcat(save, "\\\\ALL.txt");

	char search[150];  ////�����ҵ��ļ���֧��ͨ���
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
