package com.example.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
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
public class WhiteBlackController {
    @SentinelResource(value = "sentinel_authority",blockHandler = "exceptionHandler")
    @GetMapping("authority")
    public String degrade(){
        return "hello sentinel";
    }
    //blockHandler函数 原方法调用被限流、降级、系统保护时被调用
    public String exceptionHandler(BlockException e){
        log.error("error",e);
        return "系统繁忙，请稍后";
    }
    @PostConstruct
    private void initSystemRule1(){
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("sentinel_authority");
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("127.0.0.1");
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);
    }
    @PostConstruct
    private void initSystemRule2(){
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("sentinel_authority");
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("192.168.18.127");
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);
    }
}
