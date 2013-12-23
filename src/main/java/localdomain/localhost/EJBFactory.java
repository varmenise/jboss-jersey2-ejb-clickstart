package localdomain.localhost;

import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HK2 factory to provide EJB components obtained via JNDI lookup.
 */
public class EJBFactory<T> implements Factory<T>{
	
	Logger logger = LoggerFactory.getLogger(EJBFactory.class);

	final InitialContext ctx;
	final Class<T> clazz;

	@SuppressWarnings("unchecked")
	@Override
	public T provide() {
		try {
			// Query the directory with the name of the EJB
			return (T) ctx.lookup(clazz.getName());
		} catch (NamingException ex) {
			logger.info(ApplicationHandler.class.getName());
			return null;
		}
	}


	@Override
	public void dispose(T instance) {
		// do nothing
	}

	public EJBFactory(InitialContext ctx, Class<T> clazz) {
		this.ctx = ctx;
		this.clazz = clazz;
	}
}