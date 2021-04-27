#include<string>
#include<iostream>
using namespace std;
//Assert
//void Assert(bool val, string s) {
//	if (!val) {
//		cout << "Assertion Failed: " << s << endl;
//		exit(-1);
//	}
//}

//Link
template<class Elem> class Link {
public:
	Elem element;
	Link* next;
	Link(const Elem& elemval, Link* nextval = NULL) {
		element = elemval;
		next = nextval;
	}
	Link(Link* nextval = NULL) {
		next = nextval;
	}
};

//Stack
template <class Elem> class Stack {
public:
	Stack() {}
	virtual ~Stack() {}
	virtual void clear() = 0;
	virtual void push(const Elem&) = 0;
	virtual Elem pop() = 0;
	virtual Elem topValue() const = 0;
	virtual int lenght() const = 0;
};

//Linked Stack
template <class Elem>class LStack {
private:
	Link<Elem>* top;
	int size;

public:
	LStack() {
		top = NULL;
		size = 0;
	}
	~LStack() {
		clear();
	}
	void clear() {
		while (top != NULL) {
			Link<Elem>* temp = top;
			top = top->next;
			delete temp;
		}
		size = 0;
	}
	void push(Elem& it) {
		top = new Link<Elem>(it, top);
		size++;
	}
	Elem pop() {
		if (top == NULL) printf("Stack is empty");
		Link<Elem>* temp = top;
		top = top->next;
		size--;
		Elem it = temp->element;
		delete temp;
		return it;
	}
	Elem topValue() const {
		if (top == NULL) printf("Stack is empty");
		return top->element;
	}
	int length() const {
		return size;
	}
};