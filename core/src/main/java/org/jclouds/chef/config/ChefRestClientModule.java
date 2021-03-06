/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.chef.config;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.jclouds.chef.ChefApi;
import org.jclouds.chef.ChefAsyncApi;
import org.jclouds.chef.domain.Client;
import org.jclouds.chef.functions.ClientForGroup;
import org.jclouds.chef.functions.RunListForGroup;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.scriptbuilder.domain.Statement;
import org.jclouds.scriptbuilder.statements.chef.InstallChefGems;

import com.google.common.collect.MapMaker;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provides;
import com.google.inject.name.Names;

/**
 * Configures the Chef connection.
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
public class ChefRestClientModule extends BaseChefRestClientModule<ChefApi, ChefAsyncApi> {

   public ChefRestClientModule() {
      super(TypeToken.of(ChefApi.class), TypeToken.of(ChefAsyncApi.class));
   }

   @Provides
   @Singleton
   Map<String, List<String>> runListForGroup(RunListForGroup runListForGroup) {
      return new MapMaker().makeComputingMap(runListForGroup);
   }

   @Provides
   @Singleton
   Map<String, Client> tagToClient(ClientForGroup groupToClient) {
      return new MapMaker().makeComputingMap(groupToClient);
   }

   @Override
   protected void configure() {
      bind(Statement.class).annotatedWith(Names.named("installChefGems")).to(InstallChefGems.class);
      super.configure();
   }

}
