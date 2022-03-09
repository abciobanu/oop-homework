import java.util.HashMap;

public class PublishingBrand implements IPublishingArtifact {
  private final int id;
  private final String name;
  private final HashMap<Integer, Book> books;

  public PublishingBrand(int id, String name) {
    this.id = id;
    this.name = name;
    this.books = new HashMap<>();
  }

  public int getId() {
    return this.id;
  }

  public HashMap<Integer, Book> getBooks() {
    return this.books;
  }

  public void addBook(Book book) {
    this.books.put(book.getId(), book);
  }

  @Override
  public String Publish() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append("<xml>\n")
        .append("\t<publishingBrand>\n")
        .append("\t\t<ID>").append(this.id).append("</ID>\n")
        .append("\t\t<Name>").append(this.name).append("</Name>\n")
        .append("\t\t<books>\n");

    this.books.forEach((bookId, book) -> {
      // gets current book's properties and indents them properly (with four tab characters)
      String indentedBookProperties = book.getXmlFormattedProperties().replaceAll("(?m)^", "\t\t\t\t");

      stringBuilder
          .append("\t\t\t<book>\n")
          .append(indentedBookProperties)
          .append("\t\t\t</book>\n");
    });

    stringBuilder
        .append("\t\t</books>\n")
        .append("\t</publishingBrand>\n")
        .append("</xml>\n");

    return stringBuilder.toString();
  }
}
