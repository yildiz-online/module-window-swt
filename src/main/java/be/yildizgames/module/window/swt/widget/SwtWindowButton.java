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

import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.input.MouseLeftClickListener;
import be.yildizgames.module.window.input.MouseOverListener;
import be.yildizgames.module.window.widget.WidgetEvent;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowImageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowButton extends BaseSwtWindowWidget<WindowButton> implements WindowButton {


    protected final Button button;

    private final WindowImageProvider imageProvider;

    SwtWindowButton(Button button, WindowImageProvider imageProvider) {
        super(button);
        this.button = button;
        this.imageProvider = imageProvider;
    }

    @Override
    public final WindowButton setVisible(boolean visible) {
        this.button.setVisible(visible);
        return this;
    }

    @Override
    public WindowButton addOnMouseOverListener(MouseOverListener l) {
        return null;
    }

    @Override
    public WindowButton fireEvent(WidgetEvent event) {
        return null;
    }

    @Override
    public WindowButton setCoordinates(Coordinates coordinates) {
        this.button.setBounds(SwtConverter.from(coordinates));
        return this;
    }

    @Override
    public WindowButton setSize(Size size) {
        this.button.setSize(size.width, size.height);
        return this;
    }

    @Override
    public WindowButton requestFocus() {
        return this;
    }

    @Override
    public WindowButton setSize(int width, int height) {
        this.button.setSize(width, height);
        return this;
    }

    @Override
    public WindowButton setPosition(Position position) {
        this.button.setLocation(position.left, position.top);
        return this;
    }

    @Override
    public WindowButton setPosition(int left, int top) {
        this.button.setLocation(left, top);
        return this;
    }

    @Override
    public final WindowButton addMouseLeftClickListener(MouseLeftClickListener l) {
        this.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                if(e.button == SWT.Selection) {
                    l.click();
                }
            }
        });
        return this;
    }

    @Override
    public final WindowButton setBackground(String image) {
        this.button.setImage(new Image(Display.getCurrent(), this.imageProvider.getImage(image)));
        return this;
    }
}

