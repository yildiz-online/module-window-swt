/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.module.window.swt;

import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.window.swt.widget.SwtWindowCanvas;
import be.yildizgames.module.window.swt.widget.SwtWindowShell;

/**
 * Game main rendering window.
 *
 * @author Grégory Van den Borre
 * @deprecated Move to engine client (ui addon?)
 */
@Deprecated(since = "now", forRemoval = true)
public final class SwtGameWindow {

    /**
     * Canvas for the window and the 3d context.
     */
    private SwtWindowCanvas canvas;

    /**
     * Constructor.
     */
    public SwtGameWindow() {
        super();
    }

    /**
     * Build the window in the SWT thread.
     *
     * @param window Window to use as container.
     */
    public void initialize(final SwtWindowShell window, boolean fullScreenMode, Coordinates c) {
        if (fullScreenMode) {
            window.setFullScreen();
        }
        window.hideCursor();
        window.setBackground("engine.png");
        this.canvas = window.createCanvas(c);
    }

    /**
     * @return The SWT canvas.
     */
    public SwtWindowCanvas getCanvas() {
        return this.canvas;
    }

}
