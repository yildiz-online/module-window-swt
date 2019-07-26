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
import be.yildizgames.module.window.BaseWindowEngine;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.WindowThreadManager;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.swt.widget.SwtWindowCanvas;
import be.yildizgames.module.window.swt.widget.SwtWindowShell;
import be.yildizgames.module.window.swt.widget.SwtWindowShellFactory;
import be.yildizgames.module.window.widget.WindowShellFactory;

/**
 * SWT implementation for the window engine.
 *
 * @author Grégory Van den Borre
 */
public final class SwtWindowEngine implements BaseWindowEngine {

    private final SwtWindowShell window;

    private final SwtWindowCanvas canvas;

    private final WindowShellFactory windowShellFactory = new SwtWindowShellFactory();

    private final WindowThreadManager threadManager = new SwtThreadManager();

    /**
     * Simple constructor.
     */
    SwtWindowEngine() {
        super();
        setGtk();
        this.window = SwtWindowShell.noClose();
        ScreenSize screenSize = window.getSize();
        this.window.setFullScreen();
        this.window.setBackground("engine.png");
        this.canvas = this.window.createCanvas( new Coordinates(screenSize.width, screenSize.height, 0, 0));
        this.hideCursor();
        this.window.execute(this.window::open);
    }

    /**
     * Simple constructor.
     */
    public SwtWindowEngine(SwtWindowShell window, Coordinates c) {
        super();
        setGtk();
        this.window = window;
        this.window.setBackground("engine.png");
        this.canvas = this.window.createCanvas(c);
        this.hideCursor();
        this.window.execute(this.window::open);
    }

    private static void setGtk() {
        System.setProperty("SWT_GTK3", "0");
    }

    @Override
    public final WindowThreadManager getThreadManager() {
        return threadManager;
    }


    @Override
    public final void setWindowTitle(final String title) {
        this.window.setTitle(title);
    }

    @Override
    public final void setWindowIcon(final String file) {
        this.window.setIcon(file);
    }

    @Override
    public final WindowShellFactory getWindowShellFactory() {
        return this.windowShellFactory;
    }

    @Override
    public final Cursor createCursor(final Cursor cursor) {
        return this.window.createCursor(cursor);
    }

    @Override
    public final void update() {
        this.window.checkForEvent();
    }

    /**
     * @return The handle to link the game window and the 3d context.
     */
    public final WindowHandle getHandle() {
        return this.canvas.getHandle();
    }

    /**
     * Delete the resources used during loading.
     */
    @Override
    public final void deleteLoadingResources() {
        this.window.showCursor();
    }

    @Override
    public final void setCursor(final Cursor cursor) {
        this.window.setCursor(cursor);
    }

    @Override
    public final void showCursor() {
        this.window.showCursor();
    }

    @Override
    public final void hideCursor() {
        this.window.hideCursor();
    }

    @Override
    public final ScreenSize getScreenSize() {
        return this.window.getSize();
    }

    @Override
    public final void registerInput(final WindowInputListener listener) {
        this.canvas.registerInput(listener);
    }
}
