/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.example.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 为了精简logback.xml文件，同时省去为每个指标新建logger的重复配置，因此这里实现了动态的logger生成策略，无需再对各个指标进行手动配置。
 * <p>
 * 注意：目的不是为了完全取代logback.xml配置文件，而是为了省去重复的配置工作，因此除了指标之外的其他logger依然建议放在配置文件里。
 */
@Data
@Component
// 指定配置文件中的 student 属性与这个 bean绑定
@ConfigurationProperties(prefix = "aitpm.logback")
public class IndexLoggerFactory {
    private String path;
    private boolean printToConsole;
    private boolean compress;
    private Level level;


    /**
     * 创建基于时间的滚动策略
     *
     * @param loggerName
     * @param loggerContext
     * @param appender
     * @return
     */
    private TimeBasedRollingPolicy buildRollingPolicy(String loggerName,
                                                      LoggerContext loggerContext,
                                                      FileAppender appender) {
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();

        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(appender);

        rollingPolicy.setMaxHistory(7);
        rollingPolicy.setFileNamePattern(genLogPath(loggerName, true));

        rollingPolicy.start();

        return rollingPolicy;
    }

    private PatternLayoutEncoder buildEncoder(LoggerContext loggerContext) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();

        encoder.setContext(loggerContext);
        encoder.setCharset(StandardCharsets.UTF_8);
        encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} %X{REQUEST_ID} - %msg%n");

        encoder.start();

        return encoder;
    }

    private ThresholdFilter buildFilter(Level level, LoggerContext loggerContext) {
        ThresholdFilter thresholdFilter = new ThresholdFilter();

        thresholdFilter.setContext(loggerContext);
        thresholdFilter.setLevel(level.levelStr);

        thresholdFilter.start();

        return thresholdFilter;
    }

    /**
     * 生成日志输出路径
     * <p>
     * 例如：
     * /home/aitpm/logs/aitpm-schedule/1001_traffic_flow.log
     * /home/aitpm/logs/aitpm-schedule/history/2019-09-29-1001_traffic_flow.log
     *
     * @param loggerName
     * @param isHistoryPath
     * @return
     */
    private String genLogPath(String loggerName, boolean isHistoryPath) {

        if (!path.endsWith("/")) {
            path = path + "/";
        }

        if (isHistoryPath) {
            path = path + "history/%d{yyyy-MM-dd}-" + loggerName + ".log";
            return compress ? path + ".zip" : path;
        }

        return path + loggerName + ".log";
    }


}
