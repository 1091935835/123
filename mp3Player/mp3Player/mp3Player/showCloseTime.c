#include "playPanel.h"
#include "playFunctions.h"

char* showCloseTime(int minute, int allTime) {
    char show[80];
    
    int time;
    time = minute - allTime;
    if (minute>-1) {
        sprintf(show, "�ݹرղ�������ʣ%d��%d��", time/60, time%60);
    }
    else {
        strcpy(show, "δ������ʱ�رչ���");
    }

    return show;
}