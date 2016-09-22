package com.example.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SmSTool {

	public static ArrayList<String> addPrefixToNumbers(String destinataires) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(destinataires, ",");

		while (st.hasMoreTokens()) {
			StringBuilder sb = new StringBuilder();
			sb.append("225");
			sb.append(st.nextToken());
			list.add(sb.toString());
		}
		return list;
	}
}
