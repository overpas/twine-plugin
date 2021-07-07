package by.overpass.twap

import by.overpass.twap.action.DialogFactory
import by.overpass.twap.lang.parsing.psi.DefaultPsiElementFactory
import by.overpass.twap.lang.parsing.psi.PsiElementFactory
import by.overpass.twap.lang.reference.identifier.StringResourceRenameProcessorFactory
import by.overpass.twap.service.GradleSyncService

/**
 * Main service locator
 */
object ServiceLocator {

    var gradleSyncServiceField: GradleSyncService? = GradleSyncService()

    /**
     * Provide an instance of [GradleSyncService]
     */
    val gradleSyncService: GradleSyncService get() = gradleSyncServiceField!!

    private var psiElementFactoryField: PsiElementFactory? = DefaultPsiElementFactory

    /**
     * Provide an instance of [PsiElementFactory]
     */
    val psiElementFactory: PsiElementFactory get() = psiElementFactoryField!!

    private var stringResourceRenameProcessorFactoryField: StringResourceRenameProcessorFactory? =
        StringResourceRenameProcessorFactory()

    /**
     * Provide an instance of [StringResourceRenameProcessorFactory]
     */
    val stringResourceRenameProcessorFactory: StringResourceRenameProcessorFactory
        get() = stringResourceRenameProcessorFactoryField!!

    var dialogFactoryField: DialogFactory? = DialogFactory()

    /**
     * Provide an instance of [DialogFactory]
     */
    val dialogFactory: DialogFactory get() = dialogFactoryField!!
}

