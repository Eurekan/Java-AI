package com.eureka.config;

import com.eureka.common.ErrorCode;
import com.eureka.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ZhiPuAIManager {

    @Resource
    private ClientV4 clientV4;

    public String doChat(ChatMessage chatMessage) {
        List<ChatMessage> messages = new ArrayList<>();
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
        String result = invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent().toString();
        log.info("ZhiPuAI Response: {}", result);
        return result;
    }
}
