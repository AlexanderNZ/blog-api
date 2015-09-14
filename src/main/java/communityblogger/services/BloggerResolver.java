package communityblogger.services;

import communityblogger.domain.User;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Provider
@Produces("application/xml")
public class BloggerResolver implements ContextResolver<JAXBContext>{
	private JAXBContext _context;
	
	// TO DO:
	// Add to the _classes array the classes whose object are to be marshalled/
	// unmarshalled to/from XML. Objects of these class will be exchanged by
	// clients and your Web service.
	private Class<?>[] _classes = {User.class};

//	Marshaller marshaller = _context.createMarshaller();
//	Unmarshaller unmarshaller = _context.createUnmarshaller();
	
	public BloggerResolver() {
		try {
				System.out.println("I got instantiated");

			_context = JAXBContext.newInstance(_classes);
			
			// Initialise the context as required.
		} catch(JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {

		System.out.println("getContext happened");

		for(int i = 0; i < _classes.length; i++) {
			if(type.equals(_classes[i])) {
				return _context;
			}
		}
		return null;
	}
}
