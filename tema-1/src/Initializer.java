import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Initializer {
  public static HashMap<Integer, Book> initializeBooks(String filePath, String delimiterRegex) {
    HashMap<Integer, Book> books = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];
        String subtitle = tokens[2];
        String isbn = tokens[3];
        int pageCount = Integer.parseInt(tokens[4]);
        String keywords = tokens[5];
        int languageId = Integer.parseInt(tokens[6]);
        Date createdOn = new SimpleDateFormat("dd.MM.yyyy").parse(tokens[7]);

        Book book = new Book(id, name, subtitle, isbn, pageCount, keywords, languageId, createdOn);
        books.put(id, book);
      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return books;
  }

  public static HashMap<Integer, Language> initializeLanguages(String filePath, String delimiterRegex) {
    HashMap<Integer, Language> languages = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String code = tokens[1];
        String name = tokens[2];

        Language lang = new Language(id, code, name);
        languages.put(id, lang);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return languages;
  }

  public static HashMap<Integer, Author> initializeAuthors(String filePath, String delimiterRegex) {
    HashMap<Integer, Author> authors = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String firstName = tokens[1];
        String lastName = tokens[2];

        Author author = new Author(id, firstName, lastName);
        authors.put(id, author);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return authors;
  }

  public static HashMap<Integer, EditorialGroup> initializeEditorialGroups(String filePath, String delimiterRegex) {
    HashMap<Integer, EditorialGroup> editorialGroups = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];

        EditorialGroup editorialGroup = new EditorialGroup(id, name);
        editorialGroups.put(id, editorialGroup);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return editorialGroups;
  }

  public static HashMap<Integer, PublishingBrand> initializePublishingBrands(String filePath, String delimiterRegex) {
    HashMap<Integer, PublishingBrand> publishingBrands = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];

        PublishingBrand publishingBrand = new PublishingBrand(id, name);
        publishingBrands.put(id, publishingBrand);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return publishingBrands;
  }

  public static HashMap<Integer, PublishingRetailer> initializePublishingRetailers(String filePath,
                                                                                   String delimiterRegex) {
    HashMap<Integer, PublishingRetailer> publishingRetailers = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];

        PublishingRetailer publishingRetailer = new PublishingRetailer(id, name);
        publishingRetailers.put(id, publishingRetailer);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return publishingRetailers;
  }

  public static HashMap<Integer, Country> initializeCountries(String filePath, String delimiterRegex) {
    HashMap<Integer, Country> countries = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int id = Integer.parseInt(tokens[0]);
        String countryCode = tokens[1];

        Country country = new Country(id, countryCode);
        countries.put(id, country);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return countries;
  }

  public static <Owner, Associate> void initializeAssociations(String filePath,
                                                               String delimiterRegex,
                                                               HashMap<Integer, Owner> owners,
                                                               HashMap<Integer, Associate> associates)
      throws InitializationFileException {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      bufferedReader.readLine(); // skips the header

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(delimiterRegex);

        int ownerId = Integer.parseInt(tokens[0]);
        int associateId = Integer.parseInt(tokens[1]);

        Owner owner = owners.get(ownerId);
        if (owner == null) {
          // there is no such owner in the hashmap, with the ownerId being the last id read from file (1st col)
          throw new InitializationFileException(
              "Bad first column id (" + ownerId + ") in initialization file: " + filePath
          );
        }

        Associate associate = associates.get(associateId);
        if (associate == null) {
          // there is no such associate in the hashmap, with the associateId being the last id read from file (2nd col)
          throw new InitializationFileException(
              "Bad second column id (" + associateId + ") in initialization file: " + filePath
          );
        }

        if (owner instanceof Book book && associate instanceof Author author) {
          book.addAuthor(author); // book - author association
        } else if (owner instanceof EditorialGroup group && associate instanceof Book book) {
          group.addBook(book); // editorial group - book association
        } else if (owner instanceof PublishingBrand brand && associate instanceof Book book) {
          brand.addBook(book); // publishing brand - book association
        } else if (owner instanceof PublishingRetailer retailer) {
          if (associate instanceof Country country) {
            retailer.addCountry(country); // publishing retailer - country association
          } else if (associate instanceof IPublishingArtifact artifact) {
            retailer.addArtifact(artifact); // publishing retailer - artifact association
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
