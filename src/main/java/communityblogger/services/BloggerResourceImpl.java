package communityblogger.services;


import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;
import org.joda.time.DateTime;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of the BloggerResource interface.
 */
public class BloggerResourceImpl implements BloggerResource {

    /*
     * Possible data structures to store the domain model objects.
     * _users is a map whose key is username. Each User is assumed to have a
     * unique username.
     * _blogEntries is a map whose key is the ID of a BlogEntry. Each BlogEntry
     * is assumed to have a unique ID.
     * _idCounter is a thread-safe counter, which can be used to assign unique
     * IDs to blogEntry objects as they are created.
     */
    private Map<String, User> _users;
    private Map<Long, BlogEntry> _blogEntries;
    private AtomicLong _idCounter;
    private Map<String, User> userHashMap = new HashMap<String, User>();
    private Map<Long, BlogEntry> blogEntryMap = new HashMap<Long, BlogEntry>();


    public BloggerResourceImpl() {
        // TO DO:
        // Initialise instance variables.
        initialiseContent();

    }

    public final void initialiseContent() {
        // TO DO:
        // (Re)-initialise data structures so that the Web service's state is
        // the same same as when the Web service was initially created.

        _idCounter = new AtomicLong(0);
        userHashMap.put("Bertmern", new User("Bertmern", "Brerce", "Werne"));
        userHashMap.put("Spodermern", new User("Spodermern", "Terby", "Mergwer"));

        //set up a test blog entry
        BlogEntry testBlogEntry = new BlogEntry("Here is item 0 in blogEntryMap");
        testBlogEntry.setId(_idCounter.getAndIncrement());
        testBlogEntry.setTimePosted(DateTime.now());
        User testBlogCreator = userHashMap.get("Spodermern");
        testBlogCreator.addBlogEntry(testBlogEntry);
        Comment testComment = new Comment("I'm a test comment", DateTime.now());
        testBlogEntry.addComment(testComment);
        blogEntryMap.put(0l, testBlogEntry);
    }

    @Override
    public Response createUser(User user) {

        //if username does not exist (need to figure out how to check if a user exists)
        if (!userHashMap.containsKey(user.getUsername())) {

            userHashMap.put(user.getUsername(), user);

            return Response.status(201).link("/services/resources/user/"
                    + user.getUsername(), "resource").build();

        } else
            return Response.status(409).build();

    }

    @Override
    public Response retrieveUser(String username) {

        //if user exists, return user
        if (userHashMap.containsKey(username))

            //need to store this into an xml response body
            return Response.status(200).entity(userHashMap.get(username)).build();

            //else return 404
        else {
            return Response.status(404).build();
        }


    }

    @Override
    public Response createBlogEntry(BlogEntry blogEntry, String username) {


        if (userHashMap.containsKey(username)) {
            User blogAuthor = userHashMap.get(username);
            //then user exists and request should succeed
            blogEntry.setId(_idCounter.getAndIncrement());
            blogEntry.setTimePosted(DateTime.now());
            blogAuthor.addBlogEntry(blogEntry);
            blogEntryMap.put(blogEntry.getId(), blogEntry);

            return Response.status(201).link("/services/resources/blog/"
                    + blogEntry.getId(), "resource").build();

        } else {
            //user does not exist and request should fail
            return Response.status(412).build();
        }

    }

    @Override
    public Response retrieveBlogEntry(String blogId) {

        long blogIdlong = Long.parseLong(blogId);
        //if blog entry exists, return blog entry
        if (blogEntryMap.containsKey(blogIdlong)) {

            //need to store this into an xml response body
            return Response.status(200).entity(blogEntryMap.get(blogIdlong)).build();

            //else return 404
        } else {
            return Response.status(404).build();
        }

    }

    @Override
    public Response createComment() {

        Response response = null;
        return response;
    }

    @Override
    public Response retrieveComments(String blogId) {

        //if blog entry exists, return 200 OK
        //Response body must contain XML of Set of comments
        //Each comment should contain the username of the comment author, content and timestamp

        long blogIdlong = Long.parseLong(blogId);
        if (blogEntryMap.containsKey(blogIdlong)) {

            BlogEntry blogEntry = blogEntryMap.get(blogIdlong);
            Set<Comment> comments = blogEntry.getComments();
            return Response.status(200).entity(comments).build();
        }

        //if request fails, return 404
        return Response.status(404).build();
    }

    @Override
    public Response retrieveBlogEntries() {

        Collection blogCollection = blogEntryMap.values();

        //sets
        return Response.status(200).entity(blogCollection).build();
    }

    @Override
    public void followBlogEntry() {

    }
}
