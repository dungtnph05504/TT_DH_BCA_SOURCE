package com.nec.asia.nic.investigation.controller;

import java.util.Map;
import java.util.TreeMap;

public class InvestigationData {

	public Map<Integer, Integer> goodFpQuasMap;
	public Map<Integer, Integer> acceptFpQuasMap;

	public InvestigationData() {
		goodFpQuasMap = new TreeMap<Integer, Integer>();
		acceptFpQuasMap = new TreeMap<Integer, Integer>();
	}
}
