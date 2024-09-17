package com.yuri_berezhnyi.abzapp.ui.core.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions

interface NavigationStrategy {
    fun navigate(
        directionId: Int,
        bundle: Bundle? = null,
        optionsBuilder: (NavOptions.Builder.() -> Unit)? = null
    )

    fun navigate(
        directionId: Int,
        optionsBuilder: (NavOptions.Builder.() -> Unit)? = null
    )

    fun goBack()

    fun setStartDestination(id: Int)

    class Main(
        private val navigationTarget: SimpleUiTarget.Update<NavigationCommand>,
    ) : NavigationStrategy {

        private val navOptionsBuilder: NavOptions.Builder = NavOptions.Builder()

        private val navOptionsBuilderWithoutAnimation: NavOptions.Builder = NavOptions.Builder()

        override fun navigate(
            directionId: Int,
            bundle: Bundle?,
            optionsBuilder: (NavOptions.Builder.() -> Unit)?
        ) = navigationTarget.map {
            it.navigateSafely(
                directionId,
                bundle,
                optionsBuilder,
                navOptionsBuilder
            )
        }

        override fun navigate(
            directionId: Int,
            optionsBuilder: (NavOptions.Builder.() -> Unit)?
        ) = navigationTarget.map {
            it.navigateSafely(
                directionId,
                null,
                optionsBuilder,
                navOptionsBuilder
            )
        }

        override fun goBack() = navigationTarget.map {
            it.navigateUp()
        }

        override fun setStartDestination(id: Int) = navigationTarget.map {
            it.graph.setStartDestination(id)
        }

    }

    fun NavController.navigateSafely(
        directionId: Int,
        bundle: Bundle?,
        optionsBuilder: (NavOptions.Builder.() -> Unit)? = null,
        navOptions: NavOptions.Builder
    ) {
        val currentDestination = this.currentDestination
        if (currentDestination != null) {
            val navAction = currentDestination.getAction(directionId)
            if (navAction != null) {
                val destinationId: Int = navAction.destinationId
                val currentNode: NavGraph? =
                    if (currentDestination is NavGraph) currentDestination else currentDestination.parent
                if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                    val finalNavOptions = navOptions.apply {
                        setLaunchSingleTop(false)
                        setPopUpTo(currentDestination.id, false)
                    }

                    if (optionsBuilder != null) {
                        finalNavOptions.apply(optionsBuilder)
                    }

                    this.navigate(
                        directionId,
                        bundle,
                        finalNavOptions.build()
                    )
                }
            }
        }
    }
}