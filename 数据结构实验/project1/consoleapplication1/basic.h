#pragma once
#include "LStack.h"
#include<stdio.h>
#include<math.h>
#include<Windows.h>
#include <iostream>
int isp(char ch);    //��ȡ�����ز����� ch ��ջ�����ȼ�
int osp(char ch);   // ��ȡ�����ز����� ch ��ջ�����ȼ�
int  cal(char op, double x, double y, double& r);   // ����r = x op y�� ����ɹ�������true.
void GetNextChar(char& ch);   // ������ı��ʽ�л�ȡһ���ַ�
int inputEnd(); //�ж������Ƿ��Ѿ���ȡ���
int isDigit(char ch);     // �ж�ch�Ƿ�Ϊ����0-9
int IsOperator(char ch);     //�ж�ch�Ƿ�Ϊ������
double readOperand(); //��input��currentλ��ʼ��ȡһ����������.
int Get2Operands(LStack<double>& opnd, double& x, double& y);   //�Ӳ�����ջ��ȡ2��������
void printWrongInput(char* wrongMessagge); //��ӡ������Ϣ
int isLegalInput(char* input);  //�ж�����Ƿ�Ϸ����Ϸ�����1���Ƿ������printWrongInput������ӡ������Ϣ��������0
void printIntroduction(); //��ӡ��ʾ��Ϣ
void getInput(); //����������ʽ