package com.novina.amazeme.util

import kotlinx.coroutines.flow.flow

fun getFlow(any: Any) = flow { emit(any) }
