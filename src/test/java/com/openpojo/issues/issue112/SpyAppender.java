/*
 * Copyright (c) 2010-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.issues.issue112;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author oshoukry
 */
@SuppressWarnings("ReturnOfNull")
class SpyAppender implements Appender {
  private List<LoggingEvent> eventList = new ArrayList<LoggingEvent>();

  public List<LoggingEvent> getEventList() {
    return eventList;
  }

  public void addFilter(Filter newFilter) {

  }

  public Filter getFilter() {
    return null;
  }

  public void clearFilters() {

  }

  public void close() {

  }

  public void doAppend(LoggingEvent event) {
    eventList.add(event);
  }

  public String getName() {
    return null;
  }

  public void setErrorHandler(ErrorHandler errorHandler) {

  }

  public ErrorHandler getErrorHandler() {
    return null;
  }

  public void setLayout(Layout layout) {

  }

  public Layout getLayout() {
    return null;
  }

  public void setName(String name) {
  }

  public boolean requiresLayout() {
    return false;
  }
}
