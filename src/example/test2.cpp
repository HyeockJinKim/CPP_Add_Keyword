
using std::cout;
using std::endl;
using typename myLibrary::A::Nested;
class A {
private:
    int x;
public : A(){}
    virtual void f(int a, C b){} //
};

class C {
public: C() {}
    ~C() {}
    virtual void g1() {}
};

class B1 : public A, public weak C {
private:
    int y;
public : B1() {}
    void g1() {}
    virtual void f() { cout << "E" << endl; }
    virtual void f2() { }
};
/*
class _B1 : public A {
}

class B1 : public _B1, public C {
private:
    int y;
public : B1() {}
    virtual void f() { }
    virtual void f2() { }
};*/

class B2 : public weak B1 {
public : B2() {}
   virtual void f() {}
};

struct S1 {
public :
    virtual void f() {}
};

struct S2 : public weak S1 {
public :
    virtual void f() {}
};

int a() {
    A * t1 = new B1;
    A * t2 = wstatic_cast<B1*>(t1);
}
