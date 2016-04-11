#!/bin/sh

set -x

rm -rf com
mkdir -p tmp/com/failtoload
mkdir -p tmp/com/openpojotest


cat > tmp/com/failtoload/DeleteMeClass.java <<EOF
package com.failtoload;

public class DeleteMeClass {

}

EOF

cat > tmp/com/failtoload/AClassWithFieldMissing.java <<EOF
package com.failtoload;

public class AClassWithFieldMissing {
  private DeleteMeClass fieldWithoutClass;

}

EOF

cat > tmp/com/failtoload/AClassWithMethodReturningMissing.java <<EOF
package com.failtoload;

public class AClassWithMethodReturningMissing {
  public DeleteMeClass getDeleteMeClass() {
    return new DeleteMeClass();
  }
}

EOF

cat > tmp/com/failtoload/AClassWithMethodParameterMissing.java <<EOF
package com.failtoload;

public class AClassWithMethodParameterMissing {
  public void setDeleteMeClass(DeleteMeClass missing) {
  }
}

EOF

cat > tmp/com/openpojotest/AClass.java <<EOF
package com.openpojotest;

public class AClass {
  public String sayHello() {
    return "Hello World!";
  }
}

EOF

cat > tmp/com/openpojotest/AndAnotherClass.java <<EOF
package com.openpojotest;

public class AndAnotherClass {
  public String getGreetingMessage(final String name) {
    return "Hello " + name + ", so good to meet you";
  }
}

EOF

cd tmp/

javac -source 1.5 -target 1.5 -cp . com/failtoload/*.java
javac -source 1.5 -target 1.5 -cp . com/openpojotest/*.java

rm -fv com/failtoload/DeleteMeClass.class
find ./com -name "*.java" -exec rm -rvf {} \;

rm -rf ../../test
mkdir -p ../../test
jar -cvf ../../test/sampleJar.zip .

cd ..
rm -rf tmp