

package com.mandira.studio.controller;

import com.mandira.studio.dto.InquiryRequest;
import com.mandira.studio.model.Inquiry;
import com.mandira.studio.repository.InquiryRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryRepository repository;

    public InquiryController(InquiryRepository repository) {
        this.repository = repository;
    }

    // Public: contact form yahan submit hota hai
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody InquiryRequest req) {
        Inquiry inquiry = new Inquiry();
        inquiry.setName(req.getName());
        inquiry.setEmail(req.getEmail());
        inquiry.setMessage(req.getMessage());
        repository.save(inquiry);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Thanks! We'll be in touch soon."));
    }

    // Admin only: saari inquiries (latest pehle)
    @GetMapping
    public List<Inquiry> all() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
}
