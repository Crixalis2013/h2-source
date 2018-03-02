/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package my.test.command.ddl;

import my.test.TestBase;

public class CreateRoleTest extends TestBase {
    public static void main(String[] args) throws Exception {
        new CreateRoleTest().start();
    }

    // 测试org.h2.command.Parser.parseCreateRole()
    // 和org.h2.command.ddl.CreateRole、org.h2.engine.Role
    @Override
    public void startInternal() throws Exception {
        stmt.executeUpdate("DROP TABLE IF EXISTS CreateRoleTest");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CreateRoleTest (f1 int)");

        stmt.executeUpdate("CREATE ROLE IF NOT EXISTS myrole1");

        stmt.executeUpdate("CREATE ROLE IF NOT EXISTS myrole0");
        stmt.executeUpdate("GRANT INSERT ON CreateRoleTest TO myrole0");
        stmt.executeUpdate("GRANT ALTER ANY SCHEMA TO myrole0");

        stmt.executeUpdate("GRANT SELECT,DELETE ON CreateRoleTest TO myrole1");
        stmt.executeUpdate("GRANT myrole1 TO myrole0");

        stmt.executeUpdate("CREATE USER IF NOT EXISTS sa1 PASSWORD 'abc' ADMIN");

        stmt.executeUpdate("GRANT myrole1 TO sa1");
        stmt.executeUpdate("GRANT SELECT ON CreateRoleTest TO sa1");
        stmt.executeUpdate("REVOKE SELECT ON CreateRoleTest FROM sa1");

        stmt.executeUpdate("DROP ROLE IF EXISTS myrole1");

        stmt.executeUpdate("CREATE ROLE IF NOT EXISTS myrole3");
        stmt.executeUpdate("DROP ROLE IF EXISTS myrole3");
        stmt.executeUpdate("CREATE ROLE IF NOT EXISTS myrole4");
        stmt.executeUpdate("DROP ROLE IF EXISTS myrole4");

    }

}
