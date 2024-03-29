/*  
 * Copyright 2008 CoreMedia AG, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an AS IS BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.coremedia.iso.boxes;

import com.coremedia.iso.BoxFactory;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoInputStream;
import com.coremedia.iso.IsoOutputStream;

import java.io.IOException;

/**
 * The video media header contains general presentation information, independent of the coding, for video
 * media. Note that the flags field has the value 1.
 */
public class VideoMediaHeaderBox extends FullBox {
  private int graphicsmode;
  private int[] opcolor;
  public static final String TYPE = "vmhd";

  public VideoMediaHeaderBox() {
    super(IsoFile.fourCCtoBytes(TYPE));
  }

  public int getGraphicsmode() {
    return graphicsmode;
  }

  public int[] getOpcolor() {
    return opcolor;
  }

  public String getDisplayName() {
    return "Video Media Header Box";
  }

  protected long getContentSize() {
    return opcolor.length * 2 + 2;
  }


  public void parse(IsoInputStream in, long size, BoxFactory boxFactory, Box lastMovieFragmentBox) throws IOException {
    super.parse(in, size, boxFactory, lastMovieFragmentBox);
    graphicsmode = in.readUInt16();
    opcolor = new int[3];
    for (int i = 0; i < 3; i++) {
      opcolor[i] = in.readUInt16();
    }
  }

  protected void getContent(IsoOutputStream isos) throws IOException {
    isos.writeUInt16(graphicsmode);
    for (int anOpcolor : opcolor) {
      isos.writeUInt16(anOpcolor);
    }
  }

  public String toString() {
    return "VideoMediaHeaderBox[graphicsmode=" + getGraphicsmode() + ";opcolor0=" + getOpcolor()[0] + ";opcolor1=" + getOpcolor()[1] + ";opcolor2=" + getOpcolor()[2] + "]";
  }
}
