package com.bludash.emulator.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.bludash.emulator.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.bludash.emulator.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.bludash.emulator.domain.User.class.getName());
            createCache(cm, com.bludash.emulator.domain.Authority.class.getName());
            createCache(cm, com.bludash.emulator.domain.User.class.getName() + ".authorities");
            createCache(cm, com.bludash.emulator.domain.BluForm.class.getName());
            createCache(cm, com.bludash.emulator.domain.BluForm.class.getName() + ".bluFields");
            createCache(cm, com.bludash.emulator.domain.BluForm.class.getName() + ".bluFormData");
            createCache(cm, com.bludash.emulator.domain.BluField.class.getName());
            createCache(cm, com.bludash.emulator.domain.BluFormData.class.getName());
            createCache(cm, com.bludash.emulator.domain.BluFormData.class.getName() + ".bluFieldStringValues");
            createCache(cm, com.bludash.emulator.domain.BluFormData.class.getName() + ".bluFieldCurrencyValues");
            createCache(cm, com.bludash.emulator.domain.BluFormData.class.getName() + ".bluFieldNumberValues");
            createCache(cm, com.bludash.emulator.domain.BluFieldStringValue.class.getName());
            createCache(cm, com.bludash.emulator.domain.BluFieldNumberValue.class.getName());
            createCache(cm, com.bludash.emulator.domain.BluFieldCurrencyValue.class.getName());
            createCache(cm, com.bludash.emulator.domain.DataSet.class.getName());
            createCache(cm, com.bludash.emulator.domain.DataSet.class.getName() + ".dataModels");
            createCache(cm, com.bludash.emulator.domain.Query.class.getName());
            createCache(cm, com.bludash.emulator.domain.Query.class.getName() + ".records");
            createCache(cm, com.bludash.emulator.domain.DataModel.class.getName());
            createCache(cm, com.bludash.emulator.domain.Record.class.getName());
            createCache(cm, com.bludash.emulator.domain.Record.class.getName() + ".queryData");
            createCache(cm, com.bludash.emulator.domain.QueryData.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
