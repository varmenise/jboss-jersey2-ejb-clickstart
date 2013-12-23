/*
 * Copyright 2010-2013, CloudBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package localdomain.localhost;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * JAX-RS provides a deployment agnostic abstract class Application for
 * declaring root resource and provider classes.
 * 
 * @author valentina armenise
 */

@Stateless
public class MyApplication extends Application {


	public MyApplication(ServiceLocator serviceLocator) {
		DynamicConfiguration dc = Injections.getConfiguration(serviceLocator);

		Injections.addBinding(Injections.newBinder((EJBFactory.class)).to(CountryRepository.class),dc);
		// singleton binding
		Injections.addBinding(
				Injections.newBinder(EJBInjectResolver.class)
				.to(new TypeLiteral<InjectionResolver<EJBProvider>>(){})
				.in(Singleton.class),
				dc);
		dc.commit();
	}

	public MyApplication() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		//s.add(EJBProvider.class);
		s.add(CountryResource.class);
		return s;
	}
}
//@Stateless
//public class MyApplication extends ResourceConfig {
//
//	public MyApplication() {
//
//		//packages("localdomain.localhost.CountryResource");
//
//		// register(new EJBProvider());
////		register(new AbstractBinder() {
////            @Override
////            protected void configure() {
////                //bindFactory(EJBFactory.class).to(CountryRepository.class);
////            	bind(EJBFactory.class).to(CountryRepository.class);
////            	bind(EJBInjectResolver.class)
////                    .to(new TypeLiteral<InjectionResolver<EJBProvider>>(){})
////                    .in(Singleton.class);
////            }
////        });
//
//	}
//
//}


