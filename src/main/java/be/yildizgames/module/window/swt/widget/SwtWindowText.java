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
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowTextLine;
import org.eclipse.swt.widgets.Label;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowText extends BaseSwtWindowWidget <WindowTextLine> implements WindowTextLine {

    private final Label label;

    SwtWindowText(Label label) {
        super(label);
        this.label = label;
    }

    @Override
    public WindowTextLine setCoordinates(Coordinates coordinates) {
        this.label.setLocation(coordinates.left, coordinates.top);
        this.label.setSize(coordinates.width, coordinates.height);
        return this;
    }

    @Override
    public final WindowTextLine setVisible(boolean visible) {
        this.label.setVisible(visible);
        return this;
    }

    @Override
    public final WindowTextLine setText(String text) {
        this.label.setText(text);
        return this;
    }

    @Override
    public final WindowTextLine setPosition(int left, int top) {
        this.label.setLocation(left, top);
        return this;
    }

    @Override
    public final WindowTextLine setColor(Color color) {
        //this.label.setForeground();
        return this;
    }

    @Override
    public final String getText() {
        return this.label.getText();
    }

    @Override
    public WindowTextLine setUnderline(boolean active) {
        //TODO implements
        return this;
    }

    @Override
    public WindowTextLine setFont(WindowFont font) {
        this.label.setFont(SwtWindowFont.getById(font.getId()).getInnerFont());
        return this;
    }

    @Override
    public final WindowTextLine setSize(Size size) {
        this.label.setSize(size.width, size.height);
        return this;
    }

    @Override
    public final WindowTextLine setPosition(Position position) {
        this.label.setLocation(position.left, position.top);
        return this;
    }
}
