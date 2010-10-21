package com.openpojo.reflection.impl.sampleclasses;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;

public class AClassWithVariousAnnotatedFields {

    public String nonAnnotatedField;

    @SomeAnnotation
    public String singleAnnotationField;

    @SomeAnnotation
    @BusinessKey
    public String multipleAnnotationField;

}
