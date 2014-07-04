/**
 *
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
package org.apache.hadoop.hbase.mapred;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.apache.hadoop.hbase.SmallTests;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(SmallTests.class)
public class TestSplitTable {

  @Test
  @SuppressWarnings("deprecation")
  public void testSplitTableCompareTo() {
    TableSplit aTableSplit = new TableSplit(Bytes.toBytes("tableA"),
        Bytes.toBytes("aaa"), Bytes.toBytes("ddd"), "locationA");

    TableSplit bTableSplit = new TableSplit(Bytes.toBytes("tableA"),
        Bytes.toBytes("iii"), Bytes.toBytes("kkk"), "locationA");

    TableSplit cTableSplit = new TableSplit(Bytes.toBytes("tableA"),
        Bytes.toBytes("lll"), Bytes.toBytes("zzz"), "locationA");

    assertTrue(aTableSplit.compareTo(aTableSplit) == 0);
    assertTrue(bTableSplit.compareTo(bTableSplit) == 0);
    assertTrue(cTableSplit.compareTo(cTableSplit) == 0);

    assertTrue(aTableSplit.compareTo(bTableSplit) < 0);
    assertTrue(bTableSplit.compareTo(aTableSplit) > 0);

    assertTrue(aTableSplit.compareTo(cTableSplit) < 0);
    assertTrue(cTableSplit.compareTo(aTableSplit) > 0);

    assertTrue(bTableSplit.compareTo(cTableSplit) < 0);
    assertTrue(cTableSplit.compareTo(bTableSplit) > 0);

    assertTrue(cTableSplit.compareTo(aTableSplit) > 0);
  }

  @Test
  @SuppressWarnings("deprecation")
  public void testSplitTableEquals() {
    byte[] tableA = Bytes.toBytes("tableA");
    byte[] aaa = Bytes.toBytes("aaa");
    byte[] ddd = Bytes.toBytes("ddd");
    String locationA = "locationA";

    TableSplit tablesplit = new TableSplit(tableA, aaa, ddd, locationA);

    TableSplit tableB = new TableSplit(Bytes.toBytes("tableB"), aaa, ddd, locationA);
    assertNotEquals(tablesplit.hashCode(), tableB.hashCode());
    assertNotEquals(tablesplit, tableB);

    TableSplit startBbb = new TableSplit(tableA, Bytes.toBytes("bbb"), ddd, locationA);
    assertNotEquals(tablesplit.hashCode(), startBbb.hashCode());
    assertNotEquals(tablesplit, startBbb);

    TableSplit endEee = new TableSplit(tableA, aaa, Bytes.toBytes("eee"), locationA);
    assertNotEquals(tablesplit.hashCode(), endEee.hashCode());
    assertNotEquals(tablesplit, endEee);

    TableSplit locationB = new TableSplit(tableA, aaa, ddd, "locationB");
    assertNotEquals(tablesplit.hashCode(), locationB.hashCode());
    assertNotEquals(tablesplit, locationB);

    TableSplit same = new TableSplit(tableA, aaa, ddd, locationA);
    assertEquals(tablesplit.hashCode(), same.hashCode());
    assertEquals(tablesplit, same);
  }
}
