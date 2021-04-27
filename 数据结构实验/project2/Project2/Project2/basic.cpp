#include"basic.h"
#include<map>

Buffer buf;
FILE* infp;
FILE* outfp;
char inFile[20]; //input file name
char file_name[20];//file name without ".txt" or "_XXX"
int num, total, bit_num, file_cap;//total:inFile中字符总数
char val[NUMBER];
int weight[NUMBER];
char code[NUMBER][30];
HuffTree<char>* huffmanTree;
char temp_c[30]; //huffmanCode 的临时char数组



template <class E>
E max(E a, E b) {
	return a > b ? a : b;
}

template <class E>
E min(E a, E b) {
	return a < b ? a : b;
}

void printIntroduction() {
	cout << "操作命令说明：" << endl;
	cout << "统计输入文件字符频度并对字符集编括码并输出至文件（基本要求）： 1" << endl;
	cout << "  对整个文件编括码并保存编码结果到一个二进制文件（中级要求）： 2" << endl;
	cout << "            文件解码并将解码结果保存为一文本文件（高级要求）： 3" << endl;
	cout << "                                                   退出 ： 4" << endl;
}

// 获取并返回c在val中的下标
int getIndex(char c) {
	for (int i = 0; i < num; i++) {
		if (val[i] == c) return i;
	}
	return num + 1;
}

// 1：提示用户输入文件名inFile.txt，统计stat，编码字符集并输出（输出到控制台code_print和保存到inFile_count.txt）
void char_count() {
	num = 0;//字符总数

	stat(val, weight, num);
	huffmanTree = HuffmanBuild<char>(val, weight, num);
	HuffmanCode(huffmanTree->root(), temp_c, 0);
	code_print();
}


// 2提示用户输入文件名inFile.txt, 实现步骤1并将原文件压缩至一个二进制文件(inFile_coded.huf);（压缩）
void file_code() {
	char_count();

	infp = fopen(inFile, "r");
	char temp[20];
	strcpy(temp, file_name);
	outfp = fopen(strcat(temp, "_coded.huf"), "wb");

	// 记录num
	fwrite(&num, sizeof(int), 1, outfp);
	// 记录各个char对应的权值
	for (int i = 0; i < num; i++) {
		fwrite(&val[i], sizeof(char), 1, outfp);
		fwrite(&weight[i], sizeof(int), 1, outfp);
	}

	// 计算并记录文件总字符数
	total = 0;
	bit_num = 0;
	for (int i = 0; i < num; i++) {
		total += weight[i];
	}
	fwrite(&total, sizeof(int), 1, outfp);

	// 压缩文件
	int len, index, code_len;
	char* code_ptr, ch;

	while (!feof(infp)) {
		ch = fgetc(infp);
		code_ptr = code[getIndex(ch)];
		code_len = strlen(code_ptr);
		for (int i = 0; i < code_len; i++) {
			if (code_ptr[i] == '1') Write(1);
			else Write(0);
		}
	}
	if (buf.bits > 0) WriteToOutfp();

	fclose(infp);
	fclose(outfp);

	file_cap = sizeof(int) / sizeof(char) * (1 + num) + num + bit_num;

	// 打印压缩率
	printf("经过Huffman Code 压缩之后的文件为：%s\n", temp);
	printf("文本压缩率为: %0.2f %%\n", 100.0 * bit_num / total);
	printf("文件压缩率为: %0.2f %%\n", 100.0 * file_cap / total);
}

// 3提示用户输入待解码的文件名(inFile_coded.huf),读取inFile_count.txt, 解码并将结果输出到inFile_decoded.txt。（解压）
void file_decode() {
	// 文件名输入
	printf("输入要解压的文件：");
	scanf(" %[^\n]", inFile);
	infp = fopen(inFile, "rb");
	if (infp == NULL) {
		printf("无法打开该文件\n");
		return;
	}

	char temp[20];
	strcpy(temp, inFile);
	char* infile = strtok(temp, "._");
	strcpy(file_name, infile);

	// 建树

	strcpy(temp, file_name);
	outfp = fopen(strcat(temp, "_decoded.txt"), "w");

	fread(&num, sizeof(int), 1, infp);
	for (int i = 0; i < num; i++) {
		fread(&val[i], sizeof(char), 1, infp);
		fread(&weight[i], sizeof(int), 1, infp);
	}

	huffmanTree = HuffmanBuild<char>(val, weight, num);
	HuffmanCode(huffmanTree->root(), temp_c, 0);
	code_print();

	int total;
	fread(&total, sizeof(int), 1, infp);
	unsigned int direction;
	buf.bits = 0;
	buf.ch = 0;
	for (int i = 0; i < total; i++) {
		HuffNode<char>* currNode = huffmanTree->root();
		while (!currNode->isLeaf()) {
			Read(direction);
			if (direction) currNode = ((IntlNode<char>*)currNode)->right();
			else           currNode = ((IntlNode<char>*)currNode)->left();
		}
		fputc(((LeafNode<char>*)currNode)->val()->val(), outfp);
	}

	fclose(infp);
	fclose(outfp);
	printf("经过%s解压之后的文件为：%s\n\n", inFile, temp);
}

