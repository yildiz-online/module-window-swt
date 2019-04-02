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
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SwtWindowShell extends BaseSwtWindowWidget<WindowShell> implements WindowShell {

    private final Shell shell;

    private final SwtImageProvider imageProvider;

    private final Map<Cursor, SwtWindowCursor> cursorList = new HashMap<>();

    private Cursor currentCursor;

    /**
     * Invisible cursor.
     */
    private final Cursor invisibleCursor;

    private SwtWindowShell(final Shell shell, SwtImageProvider imageProvider) {
        super(shell);
        this.imageProvider = imageProvider;
        this.shell = shell;
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
        final PaletteData palette = new PaletteData(
                this.shell.getDisplay().getSystemColor(SWT.COLOR_WHITE).getRGB(),
                this.shell.getDisplay().getSystemColor(SWT.COLOR_BLACK).getRGB());
        final ImageData sourceData = new ImageData(16, 16, 1, palette);
        sourceData.transparentPixel = 0;
        this.invisibleCursor = new Cursor("Empty", "", 0, 0);
        this.cursorList.put(this.invisibleCursor, new SwtWindowCursor(sourceData, 0, 0));
    }

    public static SwtWindowShell noClose() {
        return noClose(new ClasspathImageProvider());
    }

    public static SwtWindowShell noClose(SwtImageProvider imageProvider) {
        return new SwtWindowShell(new Shell(Display.getCurrent(), SWT.NO_TRIM | SWT.TRANSPARENT), imageProvider);
    }

    public static SwtWindowShell withClose() {
        return withClose(new ClasspathImageProvider());
    }

    public static SwtWindowShell withClose(SwtImageProvider imageProvider) {
        return new SwtWindowShell(new Shell(Display.getCurrent(), SWT.CLOSE | SWT.TRANSPARENT), imageProvider);
    }

    public final void setMovable() {
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
    }

    @Override
    public final SwtWindowProgressBar createProgressBar() {
        return new SwtWindowProgressBar(new ProgressBar(this.shell, SWT.SMOOTH));
    }

    @Override
    public SwtWindowShell setTitle(String text) {
        this.shell.setText(text);
        Display.setAppName(text);
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
    public SwtWindowButton createButton() {
        return new SwtWindowButton(new Button(this.shell, SWT.SMOOTH));
    }

    @Override
    public final SwtWindowText createTextLine() {
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
    public void checkForEvent() {
        this.shell.getDisplay().readAndDispatch();
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
    public final WindowShell setBackground(Color color) {
        this.shell.setBackground(SwtConverter.from(color));
        return this;
    }

    @Override
    public final WindowShell setBackground(String background) {
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
        Image tmpImage = this.imageProvider.getImage(this.shell, background);
        Image backgroundImage = new Image(this.shell.getDisplay(), tmpImage.getImageData().scaledTo(this.shell.getBounds().width, this.shell.getBounds().height));
        this.shell.setBackgroundImage(backgroundImage);
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
    public final WindowTreeRoot createTreeRoot(int height, int width, WindowTreeElement... elements) {
        return SwtWindowTreeRoot.create(this.shell, height, width, elements);
    }

    @Override
    public WindowDropdown createDropdown() {
        return new SwtWindowDropdown(new Combo(this.shell,  SWT.DROP_DOWN | SWT.READ_ONLY));
    }

    @Override
    public WindowButtonText createTextButton() {
        return new SwtWindowTextButton(new Button(this.shell, SWT.SMOOTH));
    }

    @Override
    public WindowInputBox createInputBox() {
        return new SwtWindowInputBox(new Text(this.shell, SWT.SMOOTH));
    }

    @Override
    public WindowShell createChildWindow() {
        return new SwtWindowShell(new Shell(this.shell), this.imageProvider);
    }

    @Override
    public WindowMenuBar createMenuBar(WindowMenuBarElementDefinition... elements) {
        return new SwtWindowMenuBar(this.shell, elements);
    }

    @Override
    public WindowModalFile createOpenFileBox() {
        return new SwtWindowModalFile(this.shell);
    }

    /**
     * Make the window use all the screen and remove the title bar.
     */
    @Override
    public final WindowShell setFullScreen() {
        this.shell.setFullScreen(true);
        this.shell.setFocus();
        final Monitor m = Display.getDefault().getPrimaryMonitor();
        this.shell.setBounds(-1, -1, m.getBounds().width + 2, m.getBounds().height + 2);
        this.shell.setLayout(new FillLayout());
        return this;
    }

    @Override
    public final ScreenSize getScreenSize() {
        return new ScreenSize(this.shell.getBounds().width, this.shell.getBounds().height);
    }

    public final void execute(final Runnable r) {
        this.shell.getDisplay().syncExec(r);
    }

    @Override
    public final void open() {
        this.shell.open();
    }

    public Cursor createCursor(Cursor cursor) {
        final Image image = this.imageProvider.getImage(this.shell, cursor.getPath());
        this.cursorList.put(cursor, new SwtWindowCursor(image.getImageData(), cursor.getX(), cursor.getY()));
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.currentCursor = cursor;
        this.cursorList.get(cursor).activate(this.shell);
    }

    /**
     * Set the mouse cursor visible.
     */
    public void showCursor() {
        this.setCursor(this.currentCursor);
    }

    /**
     * Set the mouse cursor invisible.
     */
    public void hideCursor() {
        this.setCursor(this.invisibleCursor);
    }

    public SwtWindowCanvas createCanvas(Coordinates coordinates) {
        Canvas canvas = new Canvas(shell, SWT.NONE);
        canvas.setBounds(coordinates.left, coordinates.top, coordinates.width, coordinates.height);
        return new SwtWindowCanvas(canvas);
    }
}
