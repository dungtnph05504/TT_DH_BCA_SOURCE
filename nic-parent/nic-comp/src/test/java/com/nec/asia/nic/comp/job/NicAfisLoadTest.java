package com.nec.asia.nic.comp.job;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * @author khang
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since :
 */
public class NicAfisLoadTest extends TestCase {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int NTHREDS = 5;

	public NicAfisLoadTest() {
		init();
	}

	public void init() {

	}

	public void testThread() {
		try {
			ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
			List<String> transactionIdList = new ArrayList<String>();

			FileInputStream fstream = new FileInputStream("C:\\NEC\\TransactionList.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			while ((strLine = br.readLine()) != null) {
				transactionIdList.add(strLine);
			}
			br.close();

			for (String transactionId : transactionIdList) {
				NicAfisLoadTestThread runnable = new NicAfisLoadTestThread();
				runnable.setTransactionId(transactionId);
				Runnable worker = runnable;
				executor.execute(worker);
			}
			executor.shutdown();
			while (!executor.isTerminated()) {
				Thread.sleep(5000L);
			}
		} catch (Exception e) {

		}
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
