#include"basic.h"
#include<map>

Buffer buf;
FILE* infp;
FILE* outfp;
char inFile[20]; //input file name
char file_name[20];//file name without ".txt" or "_XXX"
int num, total, bit_num, file_cap;//total:inFile���ַ�����
char val[NUMBER];
int weight[NUMBER];
char code[NUMBER][30];
HuffTree<char>* huffmanTree;
char temp_c[30]; //huffmanCode ����ʱchar����



template <class E>
E max(E a, E b) {
	return a > b ? a : b;
}

template <class E>
E min(E a, E b) {
	return a < b ? a : b;
}

void printIntroduction() {
	cout << "��������˵����" << endl;
	cout << "ͳ�������ļ��ַ�Ƶ�Ȳ����ַ��������벢������ļ�������Ҫ�󣩣� 1" << endl;
	cout << "  �������ļ������벢�����������һ���������ļ����м�Ҫ�󣩣� 2" << endl;
	cout << "            �ļ����벢������������Ϊһ�ı��ļ����߼�Ҫ�󣩣� 3" << endl;
	cout << "                                                   �˳� �� 4" << endl;
}

// ��ȡ������c��val�е��±�
int getIndex(char c) {
	for (int i = 0; i < num; i++) {
		if (val[i] == c) return i;
	}
	return num + 1;
}

// 1����ʾ�û������ļ���inFile.txt��ͳ��stat�������ַ�������������������̨code_print�ͱ��浽inFile_count.txt��
void char_count() {
	num = 0;//�ַ�����

	stat(val, weight, num);
	huffmanTree = HuffmanBuild<char>(val, weight, num);
	HuffmanCode(huffmanTree->root(), temp_c, 0);
	code_print();
}


// 2��ʾ�û������ļ���inFile.txt, ʵ�ֲ���1����ԭ�ļ�ѹ����һ���������ļ�(inFile_coded.huf);��ѹ����
void file_code() {
	char_count();

	infp = fopen(inFile, "r");
	char temp[20];
	strcpy(temp, file_name);
	outfp = fopen(strcat(temp, "_coded.huf"), "wb");

	// ��¼num
	fwrite(&num, sizeof(int), 1, outfp);
	// ��¼����char��Ӧ��Ȩֵ
	for (int i = 0; i < num; i++) {
		fwrite(&val[i], sizeof(char), 1, outfp);
		fwrite(&weight[i], sizeof(int), 1, outfp);
	}

	// ���㲢��¼�ļ����ַ���
	total = 0;
	bit_num = 0;
	for (int i = 0; i < num; i++) {
		total += weight[i];
	}
	fwrite(&total, sizeof(int), 1, outfp);

	// ѹ���ļ�
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

	// ��ӡѹ����
	printf("����Huffman Code ѹ��֮����ļ�Ϊ��%s\n", temp);
	printf("�ı�ѹ����Ϊ: %0.2f %%\n", 100.0 * bit_num / total);
	printf("�ļ�ѹ����Ϊ: %0.2f %%\n", 100.0 * file_cap / total);
}

// 3��ʾ�û������������ļ���(inFile_coded.huf),��ȡinFile_count.txt, ���벢����������inFile_decoded.txt������ѹ��
void file_decode() {
	// �ļ�������
	printf("����Ҫ��ѹ���ļ���");
	scanf(" %[^\n]", inFile);
	infp = fopen(inFile, "rb");
	if (infp == NULL) {
		printf("�޷��򿪸��ļ�\n");
		return;
	}

	char temp[20];
	strcpy(temp, inFile);
	char* infile = strtok(temp, "._");
	strcpy(file_name, infile);

	// ����

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
	printf("����%s��ѹ֮����ļ�Ϊ��%s\n\n", inFile, temp);
}

void  code_print() {
	printf("%5s%5s %-6s\n", "�ַ�", "Ƶ��", "����");
	//���������̨
	for (int i = 0; i < num; i++) {
		if (val[i] == '\n') printf("%5s%5d  %-10s\n", "\\n", weight[i], code[i]);
		else              printf("%5c%5d  %-10s\n", val[i], weight[i], code[i]);
	}

	char temp[20];
	strcpy(temp, file_name);
	FILE* fp = fopen(strcat(temp, "_count.txt"), "w");

	// ������ļ�
	if (fp == NULL)
	{
		printf("open error!\n");
	}
	else {
		fprintf(fp, "%5s%5s  %-6s\n", "�ַ�", "Ƶ��", "����");
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
		//��ttree��ɾ���±�Ϊmax(s1, s2)��Ԫ��
		cnt--;
		for (int i = max(s1, s2); i < cnt; i++) {
			ttree[i] = ttree[i + 1];
		}
	}
	return ttree[0];
}

void  HuffmanCode(HuffNode<char>* ht1, char* Code, int length) {
	// length ������ǰҪ�����codeλ
	if (ht1 == NULL) {
		return;
	}
	if (ht1->isLeaf())
	{
		// Code��������
		Code[length] = '\0';
		int index = getIndex(((LeafNode<char>*)ht1)->LeafNode::val()->FreqPair::val());
		strcpy(code[index], Code);//�����������
		return;
	}
	else {
		//left children��0
		Code[length] = '0';
		HuffmanCode(((IntlNode<char>*)ht1)->left(), Code, length + 1);//�ݹ����

		//right children��1
		Code[length] = '1';
		HuffmanCode(((IntlNode<char>*)ht1)->right(), Code, length + 1);
	}

}

//�����ı��ļ�, ��ͳ��Ƶ�Ȳ�Ϊ����ַ���s��val������ӦƵ�ȼ� w��weight����������num
void stat(char* s, int* w, int& num) {

	// �����ļ���
	printf("����Ҫ������ļ���");
	scanf(" %[^\n]", inFile);

	// ��ȡfile_name
	char temp[20];
	strcpy(temp, inFile);
	char* infile = strtok(temp, ".");
	strcpy(file_name, infile);

	//���ļ�
	infp = fopen(inFile, "r");
	if (infp == NULL) {
		printf("�Ҳ������ļ���������");
		stat(s, w, num);
		return;
	}

	char ch;
	int len;
	std::map<char, int> map;
	std::map<char, int>::iterator it;

	while (!feof(infp)) {
		ch = fgetc(infp);
		// fgetc����ȫ���ַ�֮��  Ȼ���ȡһ��ֵΪ-1���ַ�
		if (ch < 0) break;
		it = map.find(ch);
		if (it != map.end()) {
			// �ҵ���itָ��Ŀ���ֵ�ԣ�it->second++ʵ���ۼ�
			it->second++;
		}
		else {
			//it ָ��map.end()
			// �������make_pair�Ļ�  �ᱨ��
			map.insert(std::make_pair(ch, 1));
		}
	}

	// ͳ�ƽ���
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
		//����������,д��outfp
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
	//��λ����ȡbuf�����λֱ��buf.bits == 0
	bit = (buf.ch & 128) >> 7;
	buf.ch = buf.ch << 1;
	buf.bits--;
}