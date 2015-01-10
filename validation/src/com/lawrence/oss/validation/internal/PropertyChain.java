package com.lawrence.oss.validation.internal;

import com.google.common.base.Joiner;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_wu on 2014/12/15.
 */
public class PropertyChain {
    public PropertyChain(){}
    private List<String> memberNames = new ArrayList<>();
    public PropertyChain(PropertyChain parent){
        if(parent != null){
            memberNames.addAll(parent.memberNames);
        }
    }

    public void add(Member member){
        memberNames.add(member.getName());
    }

    public void add(String propertyName) {
        memberNames.add(propertyName);
    }

    @Override
    public String toString(){
        return Joiner.on(".").join(memberNames);
    }

    public boolean isChildChainOf(PropertyChain parentChain){
        return toString().startsWith(parentChain.toString());
    }

    public String buildPropertyName(String propertyName){
        PropertyChain chain = new PropertyChain(this);
        chain.add(propertyName);
        return  chain.toString();
    }

    public int count(){
        return memberNames.size();
    }
}
