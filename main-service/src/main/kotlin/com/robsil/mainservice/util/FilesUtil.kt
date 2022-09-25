package com.robsil.mainservice.util

import java.nio.file.Files
import java.nio.file.Path

fun Path.createDirectoryIfExists(path: Path) {
    if (!Files.exists(path)) {
        Files.createDirectory(path)
    }
}