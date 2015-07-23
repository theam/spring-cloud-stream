/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.binder;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.integration.router.AbstractMappingMessageRouter;

/**
 * A {@link BeanPostProcessor} that sets a {@link BinderAwareChannelResolver} on any bean of type
 * {@link AbstractMappingMessageRouter} within the context.
 * 
 * @author Mark Fisher
 * @author Gary Russell
 */
public class BinderAwareRouterBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

	private final BinderAwareChannelResolver channelResolver;

	public BinderAwareRouterBeanPostProcessor(BinderAwareChannelResolver channelResolver) {
		this.channelResolver = channelResolver;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		channelResolver.setBeanFactory(beanFactory);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof AbstractMappingMessageRouter) {
			((AbstractMappingMessageRouter) bean).setChannelResolver(channelResolver);
		}
		return bean;
	}

}
