#pragma once
#include "LStack.h"
#include<stdio.h>
#include<math.h>
#include<Windows.h>
#include <iostream>
int isp(char ch);    //获取并返回操作符 ch 的栈内优先级
int osp(char ch);   // 获取并返回操作符 ch 的栈外优先级
int  cal(char op, double x, double y, double& r);   // 计算r = x op y， 计算成功，返回true.
void GetNextChar(char& ch);   // 从输入的表达式中获取一个字符
int inputEnd(); //判断输入是否已经读取完毕
int isDigit(char ch);     // 判断ch是否为数字0-9
int IsOperator(char ch);     //判断ch是否为操作符
double readOperand(); //从input的current位开始读取一个数并返回.
int Get2Operands(LStack<double>& opnd, double& x, double& y);   //从操作数栈中取2个操作数
void printWrongInput(char* wrongMessagge); //打印错误信息
int isLegalInput(char* input);  //判断输出是否合法。合法返回1；非法则调用printWrongInput方法打印错误信息，并返回0
void printIntroduction(); //打印提示信息
void getInput(); //请求输入表达式