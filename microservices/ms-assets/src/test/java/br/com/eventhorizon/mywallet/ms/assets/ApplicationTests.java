package br.com.eventhorizon.mywallet.ms.assets;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//@SpringBootTest
class ApplicationTests {

	private boolean isInternal() {
		return true;
	}

	@Test
	void test() {
		System.out.println("LocalDateTime: " + LocalDateTime.now());
		System.out.println("LocalDateTime (UTC): " + LocalDateTime.now(ZoneOffset.UTC));

		System.out.println("ZonedDateTime: " + ZonedDateTime.now());
		System.out.println("ZonedDateTime (UTC): " + ZonedDateTime.now(ZoneOffset.UTC));

		System.out.println("OffsetDateTime: " + OffsetDateTime.now());
		System.out.println("OffsetDateTime (UTC): " + OffsetDateTime.now(ZoneOffset.UTC));
	}

}
