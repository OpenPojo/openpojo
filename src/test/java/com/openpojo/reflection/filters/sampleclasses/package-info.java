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

/**
 * This package holdes the sample classes necessary for testing reflection.
 *
 * @author oshoukry
 *
 * The annotation @SomeAnnotation was thrown here because ant will automatically filter out package-info
 * out of the test classes, causing the script to fail upon checking counts of elements returned.<br>
 * <br>
 * Eclipse on the other hand will compile package-info in the class path without any problems.
 */
@SampleAnnotation package com.openpojo.reflection.filters.sampleclasses;
