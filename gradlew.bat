/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package androidx.test.internal.runner;

import android.app.Instrumentation;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import androidx.test.runner.lifecycle.ApplicationLifecycleCallback;
import androidx.test.runner.screenshot.ScreenCaptureProcessor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.RunnerBuilder;

/** Contains input arguments passed to the instrumentation test runner. */
public class RunnerArgs {
  private static final String LOG_TAG = "RunnerArgs";

  // constants for supported instrumentation arguments
  static final String ARGUMENT_TEST_CLASS = "class";
  static final String ARGUMENT_CLASSPATH_TO_SCAN = "classpathToScan";
  static final String ARGUMENT_NOT_TEST_CLASS = "notClass";
  static final String ARGUMENT_TEST_SIZE = "size";
  static final String ARGUMENT_LOG_ONLY = "log";
  static final String ARGUMENT_ANNOTATION = "annotation";
  sta