class A {
    int x;
public : A(){}
    virtual void f(){}
};

class B1 : weak public A {
public : B1() {}
    virtual void f1() { }
    virtual void f2() { }
};

class B2 : public B1 {
public : B2() {}
   virtual void f() {}
};

struct S1 {
public :
    inline virtual void f() {}
};

struct S2 : public weak S1 {
public :
    virtual void f() {}
};

int a() {
    A * t1 = new A;
    nweak B1 * t2 = wstatic_cast<B1*> (t1);
}