/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.codehaus.groovy.runtime.callsite;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaClass;

import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

/**
 * Call site for invoking static methods
 *   meta class  - cached
 *   method - not cached
 *
 * @author Alex Tkachman
 */
public class StaticMetaClassSite extends MetaClassSite {

    private final ClassInfo classInfo;
    private final int version;

    public StaticMetaClassSite(CallSite site, MetaClass metaClass) {
        super(site, metaClass);
        classInfo = ClassInfo.getClassInfo(metaClass.getTheClass());
        version = classInfo.getVersion();
    }

    private boolean checkCall(Object receiver) {
        return receiver == metaClass.getTheClass()
            && version == classInfo.getVersion(); // metaClass still be valid
    }

    public final Object call(Object receiver, Object[] args) throws Throwable {
        if (checkCall(receiver)) {
            try {
                return metaClass.invokeStaticMethod(receiver, name, args);
            } catch (GroovyRuntimeException gre) {
                throw ScriptBytecodeAdapter.unwrap(gre);
            }
        } else {
            return CallSiteArray.defaultCall(this, receiver, args);
        }
    }

    public final Object callStatic(Class receiver, Object[] args) throws Throwable {
        if (checkCall(receiver)) {
            try {
                return metaClass.invokeStaticMethod(receiver, name, args);
            } catch (GroovyRuntimeException gre) {
                throw ScriptBytecodeAdapter.unwrap(gre);
            }
        } else {
            return CallSiteArray.defaultCallStatic(this, receiver, args);
        }
    }
}
