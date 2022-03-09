import java.util.HashMap;

public class Administration {
  private static HashMap<Integer, Book> books;
  private static HashMap<Integer, Language> languages;
  private static HashMap<Integer, Author> authors;
  private static HashMap<Integer, EditorialGroup> editorialGroups;
  private static HashMap<Integer, PublishingBrand> publishingBrands;
  private static HashMap<Integer, PublishingRetailer> publishingRetailers;
  private static HashMap<Integer, Country> countries;

  public static HashMap<Integer, Book> getBooksForPublishingRetailerId(int publishingRetailerId) {
    HashMap<Integer, Book> publishedBooks = new HashMap<>();

    PublishingRetailer retailer = publishingRetailers.get(publishingRetailerId);
    if (retailer == null) {
      System.out.println("There is no retailer with id " + publishingRetailerId);
      return publishedBooks;
    }

    HashMap<Integer, IPublishingArtifact> artifacts = retailer.getPublishingArtifacts();

    /*
     * for each artifact, if it is a book, it gets inserted in publishedBooks (if it doesn't already exist in the
     * hashmap); if the artifact is an editorial group or a publishing brand, each book of the corresponding entity is
     * obtained and then inserted in publishedBooks (if it doesn't already exist in the hashmap);
     */
    artifacts.forEach((artifactKey, artifactValue) -> {
      if (artifactValue instanceof Book book) {
        if (!publishedBooks.containsKey(artifactKey)) {
          publishedBooks.put(artifactKey, book);
        }
      } else if (artifactValue instanceof EditorialGroup group) {
        HashMap<Integer, Book> groupBooks = group.getBooks();

        groupBooks.forEach((bookId, book) -> {
          if (!publishedBooks.containsKey(bookId)) {
            publishedBooks.put(bookId, book);
          }
        });
      } else if (artifactValue instanceof PublishingBrand brand) {
        HashMap<Integer, Book> brandBooks = brand.getBooks();

        brandBooks.forEach((bookId, book) -> {
          if (!publishedBooks.containsKey(bookId)) {
            publishedBooks.put(bookId, book);
          }
        });
      }
    });

    return publishedBooks;
  }

  public static HashMap<Integer, Language> getLanguagesForPublishingRetailerId(int publishingRetailerId) {
    HashMap<Integer, Language> publishedLanguages = new HashMap<>();

    HashMap<Integer, Book> retailerBooks = getBooksForPublishingRetailerId(publishingRetailerId);
    retailerBooks.forEach((bookId, book) -> {
      int languageId = book.getLanguageId();

      // no duplicate languages are wanted
      if (!publishedLanguages.containsKey(languageId)) {
        Language language = languages.get(languageId);
        publishedLanguages.put(languageId, language);
      }
    });

    return publishedLanguages;
  }

  public static HashMap<Integer, Country> getCountriesForBookId(int bookId) {
    HashMap<Integer, Country> targetCountries = new HashMap<>();

    publishingRetailers.forEach((retailerId, retailer) -> {
      HashMap<Integer, Book> retailerBooks = getBooksForPublishingRetailerId(retailerId);

      // if current retailer has the book with id bookId, insert the retailer's countries in targetCountries
      if (retailerBooks.containsKey(bookId)) {
        HashMap<Integer, Country> retailerCountries = retailer.getCountries();
        retailerCountries.forEach((countryId, country) -> {
          // no duplicate countries are wanted
          if (!targetCountries.containsKey(countryId)) {
            targetCountries.put(countryId, country);
          }
        });
      }
    });

    if (targetCountries.isEmpty()) {
      System.out.println("There is no book with id " + bookId);
    }

    return targetCountries;
  }

  public static HashMap<Integer, Book> getCommonBooksForRetailerIds(int retailerId1, int retailerId2) {
    HashMap<Integer, Book> commonBooks = new HashMap<>();

    HashMap<Integer, Book> retailerBooks1 = getBooksForPublishingRetailerId(retailerId1);
    HashMap<Integer, Book> retailerBooks2 = getBooksForPublishingRetailerId(retailerId2);

    // if any of the two obtained hashmaps is empty, an empty hashmap will be returned
    if (!retailerBooks2.isEmpty()) {
      retailerBooks1.forEach((bookId, book) -> {
        if (retailerBooks2.containsKey(bookId)) {
          commonBooks.put(bookId, book);
        }
      });
    }

    return commonBooks;
  }

  public static HashMap<Integer, Book> getAllBooksForRetailerIds(int retailerId1, int retailerId2) {
    HashMap<Integer, Book> retailerBooks1 = getBooksForPublishingRetailerId(retailerId1);
    HashMap<Integer, Book> retailerBooks2 = getBooksForPublishingRetailerId(retailerId2);

    HashMap<Integer, Book> allBooks = new HashMap<>(retailerBooks1);

    retailerBooks2.forEach((bookId, book) -> {
      // no duplicate books are wanted (union)
      if (!allBooks.containsKey(bookId)) {
        allBooks.put(bookId, book);
      }
    });

    return allBooks;
  }

  public static void initializeLibrary() {
    final String DELIMITER = "###";

    books = Initializer.initializeBooks("init/books.in", DELIMITER);
    languages = Initializer.initializeLanguages("init/languages.in", DELIMITER);
    authors = Initializer.initializeAuthors("init/authors.in", DELIMITER);
    editorialGroups = Initializer.initializeEditorialGroups("init/editorial-groups.in", DELIMITER);
    publishingBrands = Initializer.initializePublishingBrands("init/publishing-brands.in", DELIMITER);
    publishingRetailers = Initializer.initializePublishingRetailers("init/publishing-retailers.in", DELIMITER);
    countries = Initializer.initializeCountries("init/countries.in", DELIMITER);

    try {
      Initializer.initializeAssociations("init/books-authors.in", DELIMITER, books, authors);
      Initializer.initializeAssociations("init/editorial-groups-books.in", DELIMITER, editorialGroups, books);
      Initializer.initializeAssociations("init/publishing-brands-books.in", DELIMITER, publishingBrands, books);

      Initializer.initializeAssociations(
          "init/publishing-retailers-countries.in",
          DELIMITER,
          publishingRetailers,
          countries
      );

      Initializer.initializeAssociations(
          "init/publishing-retailers-books.in",
          DELIMITER,
          publishingRetailers,
          books
      );

      Initializer.initializeAssociations(
          "init/publishing-retailers-editorial-groups.in",
          DELIMITER,
          publishingRetailers,
          editorialGroups
      );

      Initializer.initializeAssociations(
          "init/publishing-retailers-publishing-brands.in",
          DELIMITER,
          publishingRetailers,
          publishingBrands
      );
    } catch (InitializationFileException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