void  code_print() {
	printf("%5s%5s %-6s\n", "字符", "频度", "编码");
	//输出到控制台
	for (int i = 0; i < num; i++) {
		if (val[i] == '\n') printf("%5s%5d  %-10s\n", "\\n", weight[i], code[i]);
		else              printf("%5c%5d  %-10s\n", val[i], weight[i], code[i]);
	}

	char temp[20];
	strcpy(temp, file_name);
	FILE* fp = fopen(strcat(temp, "_count.txt"), "w");

	// 输出到文件
	if (fp == NULL)
	{
		printf("open error!\n");
	}
	else {
		fprintf(fp, "%5s%5s  %-6s\n", "字符", "频度", "编码");
		for (int i = 0; i < num; i++) {
			if (val[i] == '\n') fprintf(fp, "%5s%5d  %-10s\n", "\\n", weight[i], code[i]);
			else     			fprintf(fp, "%5c%5d  %-10s\n", val[i], weight[i], code[i]);
		}
		fclose(fp);
	}
}

template <class Elem>
HuffTree<Elem>* HuffmanBuild(char* s, int* w, int num)
{
	HuffTree<Elem>* ttree[NUMBER];
	HuffTree<Elem>* temp;
	for (int i = 0; i < num; i++) {
		ttree[i] = new HuffTree<Elem>(s[i], w[i]);
	}

	int cnt = num;
	while (cnt != 1) {
		int s1 = 0;
		int s2 = 1;
		for (int i = 0; i < cnt; i++) {
			if (ttree[i]->weight() < ttree[s1]->weight())
				s1 = i;
		}
		if (s1 == 1) s2 = 0;
		for (int i = 0; i < cnt; i++) {
			if (ttree[i]->weight() < ttree[s2]->weight() && i != s1)
				s2 = i;
		}
		temp = new HuffTree<Elem>(ttree[s1], ttree[s2]);
		ttree[(min(s1, s2))] = temp;
		//从ttree中删除下标为max(s1, s2)的元素
		cnt--;
		for (int i = max(s1, s2); i < cnt; i++) {
			ttree[i] = ttree[i + 1];
		}
	}
	return ttree[0];
}

void  HuffmanCode(HuffNode<char>* ht1, char* Code, int length) {
	// length 表明当前要处理的code位
	if (ht1 == NULL) {
		return;
	}
	if (ht1->isLeaf())
	{
		// Code结束增长
		Code[length] = '\0';
		int index = getIndex(((LeafNode<char>*)ht1)->LeafNode::val()->FreqPair::val());
		strcpy(code[index], Code);//保存相关属性
		return;
	}
	else {
		//left children：0
		Code[length] = '0';
		HuffmanCode(((IntlNode<char>*)ht1)->left(), Code, length + 1);//递归调用

		//right children：1
		Code[length] = '1';
		HuffmanCode(((IntlNode<char>*)ht1)->right(), Code, length + 1);
	}

}

//读入文本文件, 并统计频度不为零的字符集s（val），相应频度集 w（weight），及个数num
void stat(char* s, int* w, int& num) {

	// 输入文件名
	printf("输入要编码的文件：");
	scanf(" %[^\n]", inFile);

	// 获取file_name
	char temp[20];
	strcpy(temp, inFile);
	char* infile = strtok(temp, ".");
	strcpy(file_name, infile);

	//打开文件
	infp = fopen(inFile, "r");
	if (infp == NULL) {
		printf("找不到该文件，请重新");
		stat(s, w, num);
		return;
	}

	char ch;
	int len;
	std::map<char, int> map;
	std::map<char, int>::iterator it;

	while (!feof(infp)) {
		ch = fgetc(infp);
		// fgetc读完全部字符之后  然后读取一个值为-1的字符
		if (ch < 0) break;
		it = map.find(ch);
		if (it != map.end()) {
			// 找到：it指向目标键值对，it->second++实现累加
			it->second++;
		}
		else {
			//it 指向map.end()
			// 如果不用make_pair的话  会报错
			map.insert(std::make_pair(ch, 1));
		}
	}

	// 统计结束
	num = map.size();
	int i;
	for (i = 0, it = map.begin(); it != map.end(); it++, i++) {
		s[i] = it->first;
		w[i] = it->second;
	}

}



void Write(unsigned int bit) {
	buf.bits++;
	buf.ch = (buf.ch << 1) + bit;
	if (buf.bits == 8) {
		//缓冲区已满,写入outfp
		fputc(buf.ch, outfp);
		buf.bits = 0;
		buf.ch = 0;
		bit_num++;
	}
}

void   WriteToOutfp() {
	unsigned int l = buf.bits;
	if (l > 0)
		for (unsigned int i = 0; i < 8 - l; i++)  Write(0);
}

void  Read(unsigned int& bit) {
	if (buf.bits == 0) {
		buf.ch = fgetc(infp);
		buf.bits = 8;
	}
	//用位运算取buf的最高位直至buf.bits == 0
	bit = (buf.ch & 128) >> 7;
	buf.ch = buf.ch << 1;
	buf.bits--;
}