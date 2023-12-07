import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                '}';
    }
}

class Person {
    private int id;
    private String name;
    private List<Book> booksBorrowed;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        this.booksBorrowed = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooksBorrowed() {
        return booksBorrowed;
    }

    public void borrowBook(Book book) {
        booksBorrowed.add(book);
        book.setAvailable(false);
    }

    public void returnBook(Book book) {
        booksBorrowed.remove(book);
        book.setAvailable(true);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", booksBorrowed=" + booksBorrowed +
                '}';
    }
}

class Library {
    List<Book> books;
    List<Person> p;
    private List<Transaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.p = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addPatron(Person patron) {
        p.add(patron);
    }

    public void checkoutBook(Person patron, Book book) {
        if (book.isAvailable()) {
            patron.borrowBook(book);
            transactions.add(new Transaction(new Date(), book, patron));
            System.out.println("Book checked out successfully.");
        } else {
            System.out.println("Sorry, the book is not available for checkout.");
        }
    }

    public void returnBook(Person patron, Book book) {
        patron.returnBook(book);
        transactions.add(new Transaction(new Date(), book, patron));
        System.out.println("Book returned successfully.");
    }

    public void displayBooks() {
        System.out.println("Books in the library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayPatrons() {
        System.out.println("Patrons in the library:");
        for (Person patron : p) {
            System.out.println(patron);
        }
    }

    public void displayTransactions() {
        System.out.println("Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
    public List<Book> getBooksBorrowedByPatron(Person patron) {
        return patron.getBooksBorrowed();
    }
}

class Transaction {
    private Date date;
    private Book book;
    private Person patron;

    public Transaction(Date date, Book book, Person patron) {
        this.date = date;
        this.book = book;
        this.patron = patron;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", book=" + book.getTitle() +
                ", patron=" + patron.getName() +
                '}';
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("123456", "Java Programming", "John Doe");
        Book book2 = new Book("789012", "Data Structures", "Jane Smith");

        Person patron1 = new Person(1, "Alice");
        Person patron2 = new Person(2, "Bob");

        library.addBook(book1);
        library.addBook(book2);
        library.addPatron(patron1);
        library.addPatron(patron2);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Display Books");
            System.out.println("2. Display Persons");
            System.out.println("3. Add New Person");
            System.out.println("4. Checkout Book");
            System.out.println("5. Return Book");
            System.out.println("6. Display Transactions");
            System.out.println("7. Add New Book");
            System.out.println("8. Display Books Borrowed by a Person");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.displayBooks();
                    break;
                case 2:
                    library.displayPatrons();
                    break;
                case 3:
                System.out.print("Enter Patron Name: ");
                String newPatronName = scanner.next();
                Person newPatron = new Person(library.p.size() + 1, newPatronName);
                library.addPatron(newPatron);
                System.out.println("New patron added successfully.");
                break;
                case 4:
                    System.out.print("Enter Patron ID: ");
                    int patronId = scanner.nextInt();
                    System.out.print("Enter Book ISBN: ");
                    String bookIsbn = scanner.next();
                    Person selectedPatron = null;
                    Book selectedBook = null;

                    for (Person patron : library.p) {
                        if (patron.getId() == patronId) {
                            selectedPatron = patron;
                            break;
                        }
                    }

                    for (Book book : library.books) {
                        if (book.getIsbn().equals(bookIsbn)) {
                            selectedBook = book;
                            break;
                        }
                    }

                    if (selectedPatron != null && selectedBook != null) {
                        library.checkoutBook(selectedPatron, selectedBook);
                    } else {
                        System.out.println("Invalid Patron ID or Book ISBN.");
                    }
                    break;
                case 5:
                    System.out.print("Enter Patron ID: ");
                    patronId = scanner.nextInt();
                    System.out.print("Enter Book ISBN: ");
                    bookIsbn = scanner.next();
                    selectedPatron = null;
                    selectedBook = null;

                    for (Person patron : library.p) {
                        if (patron.getId() == patronId) {
                            selectedPatron = patron;
                            break;
                        }
                    }

                    for (Book book : library.books) {
                        if (book.getIsbn().equals(bookIsbn)) {
                            selectedBook = book;
                            break;
                        }
                    }

                    if (selectedPatron != null && selectedBook != null) {
                        library.returnBook(selectedPatron, selectedBook);
                    } else {
                        System.out.println("Invalid Patron ID or Book ISBN.");
                    }
                    break;
                case 6:
                    library.displayTransactions();
                    break;
                case 7:
                    System.out.print("Enter ISBN: ");
                    String newBookIsbn = scanner.next();
                    System.out.print("Enter Title: ");
                    String newBookTitle = scanner.next();
                    System.out.print("Enter Author: ");
                    String newBookAuthor = scanner.next();
                    Book newBook = new Book(newBookIsbn, newBookTitle, newBookAuthor);
                    library.addBook(newBook);
                    System.out.println("New book added successfully.");
                    break;
                    case 8:
                    System.out.print("Enter Patron ID: ");
                    int patronIdForBooks = scanner.nextInt();
                    Person patronForBooks = null;
                    for (Person patron : library.p) {
                        if (patron.getId() == patronIdForBooks) {
                            patronForBooks = patron;
                            break;
                        }
                    }
                    if (patronForBooks != null) {
                        List<Book> booksBorrowed = library.getBooksBorrowedByPatron(patronForBooks);

                        if (!booksBorrowed.isEmpty()) {
                            System.out.println("Books borrowed by " + patronForBooks.getName() + ":");
                            for (Book book : booksBorrowed) {
                                System.out.println(book);
                            }
                        } else {
                            System.out.println("No books borrowed by " + patronForBooks.getName() + ".");
                        }
                    } else {
                        System.out.println("Invalid Patron ID.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting Library Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
        scanner.close();
    }
}

