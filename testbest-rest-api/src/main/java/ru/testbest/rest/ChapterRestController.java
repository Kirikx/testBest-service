package ru.testbest.rest;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.manage.ChapterFullDto;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.service.impl.common.ChapterServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/chapters")
public class ChapterRestController {

    private final ChapterServiceImpl chapterService;

    @GetMapping
    public List<ChapterDto> getChapters() {
        log.info("Get all chapters");
        return chapterService.getChapters();
    }

    @GetMapping("/{id}")
    public ChapterDto getChapter(@PathVariable("id") String id) {
        log.info("Get chapter by id {}", id);
        return chapterService.getChapterById(UUID.fromString(id));
    }

    @PutMapping
    public ChapterFullDto editChapter(@RequestBody ChapterFullDto chapterDto) {
        log.info("Edit chapter {}", chapterDto);
        return chapterService.editChapter(chapterDto);
    }

    @DeleteMapping("/{id}")
    public void deleteChapter(@PathVariable("id") String id) {
        log.info("Delete chapter by id {}", id);
        chapterService.deleteChapterById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public ChapterFullDto createChapter(@RequestBody ChapterFullDto chapterDto) {
        log.info("Create chapter {}", chapterDto);
        return chapterService.createChapter(chapterDto);
    }
}
