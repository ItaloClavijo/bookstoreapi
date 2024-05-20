package com.todotic.bookstoreapi.service;

import com.todotic.bookstoreapi.exception.BadRequestException;
import com.todotic.bookstoreapi.exception.ResourceNotFoundException;
import com.todotic.bookstoreapi.model.dto.BookFormDTO;
import com.todotic.bookstoreapi.model.entity.Book;
import com.todotic.bookstoreapi.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AdminBookService {

    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> paginate(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book create(BookFormDTO bookFormDTO) {
        String slug = bookFormDTO.getSlug();
        boolean slugAlreadyExists = bookRepository.existsBySlug(slug);

        if (slugAlreadyExists) {
            throw new BadRequestException("El slug ya está siendo usado por otro libro.");
        }
//        Book book = new Book();
//        book.setTitle(bookFormDTO.getTitle());
//        book.setDescription(bookFormDTO.getDescription());
//        book.setPrice(bookFormDTO.getPrice());
//        book.setSlug(bookFormDTO.getSlug());
//        book.setCoverPath(bookFormDTO.getCoverPath());
//        book.setFilePath(bookFormDTO.getFilePath());

        Book book = modelMapper.map(bookFormDTO, Book.class);
        book.setCreatedAt(LocalDateTime.now());

        return bookRepository.save(book);
    }

    public Book findById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Book update(Integer id, BookFormDTO bookFormDTO) {
        Book bookFromDb = findById(id);

        String slug = bookFormDTO.getSlug();
        boolean slugAlreadyExists = bookRepository.existsBySlugAndIdNot(slug, id);

        if (slugAlreadyExists) {
            throw new BadRequestException("El slug ya está siendo usado por otro libro.");
        }

//        bookFromDb.setTitle(bookFormDTO.getTitle());
//        bookFromDb.setDescription(bookFormDTO.getDescription());
//        bookFromDb.setPrice(bookFormDTO.getPrice());
//        bookFromDb.setSlug(bookFormDTO.getSlug());
//        bookFromDb.setCoverPath(bookFormDTO.getCoverPath());
//        bookFromDb.setFilePath(bookFormDTO.getFilePath());

        modelMapper.map(bookFormDTO, bookFromDb);
        bookFromDb.setUpdatedAt(LocalDateTime.now());

        return bookRepository.save(bookFromDb);
    }

    public void delete(Integer id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

}
