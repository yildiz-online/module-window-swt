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
import be.yildizgames.module.window.widget.WindowProgressBar;
import be.yildizgames.module.window.widget.WindowTextArea;
import org.eclipse.swt.widgets.Text;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowTextArea extends BaseSwtWindowWidget<WindowTextArea> implements WindowTextArea {

    private final Text textArea;

    SwtWindowTextArea(Text textArea) {
        super(textArea);
        this.textArea = textArea;
    }

    @Override
    public WindowTextArea setVisible(boolean visible) {
        this.textArea.setVisible(visible);
        return this;
    }

    @Override
    public WindowTextArea setCoordinates(Coordinates coordinates) {
        this.textArea.setBounds(SwtConverter.from(coordinates));
        return this;
    }

    @Override
    public WindowTextArea setText(String text) {
        this.textArea.setText(text);
        return this;
    }

    @Override
    public WindowTextArea setBackground(Color color) {
        this.textArea.setBackground(SwtConverter.from(color));
        return this;
    }

    @Override
    public WindowTextArea setFont(WindowFont font) {
        this.textArea.setFont(SwtWindowFont.getById(font.getId()).getInnerFont());
        return this;
    }

    @Override
    public final WindowTextArea setForeground(Color color) {
        this.textArea.setForeground(SwtConverter.from(color));
        return this;
    }

    @Override
    public final WindowTextArea setSize(Size size) {
        this.textArea.setSize(size.width, size.height);
        return this;
    }

    @Override
    public final WindowTextArea setPosition(Position position) {
        this.textArea.setLocation(position.left, position.top);
        return this;
    }
}
