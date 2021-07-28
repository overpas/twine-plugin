/**
 * Gradle sync utilities
 */

package by.overpass.twap.service

import com.android.tools.idea.gradle.project.sync.GradleSyncInvoker
import com.google.wireless.android.sdk.stats.GradleSyncStats
import com.intellij.openapi.project.Project

/**
 * Can do some actions related to Gradle
 */
interface GradleSyncService {
    /**
     * Triggers project sync
     *
     * @param project the project to trigger Gradle sync in
     */
    fun syncProject(project: Project)

    companion object {
        /**
         * @return default [GradleSyncService] implementation
         */
        operator fun invoke(): GradleSyncService = GradleSyncServiceImpl(GradleSyncInvoker.getInstance())
    }
}

private inline class GradleSyncServiceImpl(
    private val gradleSyncInvoker: GradleSyncInvoker,
) : GradleSyncService {

    override fun syncProject(project: Project) {
        gradleSyncInvoker.requestProjectSync(project, GradleSyncStats.Trigger.TRIGGER_PROJECT_MODIFIED)
    }
}