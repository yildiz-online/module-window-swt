/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.module.window.swt.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTextButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SwtWindowShell implements WindowShell {

    private final Shell shell;

    private final SwtImageProvider imageProvider;

    private SwtWindowShell(final Shell shell, SwtImageProvider imageProvider) {
        super();
        this.imageProvider = imageProvider;
        this.shell = shell;
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

        Listener l = new Listener() {
            Point origin;

            @Override
            public void handleEvent(final org.eclipse.swt.widgets.Event e) {
                switch (e.type) {
                    case SWT.MouseDown:
                        this.origin = new Point(e.x, e.y);
                        break;
                    case SWT.MouseUp:
                        this.origin = null;
                        break;
                    case SWT.MouseMove:
                        if (origin != null) {
                            Point p = shell.getDisplay().map(shell, null, e.x, e.y);
                            shell.setLocation(p.x - this.origin.x, p.y - this.origin.y);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid switch case: " + e);
                }
            }
        };
        this.shell.addListener(SWT.MouseDown, l);
        this.shell.addListener(SWT.MouseUp, l);
        this.shell.addListener(SWT.MouseMove, l);
        this.shell.open();
    }

    public static SwtWindowShell noClose(SwtImageProvider imageProvider) {
        return new SwtWindowShell(new Shell(Display.getCurrent(), SWT.NO_TRIM | SWT.TRANSPARENT), imageProvider);
    }

    public static SwtWindowShell withClose(SwtImageProvider imageProvider) {
        return new SwtWindowShell(new Shell(Display.getCurrent(), SWT.CLOSE | SWT.TRANSPARENT), imageProvider);
    }

    @Override
    public final SwtWindowProgressBar createProgressBar() {
        return new SwtWindowProgressBar(new ProgressBar(this.shell, SWT.SMOOTH));
    }

    @Override
    public SwtWindowShell setTitle(String text) {
        this.shell.setText("Yildiz update manager");
        Display.setAppName("Yildiz update manager");
        return this;
    }

    @Override
    public SwtWindowShell setCoordinates(Coordinates coordinates) {
        this.shell.setBounds(SwtConverter.from(coordinates));
        return this;
    }

    @Override
    public SwtWindowModal createMessageBox() {
        return new SwtWindowModal(new MessageBox(this.shell, SWT.NONE));
    }

    @Override
    public SwtWindowModal createMessageButtonBox() {
        return new SwtWindowModal(new MessageBox(this.shell, SWT.OK));
    }

    @Override
    public WindowTextArea createTextArea() {
        return new SwtWindowTextArea(new Text(this.shell, SWT.SMOOTH));
    }

    @Override
    public WindowImage createImage(String image) {
        Label label = new Label(this.shell, SWT.NONE);
        Image i = this.imageProvider.getImage(this.shell, image);
        return new SwtWindowImage(label, i);
    }

    @Override
    public SwtWindowButton createNativeButton() {
        return new SwtWindowButton(new Button(this.shell, SWT.SMOOTH));
    }

    @Override
    public final SwtWindowText createNativeTextLine() {
        Label l = new Label(this.shell, SWT.SMOOTH);
        return new SwtWindowText(l);
    }

    @Override
    public void update() {
        if (!this.shell.getDisplay().readAndDispatch()) {
            this.shell.getDisplay().sleep();
        }
    }

    @Override
    public SwtWindowShell setIcon(String file) {
        this.shell.setImage(this.imageProvider.getImage(this.shell, file));
        return this;
    }

    @Override
    public void close() {
        this.shell.dispose();
    }

    @Override
    public SwtWindowButton createButton(String background, String hover) {
        Image backgroundImage = this.imageProvider.getImage(this.shell, background);
        Image hoverImage = this.imageProvider.getImage(this.shell, hover);
        Button button = new Button(this.shell, SWT.SMOOTH);
        button.setImage(backgroundImage);
        button.addListener(SWT.MouseEnter, e -> button.setImage(hoverImage));
        button.addListener(SWT.MouseExit, e -> button.setImage(backgroundImage));
        return new SwtWindowButton(button);
    }

    @Override
    public int getWidth() {
        return this.shell.getBounds().width;
    }

    @Override
    public int getHeight() {
        return this.shell.getBounds().height;
    }

    @Override
    public WindowShell setBackground(Color color) {
        this.shell.setBackground(SwtConverter.from(color));
        return this;
    }

    @Override
    public WindowShell setSize(int width, int height) {
        this.shell.setSize(width, height);
        return this;
    }

    @Override
    public WindowShell setVisible(boolean visible) {
        this.shell.setVisible(visible);
        return this;
    }

    @Override
    public WindowDropdown createNativeDropdown() {
        return new SwtWindowDropdown(new Combo(this.shell,  SWT.DROP_DOWN | SWT.READ_ONLY));
    }

    @Override
    public WindowTextButton createNativeTextButton() {
        return new SwtWindowTextButton(new Button(this.shell, SWT.SMOOTH));
    }
}
