# 内部类

内部类就是把一个类定义在另一个类的内部。

```java
class OuterClass {
    class InnerClass {
    }
}
```

一个内部类的对象依赖于一个已存在的外部类的对象，因此我们必须通过一个外部类对象来创建内部类对象。创建的内部类对象拥有对外部类对象的引用。

```java
public static void main(String[] args) {
    OuterClass outer = new OuterClass();
    // 通过outer对象创建inner对象。
    OuterClass.InnerClass inner = outer.new InnerClass();
}
```

在一个内部类对象中`this`指的是内部类对象本身，如果要访问外部类对象，需要加上外部类的类名，如`OuterClass.this`。内部类对象可以访问所有外部类对象中的字段（包括`private`字段）。

```java
class OuterClass {

    private String field = "OuterClass";

    class InnerClass {
        private String field = "InnerClass";

        void printInnerField() {
            System.out.println(this.field);
        }

        void printOuterField() {
            System.out.println(OuterClass.this.field);
        }
    }
}

public static void main(String[] args) {
    OuterClass outer = new OuterClass();
    OuterClass.InnerClass inner = outer.new InnerClass();
    inner.printInnerField(); //此处打印“InnerClass”。
    inner.printOuterField(); //此处打印“OuterClass”。
}
```

外部类也可以访问内部类的`private`和`protected`字段。

```java
class OuterClass {

    class InnerClass {
        private int innerField;
    }

    int innerField() {
        return new InnerClass().innerField;
    }
}
```

内部类的访问权限和字段一样也可以是`public`、`private`、`protected`以及默认的包访问权限。

在下面的例子中，我们有一个`protected`访问权限的内部类`InnerClass`，如果我们需要在另一个包中创建该类的对象，首先要让外部类（`AnotherOuterClass`）继承`InnerClass`的外部类`OuterClass`，需要注意的是，如果一个内部类是`protected`的访问权限，那么它的默认构造器也是`protected`权限，因此，当`AnotherOuterClass`继承`OuterClass`后，虽然`AnotherOuterClass`可以看到`InnerClass`，但是无法进行创建`InnerClass`的对象，所以一个解决方案是在`AnotherOuterClass`中创建一个内部类`AnotherInnerClass`继承`InnerClass`。

```java
// package playground.a
public class OuterClass {

    protected class InnerClass {
    }
}

public class AnotherOuterClass extends OuterClass {

    class AnotherInnerClass extends InnerClass {
    }

    public static void main(String[] args) {
        new AnotherOuterClass().new AnotherInnerClass();
    }
}
```

另一个更简单的解决方案是在`InnerClass`中增加一个`public`的构造器。

```java
// package playground.a
public class OuterClass {

    protected class InnerClass {

        public InnerClass() {
        }
    }
}

// package playground.b
public class AnotherOuterClass extends OuterClass {

    public static void main(String[] args) {
        new AnotherOuterClass().new InnerClass();
    }
}
```

内部类**不允许**静态变量、静态方法、接口或枚举，下面的代码无法通过编译。

```java
public class OuterClass {
    class InnerClass {
        interface Interface {
        }
    }
}
```

内部类**允许**多层嵌套。

```java
class OuterClass {
    class MiddleClass {
        class InnerClass {
        }
    }
}

public static void main(String[] args) {
    OuterClass outerClass = new OuterClass();
    MiddleClass middleClass = outerClass.new MiddleClass();
    InnerClass innerClass = middleClass.new InnerClass();
}
```

## 内部类的继承

一个类如果继承一个内部类，它的构造函数必须有一个指向外部类对象的引用，因为内部类对象的创建必须依赖于一个外部类对象，此外在构造函数中需要使用一种特殊的语法。

```java
class OuterClass {
    class InnerClass {
    }
}

class InheritClass extends OuterClass.InnerClass {

    InheritClass(OuterClass outerClass) {
        outerClass.super(); //此处使用了特殊语法。
    }
}

public static void main(String[] args) {
    OuterClass outerClass = new OuterClass();
    InheritClass inheritClass = new InheritClass(outerClass); //通过构造方法把外部类对象传递进去。
}
```

## 嵌套类

在内部类的定义前加上`static`修饰就变成了嵌套类，也叫静态内部类，它和内部类的最大区别在于它不需要依赖外部对象可以独立创建。

```java
class OuterClass {

    static class StaticInnerClass {
    }
}

public static void main(String[] args) {
    new OuterClass.StaticInnerClass(); //StaticInnerClass对象的创建不依赖于OuterClass对象。
}
```

和内部类不同，嵌套类可以包含静态字段、静态方法、内部类、嵌套类以及接口，但它不能访问外部类对象的非静态字段或非静态方法。

```java
class OuterClass {

    static class StaticInnerClass {

        static String field = "StaticInnerClass";

        static void method() {}

        static class StaticInner {}

        class Inner {}

        interface Interface {}
    }
}
```

## 匿名类

匿名类就是没有名字的类，下面是一个实现了`Runnable`接口的匿名类。匿名类中**不允许**静态方法或静态字段。

```java
new Runnable() {
    @Override
    public void run() {
    }
};
```

### 匿名类中的final

以下代码试图在实现了`Runnable`接口的匿名类中对外部局部变量`object`重新赋值。

```java
public class AnonymousClass {

    public static void main(String[] args) {
        Object object = new Object();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                object = new Object();
                System.out.println(object);
            }
        };
        runnable.run();
    }
}
```

以上代码无法通过编译。

```java
AnonymousClass.java:8: error: local variables referenced from an inner class must be final or effectively final
                object = new Object();
                ^
```

原因是一个被内部类引用的外部局部变量必须是`final`的或者是等效于`final`的变量，因此我们无法对`object`变量重新赋值，因为它必须等效于被`final`修饰的变量。

我们对以上代码进行修改。

```java
public static void main(String[] args) {
    final Object object = new Object(); //此处的final在JDK8之后也可以省略，因为下面的匿名类中没有修改object对象，因此和final等效。
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println(object);
        }
    };
    runnable.run();
}
```

我们对`AnonymousClass`进行反编译查看编译器处理过代码。

```java
class AnonymousClass$1 implements Runnable {

    AnonymousClass$1(Object var1) {
        this.val$object = var1;
    }

    public void run() {
        System.out.println(this.val$object);
    }
}
```

可以看到编译器为匿名类生成了一个叫做`AnonymousClass$1`的类，外部局部变量是通过构造函数传进来的，又因为java的引用是按值传递的，因此如果我们在内部类中修改`object`的引用是无法影响外部局部变量`object`的，语言设计者为了防止混淆所以强制要求这种情况下的局部变量必须由`final`修饰或等效于`final`。

但是如果`object`是类的字段那么就不需是`final`的，以下代码编译通过。

```java
public class AnonymousClass {

    Object object = new Object();

    void run() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                object = new Object();
            }
        };
        runnable.run();
    }
}

public static void main(String[] args) {
    new AnonymousClass().run();
}
```

查看反编译后的代码。

```java
class AnonymousClass$1 implements Runnable {

    AnonymousClass$1(AnonymousClass var1) {
        this.this$0 = var1;
    }

    public void run() {
        this.this$0.object = new Object();
    }
}
```

我们可以看到这个时候内部类拥有对外部对象的引用，因此可以修改`object`字段，也就不存在局部变量中出现的混淆问题了。
