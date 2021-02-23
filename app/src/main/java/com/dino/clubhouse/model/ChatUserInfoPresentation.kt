package com.dino.clubhouse.model

import com.dino.clubhouse.R
import com.dino.clubhouse.remote.model.ChannelResponse
import com.dino.library.dinorecyclerview.ItemViewType

data class ChatUserInfoPresentation(
    val user: ChannelResponse.User
): ItemViewType {
    override val itemLayoutResId: Int = R.layout.item_chat_user
}
