package iitu.edu.LibrarySpringBoot.controllers;


import iitu.edu.LibrarySpringBoot.models.Book;
import iitu.edu.LibrarySpringBoot.models.Person;
import iitu.edu.LibrarySpringBoot.services.BooksService;
import iitu.edu.LibrarySpringBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page",required = false) Integer page,
                        @RequestParam(value = "books_per_page",required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear));
        else
            model.addAttribute("books", booksService.findAll(page,booksPerPage,sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", peopleService.findAll());
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("human", book.getPerson());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book){
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("book",booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable int id){
        booksService.update(id,book);
        return "redirect:/books";
    }

    @PatchMapping("/assign/{bookId}")
    public String assignBook(@ModelAttribute("person") Person person, @PathVariable int bookId){
        booksService.setPersonBook(person,bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/release/{id}")
    public String releaseBook(@PathVariable int id){
        booksService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @GetMapping("/search/result")
    public String searchResult(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("books", booksService.findBookStartsWith(name));
        return "books/search";
    }
}
