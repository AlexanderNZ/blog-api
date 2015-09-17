package communityblogger.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
 * Use the Apache Commons library for implementing equals() and hasCode().
 * Apache Commons provides utility classes that simplify the implementation of
 * these methods, such they meet the requirements set out in Javadoc
 * documentation for class Object.
 *
 * Apache Commons is a third-party library. This project's POM file necessarily
 * includes a dependency on the Apache Commons library.
 *
 * See https://commons.apache.org/proper/commons-lang/javadocs/api-3.4/ for the
 * Javadoc.
 */
/*
 * Use the Joda Date/Time library for working with Dates.
 *
 * Similarly to Apache Commons, Joda is not part of the regular JDK and so a
 * dependency on this library is also included in the project's POM file.
 *
 * See http://www.joda.org/joda-time/apidocs/index.html for the Javadoc.
 */

/**
 * Class to represent a blog entry. A BlogEntry object holds the following
 * data:
 * <p/>
 * - ID, a unique ID for the BlogEntry. It is the class user's responsibility
 * to ensure that any ID value that is passed to method setId() is unique.
 * <p/>
 * - timestamp, this stores the time when the BlogEntry is created.
 * <p/>
 * - content, the Blogentry's text content.
 * <p/>
 * - keywords, an optional set of keywords for the BlogEntry.
 * <p/>
 * - author, the BlogEntry's author, which is an instance of the User class.
 * <p/>
 * - comments, a set of comments that have been made on the BlogEntry.
 * <p/>
 * Class BlogEntry is not thread-safe. It is the class user's responsibility to
 * ensure that any concurrent access to BlogEntry objects is managed
 * appropriately.
 *
 * @author Ian Warren
 */
@XmlRootElement
public class BlogEntry {
    private Long _id;
    private DateTime _timestamp;
    private String _content;
    private Set<String> _keywords;
    private User _author;
    private Set<Comment> _comments;

    /**
     * Creates a BlogEntry object.
     */
    public BlogEntry() {
        this(null, null);
    }

    /**
     * Creates a BlogEntry with a specified content.
     */
    public BlogEntry(String content) {
        this(content, null);
    }

    /**
     * Creates a BlogEntry object with a specified content and set of
     * keywords.
     */
    public BlogEntry(String content, Set<String> keywords) {
        _content = content;

        // Create a copy of the keywords parameter; if it's null create an
        // empty set.
        if (keywords == null) {
            _keywords = new HashSet<String>();
        } else {
            _keywords = new HashSet<String>(keywords);
        }

        // Create an empty set to store comments.
        _comments = new HashSet<Comment>();
    }

    /**
     * Returns the unique ID of this BlogEntry.
     */
    @XmlElement
    public Long getId() {
        return _id;
    }

    /**
     * Sets the unique ID for this BlogEntry.
     * <p/>
     * Note that it's the caller's responsibility to make sure that the ID
     * value is unique (i.e. that it's not already used by an existing
     * BlogEntry instance.
     *
     * @param id the ID Value.
     */
    public void setId(Long id) {
        _id = id;
    }

    /**
     * Returns the date/time at which this BlogEntry was posted.
     */
    public DateTime getTimePosted() {
        return _timestamp;
    }

    /**
     * Sets the timestamp for this BlogEntry.
     */
    public void setTimePosted(DateTime timestamp) {
        _timestamp = timestamp;
    }

    /**
     * Returns this BlogEntry's content.
     */
    @XmlElement
    public String getContent() {
        return _content;
    }

    /**
     * Sets the content text for this BlogEntry.
     */
    public void setContent(String content) {
        _content = content;
    }

    /**
     * Returns this BlogEntry's set of keywords.
     */
    @XmlElement
    public Set<String> getKeywords() {
        // Return an unmodifiable set so that contents can't be changed.
        return Collections.unmodifiableSet(_keywords);
    }

    /**
     * Sets the set of keywords for this BlogEntry.
     */
    public void setKeywords(Set<String> keywords) {
        for (String keyword : keywords) {
            _keywords.add(keyword);
        }
    }

    /**
     * Returns this BlogEntry's author.
     */
    @XmlElement
    public User getAuthor() {
        return _author;
    }

    /**
     * Sets the User who has authored this BlogEntry.
     * <p/>
     * Note that this method has package visibility, so can only be called by
     * classes within the same package. This is a form of encapsulation that
     * prevents unintended use of this method. Class User calls this method to
     * establish a bidirectional link between a User and a BlogEntry; class
     * User calls this method in its addBlogEntry() method.
     *
     * @param user the User who posted this BlogEntry.
     */
    void setAuthor(User user) {
        _author = user;
    }

    /**
     * Returns this BlogEntry's set of Comments.
     */
    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(_comments);
    }

    /**
     * Adds a Comment to this BlogEntry object.
     *
     * @param comment the comment that applies to this BlogEntry.
     */
    public void addComment(Comment comment) {
        if (_comments.contains(comment)) {
            throw new IllegalArgumentException();
        }
        _comments.add(comment);
    }

    /**
     * Return true if this BlogEntry object is equal in value to the method argument.
     * A BlogEntry's ID is assumed to be unique and so this equality test is
     * implemented to check the values of the ID attribute.
     * <p/>
     * Note that a BlogEntry object is assumed to have its ID set.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlogEntry))
            return false;
        if (obj == this)
            return true;

        BlogEntry rhs = (BlogEntry) obj;
        return new EqualsBuilder().
                append(_id, rhs._id).
                isEquals();
    }

    /**
     * Returns a hashcode for this User object.
     */
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 31);
        builder.append(_id).
                append(_content).
                append(_timestamp);

        if (_author != null) {
            builder.append(_author.getUsername());
        }

        return builder.toHashCode();
    }

    /**
     * Returns a String representation of this User object.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[BlogEntry:");

        buffer.append(" id=");
        if (_id != null) {
            buffer.append(_id);
        } else {
            buffer.append("null");
        }

        buffer.append(", author=");
        if (_author != null) {
            buffer.append(_author.getUsername());
        } else {
            buffer.append("null");
        }

        buffer.append(", content=\"");
        if (_content != null) {
            buffer.append(_content);
            buffer.append("\"");
        } else {
            buffer.append("null");
        }

        buffer.append(", timestamp=");
        if (_timestamp != null) {
            buffer.append(_timestamp.toString());
        } else {
            buffer.append("null");
        }

        buffer.append(", #comments=");
        buffer.append(_comments.size());

        buffer.append(", #keywords=");
        buffer.append(_keywords.size());

        buffer.append("]");
        return buffer.toString();
    }
}
