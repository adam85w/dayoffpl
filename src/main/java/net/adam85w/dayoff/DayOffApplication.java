package net.adam85w.dayoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Day Off
 * Checks whether a day is a day off in Poland based on public holidays, Saturdays and Sundays.
 * All types of days off can be switched off, and the entire service is highly configurable
 *
 * @author Adam Woźniak <adam85.w@gmail.com>
 */
@SpringBootApplication
@ConfigurationPropertiesScan({"net.adam85w.dayoff"})
public class DayOffApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayOffApplication.class, args);
	}
}
