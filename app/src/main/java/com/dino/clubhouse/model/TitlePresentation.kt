package com.dino.clubhouse.model

import com.dino.clubhouse.R
import com.dino.library.dinorecyclerview.ItemViewType

data class TitlePresentation(
    val title: String,
) : ItemViewType {
    override val itemLayoutResId: Int = R.layout.item_chat_title
}
