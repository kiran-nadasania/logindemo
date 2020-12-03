package com.promptsoftech.logindemo.injection.qualifier

import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import java.lang.annotation.Retention

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class ApplicationContext