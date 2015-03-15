# Simplifying Identity management #

Say you have a Person domain entity that has a lastname private field.  You have the following rule:
  * lastname is required for this entity to be valid.
  * lastname is case insensitive so if you have two instances (Person1.lastname = "TWAIN" and Person2.lastname = "Twain") they should be equal on Person1.equals(Person2).

Which could look something like this:
```
public class Person {
    ...
    private String lastname;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equalsIgnoreCase(other.lastname))
            return false;
        return true;
    }
}
```

oh and don't forget to fix your **hashCode** too...

## Alternatively ##
You can simplify your code by doing the following
```
public class Person {
    
    @BusinessKey(caseSensitive = false)  //Configure your field(s)
    private String lastname;

    @Override
    public boolean equals(Object obj) {
        return BusinessIdentity.areEqual(this, obj);

    }
}
```

### What about hashCode you ask? ###

Do this:
```
    @Override
    public int hashCode() {
        return BusinessIdentity.getHashCode(this);
    }
```

### One more thing... ###

The duo is never complete until you are able to see a pojo print itself out... Why code out **toString()**?

Try this:
```
    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }
```