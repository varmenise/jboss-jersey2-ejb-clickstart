package localdomain.localhost;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;


public class EJBInjectResolver implements InjectionResolver<EJBProvider> {

	@Inject
	@Named(InjectionResolver.SYSTEM_RESOLVER_NAME)
	InjectionResolver<Inject> ejbInjectionResolver;

	@Override
	public Object resolve(Injectee injectee, ServiceHandle<?> handle) {
		if (HttpSession.class == injectee.getRequiredType()) {
			return ejbInjectionResolver.resolve(injectee, handle);
		}

		return null;
	}

	@Override
	public boolean isConstructorParameterIndicator() {
		return false;
	}

	@Override
	public boolean isMethodParameterIndicator() {
		return false;
	}
}
