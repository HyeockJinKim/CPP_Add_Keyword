class A {
	public:
		virtual void a(int num) {}
};

class B {
	public:
		virtual void b() {}
};

class C : public weak A, public B {
	public:
		virtual void a(int num){}
};

int main() {
	A * testA = new C();

	return 0;
}
