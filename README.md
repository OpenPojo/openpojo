# OpenPojo [![Build Status](https://travis-ci.org/oshoukry/openpojo.svg?branch=master)](https://travis-ci.org/oshoukry/openpojo) [![Coverage Status](https://coveralls.io/repos/oshoukry/openpojo/badge.svg?branch=master)](https://coveralls.io/r/oshoukry/openpojo?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.openpojo/openpojo/badge.svg?style=flat)](http://search.maven.org/#search|ga|1|g%3Acom.openpojo)
POJO Testing &amp; Identity Management Made Trivial 

Maven Group Plugin | Latest Version
------------------ | ---------------
com.openpojo.openpojo | [0.8.3](https://github.com/oshoukry/openpojo/releases/tag/openpojo-0.8.3)
com.googlecode.openpojo.openpojo | [0.6.5](https://code.google.com/p/openpojo/wiki/Old) - Deprecated

#### Testing Example
```java
public class PojoTest {
  // Configured for expectation, so we know when a class gets added or removed.
  private static final int EXPECTED_CLASS_COUNT = 1;

  // The package to test
  private static final String POJO_PACKAGE = "com.openpojo.sample";

  @Test
  public void ensureExpectedPojoCount() {
    List <PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE,
                                                                   new FilterPackageInfo());
    Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
  }

  @Test
  public void testPojoStructureAndBehavior() {
    Validator validator = ValidatorBuilder.create()
                            // Add Rules to validate structure for POJO_PACKAGE
                            // See com.openpojo.validation.rule.impl for more ...
                            .with(new GetterMustExistRule())
                            .with(new SetterMustExistRule())
                            // Add Testers to validate behaviour for POJO_PACKAGE
                            // See com.openpojo.validation.test.impl for more ...
                            .with(new SetterTester())
                            .with(new GetterTester())
                            .build();

    validator.validate(POJO_PACKAGE, new FilterPackageInfo());
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

For more examples and the tutorials see the [Wiki](https://github.com/oshoukry/openpojo/wiki)
