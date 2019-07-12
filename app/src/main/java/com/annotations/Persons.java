package com.annotations;

import java.lang.annotation.Repeatable;

public @interface Persons {
    Person[] value();
}

@Repeatable(Persons.class) //容器注解:用来存放Person的数组注解.
@interface Person {
    String role() default "";
}


