## Overview ##
This project was born out of a need to validate all POJOs (Plain Old Java Object) are behaving correctly.

This project has two main aspects to it:
  * Make [Testing](Testing.md) as easy as possible.
  * Simplifying [identity](identity.md) management (hashCode / equals) using annotation.

## Quick Links ##
  1. [Documentation](Documentation.md)
  1. How to see [TestingTutorial](TestingTutorial.md) and for Identity management (equals, hashcode & toString) see [identity](identity.md)
  1. [Why OpenPojo?](Why_OpenPojo.md)
  1. [Frequently Asked Questions](FAQ.md) - In Progress
  1. Feedback using [online secure](https://spreadsheets.google.com/viewform?formkey=dEdBUFV6bDBoWllJQ1JnNzBnZnNST2c6MQ) form
  1. [Upcoming](Upcoming.md)
  1. [Download](http://bit.ly/1oiDOun)


## Latest news ##
  * **2015-02-08:**
    * OpenPojo-0.6.5 released download using Maven or from http://bit.ly/1oiDOun
      * Added
        1. PojoMethod.getPojoParameters now returns a list of PojoParameters, for each you can interrogate parameterization, get annotations, or get actual type.
        1. PojoClass.getPojoFieldsAnnotatedWith(Annotation), now retrieves all fields for a given Annotation.
        1. PojoClass.getPojoMethodsAnnotatedWith(Annotation), now retrieve all methods for a given annotation.
  * **2015-01-26:**
    * OpenPojo-0.6.4 released download using Maven or from http://bit.ly/1oiDOun
      * Fixed
        1. Standard out messages about coverage detection framework - [Issue 50](https://code.google.com/p/openpojo/issues/detail?id=50). (Thanks to Arngrimur B.)
      * Added
        1. PojoClass now has isStatic() method to enable quick check for static declaration of nested classes.
      * Changed
        1. LoggerFactory now doesn't rely on ActiveLogger utility class - which has been removed, and isn't coupled to PojoClass.
  * **2015-01-19:**
    * OpenPojo-0.6.3 released download using Maven or from http://bit.ly/1oiDOun
      * Fixed
        1. Issue when creating nested class that is declared non-static.
      * Added
        1. Generics enhancements, testers will now generate proper types for Map types.
        1. Constructors that required generic bags (Collection / Map / Custom) are now invoked with the proper types. ([Issue 49](https://code.google.com/p/openpojo/issues/detail?id=49))
      * Changed
        1. Whenever a general Map is requested, a HashMap will be used.
        1. All Map & Collection randomly generated will have between 1 and 5 items randomly added of the proper compatible generic type.
  * **2014-11-06:**
    * OpenPojo-0.6.2 released download using Maven or from http://bit.ly/1oiDOun
      * Fixed
        1. [Issue 43](https://code.google.com/p/openpojo/issues/detail?id=43) - Filesystem class discovery was running into issues with spaces (%20).  (Thanks to J.Karlsson & L.Osiatis)
        1. [Issue 46](https://code.google.com/p/openpojo/issues/detail?id=46) - Running PojoValidator on Synthetic classes throw Null Pointer in InstanceFactory.createInstance.  (Thanks to stumc68).
  * **2014-11-01:**
    * OpenPojo-0.6.1 released download using Maven or from http://bit.ly/1oiDOun
      * Added
        1. Generics are finally here, Setter/Getter testers will now generate proper types for Collections (Set, List, Queue).  This is an initial release, all feedback welcomed!
      * Changed
        1. If OpenPojo fails to get files from system path it won't throw null pointer anymore, but will throw proper exception with enough information to help trouble shoot.
      * Fixed
        1. [issue 47](https://code.google.com/p/openpojo/issues/detail?id=47) - Method lookup now works if the type is boolean and the field name starts with is (i.e. isFlagOn, ...etc).
  * **2014-10-08:**
    * OpenPojo-0.6.0 released download using Maven or from http://bit.ly/1oiDOun
      * Added
        1. Performance boost up to 2x while using BusinessIdentity - Cache remains for the lifetime of the VM.  (Thanks to Dilip J for his help testing & confirming performance gains)
        1. Auto-discovery for instrumentation frameworks, now pluggable, no more need to pass in FilterCloverClasses to PojoClassFactory.
        1. [Issue 43](https://code.google.com/p/openpojo/issues/detail?id=43) will now log as error and skip package paths that fail to enumerate - Please report logs on issue.
      * Removed
        1. ServiceRegistrar class filter & class adaptation services removed, all is now handled by the PojoCoverageFilterService.
  * **2014-09-03:**
    * OpenPojo-0.5.2 released download using Maven or from http://bit.ly/1oiDOun
      * Added
        1. Support for Clover 4.0
      * Fixed
        1. [Issue 44](https://code.google.com/p/openpojo/issues/detail?id=44) Identity factory is now thread safe, an issue that affects testing with multi-threaded on.
... [All News](News_full.md)

### Finally ###
If OpenPojo helped you, please drop me a line and tell me how it helped you.

- Osman Shoukry (first letter & full lastname @ openpojo.com, please put OpenPOJO in the subject).