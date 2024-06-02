package com.bitroot.trainee.restapi.domain.file.details.section.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.file.details.section.SectionService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/section")
class SectionController(
    val sectionService: SectionService,
) {

    @PostMapping
    fun save(@RequestParam section: SectionRequest): String =
        sectionService.save(section)

    @PutMapping("/rename/{sectionId}/{oldSectionName}/{newSectionName}")
    fun renameSection(@PathVariable sectionId: Long, @PathVariable oldSectionName: String, @PathVariable newSectionName: String) =
        sectionService.renameSection(sectionId, oldSectionName, newSectionName)

    @DeleteMapping("/{sectionId}")
    fun deleteSection(@PathVariable sectionId: Long) =
        sectionService.delete(sectionId)
}
