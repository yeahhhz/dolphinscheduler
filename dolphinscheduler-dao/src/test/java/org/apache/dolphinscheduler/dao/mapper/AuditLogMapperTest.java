/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.dao.mapper;

import com.google.common.collect.Lists;
import org.apache.dolphinscheduler.common.enums.AuditObjectType;
import org.apache.dolphinscheduler.dao.BaseDaoTest;
import org.apache.dolphinscheduler.dao.entity.AuditLog;
import org.apache.dolphinscheduler.dao.entity.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class AuditLogMapperTest extends BaseDaoTest {

    @Autowired
    private AuditLogMapper logMapper;

    @Autowired
    private ProjectMapper projectMapper;

    private void insertOne(AuditObjectType objectType) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(1);
        auditLog.setObjectName("name");
        auditLog.setDetail("detail");
        auditLog.setDuration(1L);
        auditLog.setTime(new Date());
        auditLog.setObjectType(objectType.getCode());
        auditLog.setOperationType(0);
        auditLog.setObjectId(1L);
        logMapper.insert(auditLog);
    }

    private Project insertProject() {
        Project project = new Project();
        project.setName("ut project");
        project.setUserId(111);
        project.setCode(1L);
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());
        projectMapper.insert(project);
        return project;
    }

    /**
     * test page query
     */
    @Test
    public void testQueryAuditLog() {
        insertOne(AuditObjectType.USER);
        insertOne(AuditObjectType.PROJECT);
        Page<AuditLog> page = new Page<>(1, 3);
        List<Integer> objectTypeCodeList = Lists.newArrayList(1);
        List<Integer> operationTypeCodeList = Lists.newArrayList(1);

        IPage<AuditLog> logIPage = logMapper.queryAuditLog(page, objectTypeCodeList, operationTypeCodeList, "", null, null);
        Assertions.assertNotEquals(0, logIPage.getTotal());
    }
}
