package g4;

import java.util.HashSet;
import java.util.Set;


public class WeakClass {
    public String classkey;
    public String baseClause;
    public Set<String> superClassSet;
    public StringBuilder privateFunction;
    public StringBuilder defaultFunction;
    public StringBuilder protectedFunction;
    public StringBuilder publicFunction;

    public WeakClass(String classkey) {
        this.classkey = classkey;
        superClassSet = new HashSet<>();
        privateFunction = new StringBuilder();
        defaultFunction = new StringBuilder();
        protectedFunction = new StringBuilder();
        publicFunction = new StringBuilder();
    }
}
