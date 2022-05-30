package com.example.controller;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sentinel
 * 注解
 * springcloud
 */
@Slf4j
@RestController
public class AnnotationController {
    /**
     * 定义限流资源和限流回调函数
     * 资源 sentinel_springcloud
     */
    @SentinelResource(value = "sentinel_springcloud",blockHandler = "exceptionHandler")
    @GetMapping("anno")
    public String hello(){
        return "hello sentinel";
    }

    //blockHandler函数 原方法调用被限流、降级、系统保护时被调用
    public String exceptionHandler(BlockException e){
        log.error("error",e);
        return "系统繁忙，请稍后";
    }
}