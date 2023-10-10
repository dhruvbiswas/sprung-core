package com.di.core.discovery;

import java.lang.annotation.Annotation;

public interface IBeanIDGenerator {

    String generateBeanId(Class cl, Annotation cl_annotation);

}
