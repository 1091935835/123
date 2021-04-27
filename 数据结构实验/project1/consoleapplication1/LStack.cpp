#include"LStack.h"

void Assert(bool val, string s) {
	if (!val) {
		cout << "Assertion Failed: " << s << endl;
		exit(-1);
	}
}

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
		Assert(top != NULL, "Stack is empty");
		Link<Elem>* temp = top;
		top = top->next;
		size--;
		Elem it = temp->element;
		delete temp;
		return it;
	}
	Elem topValue() const {
		Assert(top != NULL, "Stack is empty");
		return top->element;
	}
	int length() const {
		return size;
	}
};