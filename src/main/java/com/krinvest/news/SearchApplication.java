package com.krinvest.news;

import com.krinvest.news.util.ConfigUtil;
import com.krinvest.news.util.DataUtil;
import com.krinvest.news.util.TimeUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Map;

@SpringBootApplication
public class SearchApplication {

	public static void main(String[] args) {

//		String configPath = "C:\\Users\\KOSCOM\\Desktop\\각종자료\\개인자료\\key.json";
//		String configPath = "C:/Users/user/Desktop/개인자료/콤트/key/news_config.json";
		ConfigUtil.setConfigPath(args[0]);

		SpringApplication.run(SearchApplication.class, args);
	}

}
