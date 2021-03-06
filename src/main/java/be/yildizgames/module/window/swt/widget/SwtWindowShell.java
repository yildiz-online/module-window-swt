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
import be.yildizgames.module.coordinate.BaseCoordinate;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.input.MouseOverListener;
import be.yildizgames.module.window.widget.DirectoryChooser;
import be.yildizgames.module.window.widget.OnMinimize;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowCanvas;
import be.yildizgames.module.window.widget.WindowCheckBox;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowImageProviderClassPath;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowMediaPlayer;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowNotification;
import be.yildizgames.module.window.widget.WindowPopup;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellOptions;
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

/**
 * @author Grégory Van den Borre
 */
public class SwtWindowShell extends BaseSwtWindowWidget<WindowShell> implements WindowShell {

    private final Shell shell;

    private final WindowImageProvider imageProvider;

    private final Map<Cursor, SwtWindowCursor> cursorList = new HashMap<>();

    private Cursor currentCursor;

    /**
     * Invisible cursor.
     */
    private final Cursor invisibleCursor;

    private SwtWindowShell(final Shell shell, WindowImageProvider imageProvider) {
        super(shell);
        System.setProperty("SWT_GTK3", "0");
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
        return noClose(new WindowImageProviderClassPath());
    }

    public static SwtWindowShell noClose(WindowImageProvider imageProvider) {
        return new SwtWindowShell(new Shell(Display.getCurrent(), SWT.NO_TRIM | SWT.TRANSPARENT), imageProvider);
    }

    public static SwtWindowShell withClose() {
        return withClose(new WindowImageProviderClassPath());
    }

    public static SwtWindowShell withClose(WindowImageProvider imageProvider) {
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
        return new SwtWindowTextArea(new Text(this.shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL));
    }

    @Override
    public WindowImage createImage(String image) {
        Label label = new Label(this.shell, SWT.NONE);
        return new SwtWindowImage(label, this.imageProvider, image);
    }

    @Override
    public SwtWindowButton createButton() {
        return new SwtWindowButton(new Button(this.shell, SWT.SMOOTH), this.imageProvider);
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
        this.shell.setImage(new Image(this.shell.getDisplay(), this.imageProvider.getImage(file)));
        return this;
    }

    @Override
    public void close() {
        this.shell.dispose();
    }

    @Override
    public SwtWindowButton createButton(String background, String hover) {
        Image backgroundImage = new Image(this.shell.getDisplay(), this.imageProvider.getImage(background));
        Image hoverImage = new Image(this.shell.getDisplay(), this.imageProvider.getImage(hover));
        Button button = new Button(this.shell, SWT.SMOOTH);
        button.setImage(backgroundImage);
        button.addListener(SWT.MouseEnter, e -> button.setImage(hoverImage));
        button.addListener(SWT.MouseExit, e -> button.setImage(backgroundImage));
        return new SwtWindowButton(button, this.imageProvider);
    }

    @Override
    public final WindowShell setBackground(Color color) {
        this.shell.setBackground(SwtConverter.from(color));
        return this;
    }

    @Override
    public final WindowShell setBackground(String background) {
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
        Image tmpImage = new Image(this.shell.getDisplay(), this.imageProvider.getImage(background));
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
    public WindowShell addOnMouseOverListener(MouseOverListener l) {
        return null;
    }

    @Override
    public final WindowShell setSize(Size size) {
        this.shell.setSize(size.width, size.height);
        return this;
    }

    @Override
    public final WindowShell setPosition(Position position) {
        this.shell.setLocation(position.left, position.top);
        return this;
    }

    @Override
    public WindowShell setPosition(int left, int top) {
        return null;
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
        return new SwtWindowTextButton(new Button(this.shell, SWT.SMOOTH), imageProvider);
    }

    @Override
    public WindowInputBox createInputBox() {
        return new SwtWindowInputBox(new Text(this.shell, SWT.SMOOTH));
    }

    @Override
    public WindowShell createChildWindow(WindowShellOptions... windowShellOptions) {
        //FIXME options ignored
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

    @Override
    public WindowFont createFont(String path, int height) {
        return new SwtWindowFont(this.shell,path, height);
    }

    @Override
    public WindowCanvas createCanvas() {
        //FIXME implements
        return new SwtWindowCanvas(null);
    }

    @Override
    public WindowPopup createPopup() {
        return null;
    }

    @Override
    public WindowCheckBox createCheckBox() {
        return null;
    }

    @Override
    public WindowShell addKeyListener(KeyboardListener listener) {
        //FIXME implements
        return this;
    }

    @Override
    public WindowShell toBack() {
        //FIXME implements
        return this;
    }

    @Override
    public WindowShell minimize(OnMinimize... minimizes) {
        //FIXME implements
        return this;
    }

    @Override
    public WindowShell maximize() {
        //FIXME implements
        return this;
    }

    @Override
    public WindowMediaPlayer createMediaPlayer() {
        //FIXME implements
        return null;
    }

    @Override
    public DirectoryChooser createDirectoryChooser() {
        return null;
    }

    @Override
    public void exit() {
        //FIXME implements
    }

    @Override
    public SwtWindowShell requestFocus() {
        //FIXME implements
        return this;
    }

    @Override
    public WindowHandle getHandle() {
        return null;
    }

    @Override
    public WindowShell toFront() {
        //FIXME implements
        return this;
    }

    @Override
    public BaseCoordinate getCoordinates() {
        return new Coordinates(this.shell.getSize().x, this.shell.getSize().y, this.shell.getLocation().x, this.shell.getLocation().y);
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
    public final ScreenSize getSize() {
        return new ScreenSize(this.shell.getBounds().width, this.shell.getBounds().height);
    }

    @Override
    public final ScreenSize getMonitorSize() {
        return new ScreenSize(
                shell.getDisplay().getPrimaryMonitor().getBounds().width,
                shell.getDisplay().getPrimaryMonitor().getBounds().height);
    }

    public final void execute(final Runnable r) {
        this.shell.getDisplay().syncExec(r);
    }

    @Override
    public final void open() {
        this.shell.open();
    }

    public Cursor createCursor(Cursor cursor) {
        final Image image = new Image(this.shell.getDisplay(), this.imageProvider.getImage(cursor.getPath()));
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
    
        @Override
    public final WindowNotification createNotification() {
        return null;
    }
}
