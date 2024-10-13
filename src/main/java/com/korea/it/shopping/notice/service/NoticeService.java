package com.korea.it.shopping.notice.service;


import com.korea.it.shopping.notice.entity.Notice;
import com.korea.it.shopping.notice.repo.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Notice saveNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    
    public Optional<Notice> findById(Long id) {
        return noticeRepository.findById(id);
    }

    public void deleteById(Long id) {
        noticeRepository.deleteById(id);
    }
}

