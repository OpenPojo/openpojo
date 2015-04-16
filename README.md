# OpenPojo [![Build Status](https://travis-ci.org/oshoukry/openpojo.svg?branch=master)](https://travis-ci.org/oshoukry/openpojo) [![Coverage Status](https://coveralls.io/repos/oshoukry/openpojo/badge.svg?branch=master)](https://coveralls.io/r/oshoukry/openpojo?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.openpojo/openpojo/badge.svg?style=flat)](http://search.maven.org/#search|ga|1|g%3Acom.openpojo)
POJO Testing &amp; Identity Management Made Trivial 

Maven Group Plugin | Latest Version
------------------ | ---------------
com.openpojo.openpojo | [0.7.2](https://github.com/oshoukry/openpojo/releases/tag/openpojo-0.7.2)
com.googlecode.openpojo.openpojo | [0.6.5](https://code.google.com/p/openpojo/wiki/Old) - Deprecated

#### Testing Example
```java
public class PojoTest {
  // Configured for expectation, so we know when a class gets added or removed.
  private static final int EXPECTED_CLASS_COUNT = 1;

  // The package to test
  private static final String POJO_PACKAGE = "com.openpojo.sample";

  private List<PojoClass> pojoClasses;
  private PojoValidator pojoValidator;

  @Before
  public void setup() {
    pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, new FilterPackageInfo());
    pojoValidator = new PojoValidator();

    // Add Rules to validate structure for POJO_PACKAGE
    pojoValidator.addRule(new GetterMustExistRule());
    pojoValidator.addRule(new SetterMustExistRule());
    pojoValidator.addRule(/* ... See com.openpojo.validation.rule.impl for more ...*/);

    // Add Testers to validate behaviour for POJO_PACKAGE
    pojoValidator.addTester(new SetterTester());
    pojoValidator.addTester(new GetterTester());
    pojoValidator.addTester(/* ... See com.openpojo.validation.test.impl for more ...*/);
  }

  @Test
  public void ensureExpectedPojoCount() {
    Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
  }

  @Test
  public void testPojoStructureAndBehavior() {
    for (PojoClass pojoClass : pojoClasses) {
        pojoValidator.runValidation(pojoClass);
    }
  }
}
```

#### Identity Management Example
```java
public class Person {
  @BusinessKey(caseSensitive = false)  //Configure your field(s)
  private String lastName;

  @Override
  public boolean equals(Object obj) {
    return BusinessIdentity.areEqual(this, obj);
  }

  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }

  @Override
  public String toString() {
      return BusinessIdentity.toString(this);
  }
}
```
