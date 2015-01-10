package com.lawrence.oss.validation.internal;

import com.lawrence.oss.validation.Action1;

/**
 * Created by z_wu on 2015/1/4.
 */
public interface IConfigurable<TConfiguration, TNext> {
   TNext configure(Action1 configure);
}
