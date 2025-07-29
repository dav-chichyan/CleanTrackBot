package com.maqoor.telegram_bot;
import com.maqoor.telegram_bot.telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Slf4j
@SpringBootApplication()
@EnableJpaRepositories(basePackages = "com.maqoor.telegram_bot.telegram.repo")
public class TelegramBotApplicationMqApplication {

	public static void main(String[] args) throws TelegramApiException {
		ApplicationContext context = SpringApplication.run(TelegramBotApplicationMqApplication.class, args);
		TelegramBot telegramBot = context.getBean(TelegramBot.class);
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(telegramBot);
		log.info("TelegramBot started");
	}
}
