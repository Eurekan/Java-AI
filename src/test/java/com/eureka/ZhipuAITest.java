package com.eureka;

import com.eureka.common.ErrorCode;
import com.eureka.config.ZhiPuAIManager;
import com.eureka.exception.BusinessException;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ZhipuAITest {
    @Resource
    private ZhiPuAIManager zhiPuAIManager;

    @Test
    public void testZhiPuAI() {
        String response;
        try {
            response = zhiPuAIManager.doChat(new ChatMessage(ChatMessageRole.USER.value(), "请为我讲解搏击俱乐部这部电影的内容？"));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.THIRD_SERVICE_ERROR);
        }
        System.out.println(response);
    }
}

