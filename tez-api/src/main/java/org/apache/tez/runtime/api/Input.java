/**
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

package org.apache.tez.runtime.api;

import java.util.List;

/**
 * Represents an input through which a TezProcessor receives data on an edge.
 * </p>
 *
 * <code>Input</code> classes must have a 0 argument public constructor for Tez
 * to construct the <code>Input</code>. Tez will take care of initializing and
 * closing the Input after a {@link Processor} completes. </p>
 */
public interface Input {

  /**
   * Initializes the <code>Input</code>.
   *
   * @param inputContext
   *          the {@link TezInputContext}
   * @return list of events that were generated during initialization
   * @throws Exception
   *           if an error occurs
   */
  public List<Event> initialize(TezInputContext inputContext)
      throws Exception;

  /**
   * Start any processing that the Input may need to perform. This, for now, is
   * always invoked by the framework.
   * 
   * The implementation of Input is expected to be non blocking. Inputs should
   * see this as a signal to start processing, but must return control to the
   * framework before the Processor actually starts.
   * 
   * Inputs should be written to handle multiple start invocations - typically
   * honoring only the first one.
   * 
   * @return list of events that were generated during start
   * @throws Exception
   */
  public List<Event> start() throws Exception;
  
  /**
   * Gets an instance of the {@link Reader} for this <code>Output</code>
   *
   * @return Gets an instance of the {@link Reader} for this <code>Output</code>
   * @throws Exception
   *           if an error occurs
   */
  public Reader getReader() throws Exception;

  /**
   * Handles user and system generated {@link Event}s, which typically carry
   * information such as an output being available on the previous vertex.
   *
   * @param inputEvents
   *          the list of {@link Event}s
   */
  public void handleEvents(List<Event> inputEvents) throws Exception;

  /**
   * Closes the <code>Input</code>
   *
   * @return list of events that were generated during close
   * @throws Exception
   *           if an error occurs
   */
  public List<Event> close() throws Exception;
}
