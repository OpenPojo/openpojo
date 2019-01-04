# OpenPojo [![Build Status](https://travis-ci.org/OpenPojo/openpojo.svg?branch=master)](https://travis-ci.org/OpenPojo/openpojo) [![Coverage Status](https://coveralls.io/repos/OpenPojo/openpojo/badge.svg?branch=master)](https://coveralls.io/r/OpenPojo/openpojo?branch=master)  [![Maven Central](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/openpojo/openpojo/maven-metadata.xml.svg?style=flat&colorB=007ec6)](http://search.maven.org/#search|ga|1|g%3Acom.openpojo%20a%3Aopenpojo)
POJO Testing &amp; Identity Management Made Trivial 

Maven Group Plugin | Latest Version
------------------ | ---------------
com.openpojo.openpojo | [0.8.11](https://github.com/oshoukry/openpojo/releases/tag/openpojo-0.8.11)
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
