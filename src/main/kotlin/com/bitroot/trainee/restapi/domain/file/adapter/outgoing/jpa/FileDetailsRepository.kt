package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetails
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section

interface FileDetailsRepository {

    fun save(fileDetails: FileDetails): String
    fun delete(fileDetails: FileDetails): String
    fun getFileDetailsById(id: Long): FileDetails
    fun getFileDetailsByName(name: String): FileDetails
    fun getFileDetailsBySectionId(section: Set<Section>): List<FileDetails>
    fun getAllFilesForSchool(userId: Long): Set<FileDetails>
    fun renameFile(oldFileName: String, newFileName: String): String
}
