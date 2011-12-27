package apaapi4j.helper;

import org.junit.Test;

import apaapi4j.AmazonAPIService;
import apaapi4j.ItemSearchResponse;

public class ThreadTest {
	public static void main(String[] args) throws Exception {
		ThreadTest t = new ThreadTest();
		t.threadの仕様();
		t.シングルスレッドアクセス();
	}

	@Test
	public void threadの仕様() throws Exception {
		RequestThreadRunnner h1 = new RequestThreadRunnner("Hadoop");
		h1.startThread();
		RequestThreadRunnner h2 = new RequestThreadRunnner("Clean Code");
		h2.startThread();
		RequestThreadRunnner h3 = new RequestThreadRunnner("まどか☆マギカ");
		h3.startThread();
	}
	
	@Test
	public void シングルスレッドアクセス() throws Exception {
		AmazonAPIService api = new AmazonAPIService();
		Helper.logger.info(api.searchBooksByKeywords("Hadoop").toString());
		Helper.logger.info(api.searchBooksByKeywords("Clean Code").toString());
		Helper.logger.info(api.searchBooksByKeywords("まどか☆マギカ").toString());
	}
}

class RequestThreadRunnner implements Runnable {
	final String keyword;
	AmazonAPIService api = new AmazonAPIService();
	

	public RequestThreadRunnner(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void run() {
		Helper.logger.info("run: " + keyword);
		Helper.logger.info(call().toString());
	}

	public synchronized ItemSearchResponse call() {
		return api.searchBooksByKeywords(keyword);
	}

	public void startThread() {
		new Thread(this).start();
	}

	@Override
	public String toString() {
		return "RequestThreadRunnner [keyword=" + keyword + "]";
	}
}
