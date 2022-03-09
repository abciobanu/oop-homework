import java.io.*;
import java.util.HashMap;

public class AdministrationTest {
  private static void getBooksForPublishingRetailerIdTest() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test-in/books-retailer-id.in"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        int retailerId = Integer.parseInt(line);

        // each retailer's books' properties are written to a separate file
        BufferedWriter bufferedWriter =
            new BufferedWriter(new FileWriter("test-out/books-retailer-" + retailerId + ".out"));

        HashMap<Integer, Book> books = Administration.getBooksForPublishingRetailerId(retailerId);
        books.forEach((bookId, book) -> {
          try {
            bufferedWriter.write(book.Publish());
          } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
          }
        });

        bufferedWriter.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void getLanguagesForPublishingRetailerIdTest() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test-in/languages-retailer-id.in"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        int retailerId = Integer.parseInt(line);

        // each retailer's languages are written to a separate file
        BufferedWriter bufferedWriter =
            new BufferedWriter(new FileWriter("test-out/languages-retailer-" + retailerId + ".out"));

        HashMap<Integer, Language> languages = Administration.getLanguagesForPublishingRetailerId(retailerId);
        languages.forEach((languageId, language) -> {
          try {
            bufferedWriter.write(language.getName() + "\n");
          } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
          }
        });

        bufferedWriter.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void getCountriesForBookIdTest() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test-in/countries-book-id.in"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        int bookId = Integer.parseInt(line);

        // each retailer's countries are written to a separate file
        BufferedWriter bufferedWriter =
            new BufferedWriter(new FileWriter("test-out/countries-book-" + bookId + ".out"));

        HashMap<Integer, Country> countries = Administration.getCountriesForBookId(bookId);
        countries.forEach((countryId, country) -> {
          try {
            bufferedWriter.write(country.getCountryCode() + "\n");
          } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
          }
        });

        bufferedWriter.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void getCommonBooksForRetailerIdsTest() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test-in/common-books-retailer-ids.in"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(" ");
        int retailerId1 = Integer.parseInt(tokens[0]);
        int retailerId2 = Integer.parseInt(tokens[1]);

        // the common books for each pair of retailer ids are written to a separate file
        BufferedWriter bufferedWriter =
            new BufferedWriter(new FileWriter("test-out/common-books-" + retailerId1 + "-" + retailerId2 + ".out"));

        HashMap<Integer, Book> books = Administration.getCommonBooksForRetailerIds(retailerId1, retailerId2);
        books.forEach((bookId, book) -> {
          try {
            bufferedWriter.write(book.Publish());
          } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
          }
        });

        bufferedWriter.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void getAllBooksForRetailerIdsTest() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test-in/all-books-retailer-ids.in"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] tokens = line.split(" ");
        int retailerId1 = Integer.parseInt(tokens[0]);
        int retailerId2 = Integer.parseInt(tokens[1]);

        // the union of books for each pair of retailer ids is written to a separate file
        BufferedWriter bufferedWriter =
            new BufferedWriter(new FileWriter("test-out/all-books-" + retailerId1 + "-" + retailerId2 + ".out"));

        HashMap<Integer, Book> books = Administration.getAllBooksForRetailerIds(retailerId1, retailerId2);
        books.forEach((bookId, book) -> {
          try {
            bufferedWriter.write(book.Publish());
          } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
          }
        });

        bufferedWriter.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    Administration.initializeLibrary();

    File outputDirectory = new File("test-out");
    if (!outputDirectory.exists()) {
      boolean successfullyCreated = outputDirectory.mkdir();
      if (!successfullyCreated) {
        System.err.println("Could not create directory " + outputDirectory.getName());
        System.exit(1);
      }
    }

    getBooksForPublishingRetailerIdTest();
    getLanguagesForPublishingRetailerIdTest();
    getCountriesForBookIdTest();
    getCommonBooksForRetailerIdsTest();
    getAllBooksForRetailerIdsTest();
  }
}
