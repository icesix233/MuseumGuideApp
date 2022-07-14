package com.example.wifilocation997.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.example.wifilocation997.activity.ChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.modules.chat.EaseChatLayout;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;
import com.hyphenate.easeui.modules.conversation.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;

public class ConversationListFragment extends EaseConversationListFragment {
    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        //lzl呕心沥血
        Object item = conversationListLayout.getItem(position).getInfo();
        if(item instanceof EMConversation) {
            //ChatActivity.actionStart(mContext, ((EMConversation)item).conversationId(), EaseCommonUtils.getChatType((EMConversation) item));
            ChatActivity.actionStart(mContext, ((EMConversation)item).conversationId(), EaseCommonUtils.getChatType((EMConversation) item));
        }
    }
}
