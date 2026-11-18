package com.example.mrbtech.presentation.unit_screen

import com.example.mrbtech.domain.model.Unit

sealed class UnitScreenUIEvent {


    data class DownloadFile(val url: String,val name: String,val unit: Int): UnitScreenUIEvent()
}