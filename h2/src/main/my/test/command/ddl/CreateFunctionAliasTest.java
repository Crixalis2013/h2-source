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

public class CreateFunctionAliasTest extends TestBase {
    public static void main(String[] args) throws Exception {
        new CreateFunctionAliasTest().start();
    }

    @Override
    public void init() throws Exception {
    }

    @Override
    public void startInternal() throws Exception {
        executeUpdate("DROP TABLE IF EXISTS CreateFunctionAliasTest");
        executeUpdate("DROP ALIAS IF EXISTS my_sqrt");
        executeUpdate("DROP ALIAS IF EXISTS my_reverse");

        // 必须是static方法
        executeUpdate("CREATE ALIAS IF NOT EXISTS my_sqrt DETERMINISTIC FOR \"java.lang.Math.sqrt\"");

        executeUpdate("CREATE ALIAS IF NOT EXISTS my_reverse AS "
                + "$$ String reverse(String s) { return new StringBuilder(s).reverse().toString(); } $$");

        sql = "select my_sqrt(4.0), my_reverse('abc')";
        executeQuery();

        executeUpdate("CREATE TABLE IF NOT EXISTS CreateFunctionAliasTest(f1 varchar as my_reverse('abc'))");
        executeUpdate("DROP TABLE IF EXISTS CreateFunctionAliasTest");
        executeUpdate("DROP ALIAS IF EXISTS my_reverse");
    }
}
