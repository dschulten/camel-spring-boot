/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.hystrix.springboot;

import org.apache.camel.component.hystrix.metrics.servlet.HystrixEventStreamServlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet mapping auto-configuration.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "camel.component.hystrix.mapping.enabled", matchIfMissing = true)
@ConditionalOnBean(type = "org.apache.camel.spring.boot.CamelAutoConfiguration")
@AutoConfigureAfter(name = "org.apache.camel.spring.boot.CamelAutoConfiguration")
@ConditionalOnWebApplication
@EnableConfigurationProperties(HystrixMappingConfiguration.class)
public class HystrixMappingAutoConfiguration {

    @Bean
    ServletRegistrationBean hystrixServlet(HystrixMappingConfiguration config) {
        ServletRegistrationBean mapping = new ServletRegistrationBean();
        mapping.setServlet(new HystrixEventStreamServlet());
        mapping.addUrlMappings(config.getPath());
        mapping.setName(config.getServletName());

        return mapping;
    }

}
