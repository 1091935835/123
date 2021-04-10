#include "playPanel.h"
#include "playFunctions.h"

char* showCloseTime(int minute, int allTime) {
    char show[80];
    
    int time;
    time = minute - allTime;
    if (minute>-1) {
        sprintf(show, "据关闭播放器还剩%d分%d秒", time/60, time%60);
    }
    else {
        strcpy(show, "未开启定时关闭功能");
    }

    return show;
}