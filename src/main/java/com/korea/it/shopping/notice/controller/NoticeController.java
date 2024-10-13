package com.korea.it.shopping.notice.controller;


import com.korea.it.shopping.notice.entity.Notice;
import com.korea.it.shopping.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin(origins = "http://localhost:3000") // React 앱과의 통신을 위해 CORS 설정
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long id) {
        Optional<Notice> notice = noticeService.findById(id);
        return notice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @PostMapping
    public Notice createNotice(@RequestBody Notice notice) {
        System.out.println("Received Notice Title: " + notice.getTitle()); // 제목 확인
        System.out.println("Received Notice Date: " + notice.getDate()); // 날짜 확인

        if (notice.getTitle() == null || notice.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        return noticeService.saveNotice(notice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long id, @RequestBody Notice updatedNotice) {
        Optional<Notice> noticeData = noticeService.findById(id);

        if (noticeData.isPresent()) {
            Notice notice = noticeData.get();
            notice.setTitle(updatedNotice.getTitle());
            notice.setDate(updatedNotice.getDate());
            notice.setContent(updatedNotice.getContent());
            noticeService.saveNotice(notice);
            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        try {
            noticeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
