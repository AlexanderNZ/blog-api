package communityblogger.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/services")
public class BloggerApplication extends Application {


    private Set<Object> _singletons = new HashSet<Object>();
    private Set<Class<?>> _classes = new HashSet<Class<?>>();

    public BloggerApplication() {
        _singletons.add(new BloggerResourceImpl());
        _classes.add(BloggerResolver.class);


        // TO DO:
        // Populate the _singletons and _classes sets with a resource instance
        // and any component classes your application needs.
    }

    @Override
    public Set<Object> getSingletons() {
        return _singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return _classes;
    }
}
