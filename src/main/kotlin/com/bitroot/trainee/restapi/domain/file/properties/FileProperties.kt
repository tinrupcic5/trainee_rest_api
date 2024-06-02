package com.bitroot.trainee.restapi.domain.file.properties

import com.bitroot.trainee.restapi.appsettings.AppSettings
import com.bitroot.trainee.restapi.appsettings.isVideo
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetails
import com.bitroot.trainee.restapi.domain.file.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.toDto
import com.google.api.ResourceProto.resource
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Collectors

@Component
class FileProperties(
    @Value("\${spring.servlet.multipart.location}")
    private val filePath: String,
) {
    private val filePathToVideo: String = "video/"
    private val filePathToPictures: String = "image/"
    private val logger = KotlinLogging.logger { }

    /**
     *  It is typically used in a web application where users can download files stored on the server.
     *  NOT USED NOW
     */
    fun downloadFile(fileName: String): ResponseEntity<Resource> {
        val filePath: Path = Paths.get(filePath + filePathToVideo, fileName)
        val resource: Resource = UrlResource(filePath.toUri())

        if (resource.exists()) {
            val contentDisposition: ContentDisposition = ContentDisposition.builder("attachment")
                .filename(resource.filename)
                .build()

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_OCTET_STREAM
            headers.contentDisposition = contentDisposition

            return ResponseEntity.ok()
                .headers(headers)
                .body(resource)
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    fun uploadFile(schoolName: String, sectionName: String, file: MultipartFile): String {
        if (file.isEmpty) {
            logger.info("File is empty")
            return "File is empty"
        }

        val baseFilePath = if (isVideo(file)) {
            "$filePath$schoolName${File.separator}$sectionName${File.separator}$filePathToVideo"
        } else {
            "$filePath$schoolName${File.separator}$sectionName${File.separator}$filePathToPictures"
        }

        val uploadDirectory: Path = Paths.get(baseFilePath)

        createFolderIfNotExists(baseFilePath)

        val targetLocation: Path = uploadDirectory.resolve(file.originalFilename)

        println("Save Location: $baseFilePath")
        file.transferTo(targetLocation)
        return "File uploaded successfully"
    }

    fun createFolderIfNotExists(folderPath: String) {
        val folder = File(folderPath)

        if (!folder.exists()) {
            folder.mkdirs()
            println("Folder created: $folderPath")
        } else {
            println("Folder already exists: $folderPath")
        }
    }

    fun renameFile(schoolName: String, sectionName: String, oldFileName: String, newFileName: String): String {
        val directoryPath: Path =
            Paths.get("$filePath$schoolName${File.separator}$sectionName${File.separator}$filePathToVideo")
        val matchingFiles = Files.list(directoryPath)
            .filter { Files.isRegularFile(it) }
            .filter { it.fileName.toString().startsWith(oldFileName) }
            .collect(Collectors.toList())

        if (matchingFiles.isEmpty()) {
            logger.info("No files found with the specified oldFileName")
            return "No files found with the specified oldFileName"
        }

        return try {
            for (file in matchingFiles) {
                val fileExtension = file.fileName.toString().substringAfterLast('.')
                val newFileNameWithExtension = "$newFileName.$fileExtension"
                val newFilePath: Path = file.resolveSibling(newFileNameWithExtension)
                Files.move(file, newFilePath, StandardCopyOption.REPLACE_EXISTING)
            }
            logger.info("Files renamed successfully")
            "Files renamed successfully"
        } catch (e: Exception) {
            logger.error("Failed to rename files: ${e.message}")
            "Failed to rename files: ${e.message}"
        }
    }

    fun deleteFolder(schoolName: String, sectionName: String) {
        val baseFilePath = "$filePath$schoolName${File.separator}$sectionName"

        val directoryPath: Path = Paths.get(baseFilePath)

        try {
            Files.walk(directoryPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete)
            println("Folder deleted successfully: $baseFilePath")
        } catch (e: Exception) {
            println("Failed to delete folder: ${e.message}")
        }
    }

    fun deleteFile(schoolName: String, sectionName: String, videoNameToDelete: String, fileType: String): String {
        val baseFilePath = if (fileType == AppSettings.VIDEO) {
            "$filePath$schoolName${File.separator}$sectionName${File.separator}$filePathToVideo"
        } else {
            "$filePath$schoolName${File.separator}$sectionName${File.separator}$filePathToPictures"
        }

        val directoryPath: Path =
            Paths.get(baseFilePath)

        val allFiles = Files.list(directoryPath)
            .filter { Files.isRegularFile(it) }
            .collect(Collectors.toList())

        val matchingFiles = allFiles.filter { file ->
            val fileNameWithoutExtension = StringUtils.stripFilenameExtension(file.fileName.toString())
            videoNameToDelete == fileNameWithoutExtension
        }

        if (matchingFiles.isEmpty()) {
            return "File not found: $videoNameToDelete"
        }

        try {
            for (fileToDelete in matchingFiles) {
                Files.delete(fileToDelete)
            }
            return "File(s) deleted successfully: $videoNameToDelete"
        } catch (e: Exception) {
            return "Failed to delete file(s): ${e.message}"
        }
    }
    fun streamFile(fileDetails: FileDetails): ResponseEntity<Resource> {
        val baseFilePath = if (fileDetails.fileType.value == AppSettings.VIDEO) {
            "$filePath${fileDetails.section.userDetails.schoolDetails.school.schoolName.value}${File.separator}${fileDetails.section.name.value}${File.separator}$filePathToVideo"
        } else {
            "$filePath${fileDetails.section.userDetails.schoolDetails.school.schoolName.value}${File.separator}${fileDetails.section.name.value}${File.separator}$filePathToPictures"
        }

        val directory = Paths.get(baseFilePath).toFile()
        val foundFile = directory.listFiles()?.find { it.name.startsWith(fileDetails.name.value) }

        return if (foundFile != null && foundFile.exists()) {
            val resource = InputStreamResource(FileInputStream(foundFile))
            val headers = HttpHeaders().apply {
                contentType = if (fileDetails.fileType.value == AppSettings.VIDEO) MediaType.valueOf("video/mp4") else MediaType.valueOf("image/jpeg")
                contentLength = foundFile.length()
            }
            ResponseEntity.ok().headers(headers).body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    fun downloadFiles(names: List<FileDetails>): Set<ResourceDto> {
        val downloadedResources: MutableSet<ResourceDto> = HashSet()

        names.forEach { fileDetails ->
            val baseFilePath = if (fileDetails.fileType.value == AppSettings.VIDEO) {
                "$filePath${fileDetails.section.userDetails.schoolDetails.school.schoolName.value}${File.separator}${fileDetails.section.name.value}${File.separator}$filePathToVideo"
            } else {
                "$filePath${fileDetails.section.userDetails.schoolDetails.school.schoolName.value}${File.separator}${fileDetails.section.name.value}${File.separator}$filePathToPictures"
            }

            try {
                val filesInFolder = Paths.get(baseFilePath).toFile().list()

                // Search for a file with the same name in the folder
                val foundFileName = filesInFolder?.find { it.startsWith(fileDetails.name.value) }
                if (foundFileName != null) {
                    val filePath: Path = Paths.get(baseFilePath, foundFileName)
                    val resource: Resource = UrlResource(filePath.toUri())

                    if (resource.exists()) {
                        downloadedResources.add(
                            ResourceDto(
                                section = fileDetails.section.toDto(resource),
                                fileDetails = fileDetails.toDto(foundFileName),
                                fileViewStatus = fileDetails.fileViewStatus,
                                createdAt = fileDetails.createdAt,
                            ),
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle the exception if needed
            }
        }

        return downloadedResources
    }

}
