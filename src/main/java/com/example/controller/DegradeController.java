package com.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 熔断降级
 */
@RestController
@Slf4j
public class DegradeController {
    @SentinelResource(value = "sentinel_degrade",fallback = "exceptionHandler")
    @GetMapping("degrade")
    public String degrade(){
        return "hello sentinel";
    }
    //blockHandler函数 原方法调用被限流、降级、系统保护时被调用
    public String exceptionHandler(BlockException e){
        log.error("error",e);
        return "系统繁忙，请稍后";
    }
    @PostConstruct
    private void initDegradeRule(){
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("sentinel_degrade");
        rule.setCount(0.01);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
