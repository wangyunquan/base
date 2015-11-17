package com.buswe.dht.test;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

import com.buswe.dht.node.DhtKeyFactory;

public class KeyTest {

	public static void main(String[] args) {
		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
 
		System.out.println(osBean.getSystemLoadAverage());
	}

}
