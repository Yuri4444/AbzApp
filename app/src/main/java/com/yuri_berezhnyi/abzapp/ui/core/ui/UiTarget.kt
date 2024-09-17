package com.yuri_berezhnyi.abzapp.ui.core.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiTarget {

    interface Collect<T> {
        fun collect(owner: LifecycleOwner, collector: FlowCollector<T>)
    }

    interface Update<T> {
        fun map(source: T)
    }

    interface SuspendUpdate<T> {
        suspend fun suspendMap(source: T)
    }

    interface Mutable<T> : Collect<T>, Update<T>, SuspendUpdate<T>

    abstract class SingleUi<T>(
        private val channel: Channel<T> = Channel(Channel.UNLIMITED)
    ) : Mutable<T> {
        override suspend fun suspendMap(source: T) = channel.send(source)

        override fun collect(owner: LifecycleOwner, collector: FlowCollector<T>) {
            owner.lifecycleScope.launch {
                owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    channel.receiveAsFlow().collect(collector)
                }
            }
        }

        override fun map(source: T) {
            channel.trySend(source)
        }
    }

    abstract class AbstractStateFlow<T>(value: T) : Mutable<T>, Same<T> {

        private val stateFlow = MutableStateFlow(value)

        override fun collect(owner: LifecycleOwner, collector: FlowCollector<T>) {
            owner.lifecycleScope.launch {
                owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    stateFlow.collect(collector)
                }
            }
        }

        override fun map(source: T) {
            stateFlow.value = source
        }

        override suspend fun suspendMap(source: T) = stateFlow.emit(source)

        override fun same(source: T): Boolean = source == stateFlow.value

    }
}

interface SimpleUiTarget {

    interface Collect<T> {
        suspend fun collect(collector: FlowCollector<T>)
    }

    interface Update<T> {
        fun map(source: T)
    }

    interface Mutable<T> : Collect<T>, Update<T>, Same<T>

    abstract class AbstractStateFlow<T>(value: T) : Mutable<T> {

        private val stateFlow = MutableStateFlow(value)

        override fun same(source: T): Boolean = source == stateFlow.value

        override suspend fun collect(collector: FlowCollector<T>) = stateFlow.collect(collector)

        override fun map(source: T) {
            stateFlow.value = source
        }
    }

    abstract class SingleUi<T>(
        private val channel: Channel<T> = Channel(Channel.UNLIMITED)
    ) : Mutable<T> {

        override suspend fun collect(collector: FlowCollector<T>) {
            channel.receiveAsFlow().collect(collector)
        }

        override fun map(source: T) {
            channel.trySend(source)
        }

        override fun same(source: T): Boolean = false
    }

}

interface Same<T> {
    fun same(source: T): Boolean
}