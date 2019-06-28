package com.ikasoa.core.utils;

import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * List工具单元测试
 */
public class ListUtilTest extends TestCase {

	@Test
	public void testNewArrayList() {
		List<Object> list1 = ListUtil.newArrayList();
		assertEquals(list1.size(), 0);
		List<String> list2 = ListUtil.newArrayList(1);
		assertEquals(list2.size(), 0);
		list2.add("1");
		assertEquals(list2.size(), 1);
		List<String> list3 = ListUtil.newArrayList("1", "2");
		assertEquals(list3.size(), 2);
	}

	@Test
	public void testNewLinkedList() {
		List<Object> list1 = ListUtil.newLinkedList();
		assertEquals(list1.size(), 0);
		list1.add("1");
		assertEquals(list1.size(), 1);
		List<String> list2 = ListUtil.newLinkedList("1", "2");
		assertEquals(list2.size(), 2);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(ListUtil.isEmpty(null));
		assertTrue(ListUtil.isEmpty(ListUtil.newArrayList()));
	}

}
