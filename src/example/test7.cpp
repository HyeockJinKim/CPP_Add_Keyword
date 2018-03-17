class A {
        int x;
		virtual void a(int num, int n) {}
};

class B : public A {
		virtual void a(int a) {}
};

int main() {
	A * testA = new B();

	return 0;
}
// overload problem