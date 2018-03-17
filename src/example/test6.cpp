class A {
        int x;
		virtual void a(int num) {}
};

class B {
		virtual void b() {}
};

class C : public weak A, public B {
int y;
void cc(){}
		virtual void a(int num){}
};

int main() {
	A * testA = new C();

	return 0;
}
// default access specifier Test.