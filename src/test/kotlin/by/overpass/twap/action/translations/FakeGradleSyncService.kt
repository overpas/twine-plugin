package by.overpass.twap.action.translations

import by.overpass.twap.service.GradleSyncService
import com.intellij.openapi.project.Project

/**
 * Fake [GradleSyncService] implementation to be used in [EditTranslationsAction] test
 */
class FakeGradleSyncService : GradleSyncService {

    override fun syncProject(project: Project) {
        println("Performing Gradle sync [test]")
    }
}