package com.example.Controllers;

import com.example.Services.BookService;
import com.example.Services.CartService;
import com.example.Services.OrderService;
import com.example.Services.PersonService;
import com.example.Suppliers.Book;
import com.example.Suppliers.Person;
import com.example.Util.BookValidator;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/templates/admin")
public class AdminController {
    private final BookValidator bookValidator;
    private final BookService bookService;
    private final OrderService orderService;
    private final PersonService personService;
    private final CartService cartService;


    public AdminController(BookValidator bookValidator, BookService bookService, OrderService orderService, PersonService personService, CartService cartService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.orderService = orderService;
        this.personService = personService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String index(){
        return "templates/admin/index";
    }

    @GetMapping("/add-book")
    public String addBook(@ModelAttribute("book") Book book){
        return "templates/admin/addBook";
    }

    @PostMapping("/add-book")
    public String createBook(Model model, @Valid @ModelAttribute("book") Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "templates/admin/addBook";
        model.addAttribute("postCorrect", true);
        model.addAttribute("book", new Book());

        bookService.save(book);
        return "templates/admin/addBook";
    }

    @PostMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/";
    }

    @GetMapping("{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "templates/admin/edit";
    }
    @PostMapping("/{id}/edit")
    public String updateBook(Model model, @PathVariable("id") int id, @Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "templates/admin/edit";
        bookService.update(id,book);
        return "redirect:/admin/bookDetails";
    }

    @GetMapping("/bookDetails")
    public String bookDetails(Model model) {
        model.addAttribute("bookList", bookService.findAll());
        return "templates/admin/bookDetails";
    }



    @GetMapping("/addAdmin")
    public String showAllUser(Model model) {
        List<Person> users = personService.getUsers("ROLE_USER");
        model.addAttribute("users", users);
        return "templates/admin/allUsers";
    }

    @PostMapping("/addAdmin")
    public String addAdmin(@RequestParam("username") String username) {
        Person user = personService.getPerson(username).get();
        user.setRole("ROLE_ADMIN");
        personService.savePerson(user);
        return "redirect:/admin/addAdmin";
    }
}