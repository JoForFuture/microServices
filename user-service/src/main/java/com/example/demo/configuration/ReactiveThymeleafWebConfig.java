package com.example.demo.configuration;

/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */


import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring6.view.reactive.ThymeleafReactiveViewResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(ThymeleafProperties.class)
@RequiredArgsConstructor
public class ReactiveThymeleafWebConfig {
	
	ISpringWebFluxTemplateEngine iSpringWebFluxTemplateEngine;
//
//	@Bean
//	  ISpringWebFluxTemplateEngine iSpringWebFluxTemplateEngine()
//	    {
//	    	SpringWebFluxTemplateEngine templateEngine=new SpringWebFluxTemplateEngine();
//	    	return templateEngine;
//	    }
 

    @Bean
    public ThymeleafReactiveViewResolver thymeleafChunkedAndDataDrivenViewResolver() {
        final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(iSpringWebFluxTemplateEngine);
        viewResolver.setOrder(1);
        viewResolver.setResponseMaxChunkSizeBytes(8192); // OUTPUT BUFFER size limit
      
        return viewResolver;
    }
    
    
  
   

}
