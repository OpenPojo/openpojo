# How do I get a list of all classes in a given package? #

To lookup classes you can do one of the following:

### 1. Getting all classes in a given package "com.mycompany.pojos" ###
```
List<PojoClass> pojoClasses =
    PojoClassFactory.getPojoClasses("com.mycompany.pojos");
```

### 2. Getting all classes in a given package and all sub packages "com.mycompany" ###
```
List<PojoClass> pojoClasses =
    PojoClassFactory.getPojoClassesRecursively("com.mycompany.mypackage", null);
```

### 3. Getting all classes that extend a given class or implement a given interface ###
```
List<PojoClass> pojoClasses =
    PojoClassFactory.enumerateClassesByExtendingType("com.mycompany", MyInterface.class, null);
```

### 4. Getting all concrete (i.e. not interfaces or abstract classes) classes in a given package ###
```
List<PojoClass> pojoClasses =
    PojoClassFactory.getPojoClassesRecursively("com.mycompany", new FilterNonConcrete());
```

### What other filters can I use? ###
See package "com.openpojo.reflection.filters" as of writing this there are:
  1. _FilterBasedOnInheritance_ - Used by enumerateClassesByExtendingType.
  1. _FilterChain_ - Combine many filters in a single chain.
  1. _FilterClassName_ - Use a regular expression to filter classes based on their name.
  1. _FilterEnum_ - Filter out all enum classes.
  1. _FilterNestedClasses_ - Filter out all non-top-level classes.
  1. _FilterNonConcrete_ - Filter out Interfaces and abstract classes.
  1. _FilterPackageInfo_ - Filter the PackageInfo special package configuration class.
  1. _FilterSyntheticClasses_ - Filter out any auto-generated JVM classes.

## I want to write my own filtering mechanism for looking up a class ##

To implement your own filtering mechanism implement the interface PojoClassFilter

For example, if you want to get all inner classes in your system, first implement your Filter:

```
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

public class InnerClassesFilter implements PojoClassFilter {
  public boolean include(final PojoClass pojoClass {
    return pojoClass.isNestedClass();
  }
}
```

Then in in your test do:

```
List<PojoClass> pojoClasses =
    PojoClassFactory.getPojoClassesRecursively("com.mycompany", new InnerClassesFilter());
```