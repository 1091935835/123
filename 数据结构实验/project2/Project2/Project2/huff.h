#include<iostream>
using namespace std;

//FreqPair
template <class  Elem>
class FreqPair {
private:
	Elem symbol;
	int freq;

public:
	FreqPair(Elem s, int f) {
		symbol = s;
		freq = f;
	}
	int weight() {
		return freq;
	}
	Elem val() {
		return symbol;
	}
};

//HuffNode
template <class Elem>
class HuffNode {
public:
	virtual int weight() = 0;
	virtual bool isLeaf() = 0;
};

template <class Elem>
class LeafNode :public HuffNode<Elem> {
private:
	FreqPair<Elem>* it;
public:
	LeafNode(Elem val, int freq)
	{
		it = new FreqPair<Elem>(val, freq);
	}
	int weight() { return it->weight(); }
	bool isLeaf() { return true; }
	FreqPair<Elem>* val() { return it; }

};

template <class Elem>
class IntlNode :public HuffNode<Elem> {
private:
	HuffNode<Elem>* lc;
	HuffNode<Elem>* rc;
	int wgt;
public:
	IntlNode(HuffNode<Elem>* l, HuffNode<Elem>* r)
	{
		wgt = l->weight() + r->weight();
		lc = l;
		rc = r;
	}
	int weight() { return wgt; }
	bool isLeaf() { return false; }
	HuffNode<Elem>* left() { return lc; }
	void setLeft(HuffNode<Elem>* l) { lc = (HuffNode<Elem>*)l; }
	HuffNode<Elem>* right() { return rc; }
	void setRight(HuffNode<Elem>* r) { rc = (HuffNode<Elem>*)r; }
};

//Huffman Tree
template <class Elem>
class HuffTree {
private:
	HuffNode<Elem>* myroot;
	void printhelp(HuffNode<Elem>* subroot, int level) const {
		FreqPair<Elem>* s1;
		if (subroot == NULL) return;
		if (subroot->isLeaf()) {
			for (int i = 0; i < level; i++) cout << "*";
			cout << "Leaf:";
			s1 = ((LeafNode<Elem>*)subroot)->val();
			cout << s1->val() << " " << s1->weight() << endl;
		}
		else {
			printhelp(((IntlNode<Elem>*)subroot)->left(), level + 1);
			for (int i = 0; i < level; i++) cout << "*";
			cout << "Internal:";
			cout << ((IntlNode<Elem>*)subroot)->weight() << endl;
			printhelp(((IntlNode<Elem>*)subroot)->right(), level + 1);
		}
	}
public:
	HuffTree(Elem val, int freq) {
		myroot = new LeafNode<Elem>(val, freq);
	}
	HuffTree(HuffTree<Elem>* l, HuffTree<Elem>* r) {
		myroot = new IntlNode<Elem>(l->root(), r->root());
	}
	/*~ HuffTree() { бнбн }
	clear() {бнбн} */
	HuffNode<Elem>* root() { return myroot; }
	int weight() { return myroot->weight(); }
	void print() const {
		if (myroot == NULL) cout << "The huffTree is empty.\n";
		else printhelp(myroot, 0);
	}
};