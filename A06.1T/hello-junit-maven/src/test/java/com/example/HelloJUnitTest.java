package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloJUnitTest {

    @Test
    void sayHello_returnsExpectedMessage() {
        HelloJUnit hello = new HelloJUnit();
        assertEquals("Hello, World of Tests!", hello.sayHello());
    }
}
