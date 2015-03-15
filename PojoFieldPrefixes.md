### Field Prefixes ###
Some companies or legacy code uses specific field prefixes _m or others, those will prevent OpenPojo from being able to lookup getters setters._

You will need to register those prefixes before you enumerate your Pojos.
```
import com.openpojo.reflection.utils.AttributeHelper;

import org.junit.Before;
import org.junit.Test;

public class SomeTest {

  @Before
  public void cleanup() {
    PojoCache.clear();
    AttributeHelper.registerFieldPrefix("m_");  // Call again for any other prefixes you have
  }

  @Test
  public void myTest() {
    ...
  }
```