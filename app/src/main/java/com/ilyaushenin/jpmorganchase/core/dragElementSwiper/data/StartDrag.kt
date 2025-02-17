package com.ilyaushenin.jpmorganchase.core.dragElementSwiper.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerId

internal data class StartDrag(val id: PointerId, val offset: Offset? = null)