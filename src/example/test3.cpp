class A {
public:
virtual a(int num) {}
};

class B {
public:
virtual d(int num) {}
};

// FIXME : Function name equal problem.
// FIXME : Function Overloading problem.
// TODO : function public, private problem.

class testA : weak public A {
public:
virtual a(int num) {}
};

class testAB : weak public A, public weak B {
public:
virtual a(int num) {}
virtual d(int num) {}
};