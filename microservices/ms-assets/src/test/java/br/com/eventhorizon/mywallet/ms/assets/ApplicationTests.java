package br.com.eventhorizon.mywallet.ms.assets;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;

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

		System.out.println("Date: " + Date.from(Instant.now()));
		System.out.println("Date (UTC): " + Date.from(Instant.now(Clock.systemUTC())));
	}
}
