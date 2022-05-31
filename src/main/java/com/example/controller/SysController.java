package com.example.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class SysController {
    @SentinelResource(entryType = EntryType.IN)
    @GetMapping("sys")
    public String degrade(){
        return "hello sentinel";
    }
    //blockHandler函数 原方法调用被限流、降级、系统保护时被调用
    public String exceptionHandler(BlockException e){
        log.error("error",e);
        return "系统繁忙，请稍后";
    }
    @PostConstruct
    private void initSystemRule(){
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setQps(200);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }
}
