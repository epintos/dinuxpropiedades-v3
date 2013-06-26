package ar.edu.itba.it.paw.web;

import java.net.URL;
import java.net.URLClassLoader;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.CryptoMapper;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.publications.Currency;
import ar.edu.itba.it.paw.domain.publications.Operations;
import ar.edu.itba.it.paw.domain.publications.OrderEnum;
import ar.edu.itba.it.paw.domain.publications.PropertyType;
import ar.edu.itba.it.paw.domain.publications.RoomsEnum;
import ar.edu.itba.it.paw.domain.publications.Services;
import ar.edu.itba.it.paw.domain.publications.Status;
import ar.edu.itba.it.paw.domain.users.UserType;
import ar.edu.itba.it.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.it.paw.web.converters.DateTimeConverter;
import ar.edu.itba.it.paw.web.converters.EnumConverter;
import ar.edu.itba.it.paw.web.errorPages.InternalErrorPage;
import ar.edu.itba.it.paw.web.errorPages.SessionExpiredPage;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

@Component
public class DinuxPropiedadesApp extends WebApplication {

	private final SessionFactory sessionFactory;

	@Autowired
	public DinuxPropiedadesApp(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(
				new SpringComponentInjector(this));
		getRequestCycleListeners().add(
				new HibernateRequestCycleListener(sessionFactory));
		setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
		JQContributionConfig config = new JQContributionConfig().withDefaultJQueryUi();
		getComponentPreOnBeforeRenderListeners().add(
				new JQComponentOnBeforeRenderListener(config));

		getApplicationSettings().setPageExpiredErrorPage(SessionExpiredPage.class);
		getApplicationSettings().setInternalErrorPage(InternalErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
	}
	
	 public String getClasspathString() {
	     StringBuffer classpath = new StringBuffer();
	     ClassLoader applicationClassLoader = this.getClass().getClassLoader();
	     if (applicationClassLoader == null) {
	         applicationClassLoader = ClassLoader.getSystemClassLoader();
	     }
	     URL[] urls = ((URLClassLoader)applicationClassLoader).getURLs();
	      for(int i=0; i < urls.length; i++) {
	          classpath.append(urls[i].getFile()).append("\r\n");
	      }    
	     
	      return classpath.toString();
	  }
	
	@Override
	public Session newSession(Request request, Response response) {
		return new WicketSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		converterLocator.set(Operations.class,
				EnumConverter.create(Operations.class));
		converterLocator.set(OrderEnum.class,
				EnumConverter.create(OrderEnum.class));
		converterLocator.set(PropertyType.class,
				EnumConverter.create(PropertyType.class));
		converterLocator.set(RoomsEnum.class,
				EnumConverter.create(RoomsEnum.class));
		converterLocator.set(Services.class,
				EnumConverter.create(Services.class));
		converterLocator.set(Status.class, EnumConverter.create(Status.class));
		converterLocator.set(UserType.class,
				EnumConverter.create(UserType.class));
		converterLocator.set(Currency.class,
				EnumConverter.create(Currency.class));
		converterLocator.set(DateTime.class,
				new DateTimeConverter());
		return converterLocator;
	}
	
}
