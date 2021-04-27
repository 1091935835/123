#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

void List() {
	FILE* fp = fopen(".\\SongInfo.txt", "r");

	int cnt = 1;

	if (fp) {
		char s1[100], s2[100], s3[100];
		while (!feof(fp)){
			fscanf(fp, "%s %s %s", &s1, &s2, &s3);

			printf("%d: %s   %s   %s\n", cnt, s1, s2, s3);

			cnt++;
		}

		int temp;
		while (1) {
			printf("输入0退出\n");
			scanf("%d", &temp);
			if (temp == 0) {
				break;
			}
		}
	}
	else {
		printf("打开歌单文件失败");
	}
}