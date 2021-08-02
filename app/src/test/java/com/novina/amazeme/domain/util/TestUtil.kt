package com.novina.amazeme.domain.util

import kotlinx.coroutines.flow.flow

fun getFlow(any: Any) = flow { emit(any) }
