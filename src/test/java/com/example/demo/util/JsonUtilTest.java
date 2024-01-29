package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void convertToJsonTest() {
        final TestObject testObject = new TestObject("testValue1", "testValue2");

        final String json = JsonUtil.convertToJson(testObject);

        assertTrue(json.contains("testField1"), "JSON should contain 'testField1'");
        assertTrue(json.contains("testValue1"), "JSON should contain the value of 'testField1'");
        assertTrue(json.contains("testField2"), "JSON should contain 'testField2'");
        assertTrue(json.contains("testValue2"), "JSON should contain the value of 'testField2'");
    }

    @Test
    void convertJsonToListTest() {
        final String listJson = "[{\"testField1\":\"value1\",\"testField2\":\"value2\"}, {\"testField1\":\"value3\",\"testField2\":\"value4\"}]";

        final List<TestObject> list = JsonUtil.convertJsonToList(listJson, TestObject.class);

        assertNotNull(list, "List should not be null");
        assertEquals(2, list.size(), "List should contain two elements");
        assertEquals("value1", list.get(0).testField1, "First element's testField1 should be 'value1'");
        assertEquals("value2", list.get(0).testField2, "First element's testField2 should be 'value2'");
        assertEquals("value3", list.get(1).testField1, "Second element's testField1 should be 'value3'");
        assertEquals("value4", list.get(1).testField2, "Second element's testField2 should be 'value4'");
    }

    static class TestObject {
        String testField1;
        String testField2;

        public TestObject(String testField1, String testField2) {
            this.testField1 = testField1;
            this.testField2 = testField2;
        }
    }
}
