/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.application.options.colors;

import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.FileStatusFactory;

import java.util.Collection;
import java.util.ArrayList;

class FileStatusColorsPageFactory implements ColorAndFontPanelFactory {
  public NewColorAndFontPanel createPanel(ColorAndFontOptions options) {
    return NewColorAndFontPanel.create(new PreviewPanel.Empty(), ColorAndFontOptions.FILE_STATUS_GROUP, options, collectFileTypes(), null);
  }

  public String getPanelDisplayName() {
    return ColorAndFontOptions.FILE_STATUS_GROUP;
  }

  private static Collection<String> collectFileTypes() {
    ArrayList<String> result = new ArrayList<String>();
    FileStatus[] statuses = FileStatusFactory.getInstance().getAllFileStatuses();

    for (FileStatus status : statuses) {
      result.add(status.getText());
    }
    return result;
  }
}
