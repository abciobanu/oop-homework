import java.util.Date;
import java.util.HashMap;

public class Book implements IPublishingArtifact {
  private final int id;
  private final String name;
  private final String subtitle;
  private final String isbn;
  private final int pageCount;
  private final String keywords;
  private final int languageId;
  private final Date createdOn;
  private final HashMap<Integer, Author> authors;

  public Book(int id, String name, String subtitle, String isbn, int pageCount, String keywords, int languageId,
              Date createdOn) {
    this.id = id;
    this.name = name;
    this.subtitle = subtitle;
    this.isbn = isbn;
    this.pageCount = pageCount;
    this.keywords = keywords;
    this.languageId = languageId;
    this.createdOn = createdOn;
    this.authors = new HashMap<>();
  }

  public int getId() {
    return this.id;
  }

  public int getLanguageId() {
    return this.languageId;
  }

  public void addAuthor(Author author) {
    this.authors.put(author.getId(), author);
  }

  public String getXmlFormattedProperties() {
    StringBuilder properties = new StringBuilder();

    properties
        .append("<title>").append(this.name).append("</title>\n")
        .append("<subtitle>").append(this.subtitle).append("</subtitle>\n")
        .append("<isbn>").append(this.isbn).append("</isbn>\n")
        .append("<pageCount>").append(this.pageCount).append("</pageCount>\n")
        .append("<keywords>").append(this.keywords).append("</keywords>\n")
        .append("<languageID>").append(this.languageId).append("</languageID>\n")
        .append("<createdOn>").append(this.createdOn).append("</createdOn>\n")
        .append("<authors>");

    StringBuilder authorsStringBuilder = new StringBuilder();
    this.authors.forEach((authorId, author) ->
        authorsStringBuilder.append(author.getFirstName()).append(" ").append(author.getLastName()).append("; "));

    // deletes trailing "; " and leading space (if exists)
    authorsStringBuilder.delete(authorsStringBuilder.length() - 2, authorsStringBuilder.length());
    if (authorsStringBuilder.charAt(0) == ' ') {
      authorsStringBuilder.deleteCharAt(0);
    }

    properties
        .append(authorsStringBuilder)
        .append("</authors>\n");

    return properties.toString();
  }

  @Override
  public String Publish() {
    // gets book's properties and indents them properly (with a tab character)
    String indentedProperties = getXmlFormattedProperties().replaceAll("(?m)^", "\t");

    return new StringBuilder()
        .append("<xml>\n")
        .append(indentedProperties)
        .append("</xml>\n")
        .toString();
  }
}
