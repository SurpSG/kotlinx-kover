/*
 * Copyright 2017-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.kover.tasks

import kotlinx.kover.api.*
import kotlinx.kover.engines.intellij.*
import kotlinx.kover.engines.jacoco.*
import org.gradle.api.file.*
import org.gradle.api.tasks.*

@CacheableTask
open class KoverHtmlReportTask : KoverCommonTask() {
    /**
     * Specifies file path of generated XML report file with coverage data.
     */
    val htmlReportDir: DirectoryProperty = project.objects.directoryProperty()
        @OutputDirectory get

    @TaskAction
    fun generate() {
        val htmlDirFile = htmlReportDir.get().asFile

        if (coverageEngine.get() == CoverageEngine.INTELLIJ) {
            intellijReport(
                binaryReportFiles.get(),
                srcDirs.get(),
                outputDirs.get(),
                null,
                htmlDirFile,
                classpath.get()
            )
        } else {
            jacocoReport(
                binaryReportFiles.get(),
                srcDirs.get(),
                outputDirs.get(),
                classpath.get(),
                null,
                htmlDirFile,
            )
        }
        project.logger.lifecycle("Kover: HTML report for '${project.name}' file://${htmlDirFile.canonicalPath}/index.html")
    }
}
