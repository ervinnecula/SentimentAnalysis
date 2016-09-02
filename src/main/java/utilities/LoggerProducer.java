package utilities;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;

public class LoggerProducer {
	@Produces
	public Logger getLogger(InjectionPoint p) {
		return Logger.getLogger(p.getClass().getCanonicalName());
	}
}