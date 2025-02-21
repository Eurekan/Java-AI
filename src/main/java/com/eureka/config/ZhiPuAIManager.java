package com.eureka.config;

import com.eureka.common.ErrorCode;
import com.eureka.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZhiPuAIManager {

    @Resource
    private ClientV4 clientV4;

    /**
     * 同步调用
     */
    public String doChat(ChatMessage chatMessage) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage prompt = new ChatMessage(ChatMessageRole.SYSTEM.value(), "你是我的学姐，" +
                "回答用暧昧的语气");
        messages.add(prompt);
        if (chatMessage == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        messages.add(chatMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("glm-4-flash")
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
        return invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent().toString();
    }
}
