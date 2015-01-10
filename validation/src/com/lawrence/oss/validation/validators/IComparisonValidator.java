package com.lawrence.oss.validation.validators;

import java.lang.reflect.Member;

/**
 * Created by z_wu on 2014/12/17.
 */
public interface IComparisonValidator extends IPropertyValidator{
    Comparison getComparison();
    Member getMemberToCompare();
    Object getValueToCompare();
}
