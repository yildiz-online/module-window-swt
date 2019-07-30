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
package be.yildizgames.module.window.swt.widget;

import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowInputBoxChangeListener;
import org.eclipse.swt.widgets.Text;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowInputBox extends BaseSwtWindowWidget<WindowInputBox> implements WindowInputBox {

    private final Text input;

    SwtWindowInputBox(Text text) {
        super(text);
        this.input = text;
    }

    @Override
    public final WindowInputBox setVisible(boolean visible) {
        this.input.setVisible(visible);
        return this;
    }

    @Override
    public final WindowInputBox setText(String text) {
        this.input.setText(text);
        return this;
    }

    @Override
    public final String getText() {
        return this.input.getText();
    }

    @Override
    public WindowInputBox setToolTip(String tooltip) {
        this.input.setToolTipText(tooltip);
        return this;
    }

    @Override
    public WindowInputBox onChange(WindowInputBoxChangeListener l) {
        this.input.addModifyListener(modifyEvent -> l.onChange());
        return this;
    }

    @Override
    public final WindowInputBox setSize(Size size) {
        this.input.setSize(size.width, size.height);
        return this;
    }

    @Override
    public final WindowInputBox setPosition(Position position) {
        this.input.setLocation(position.left, position.top);
        return this;
    }

    @Override
    public final WindowInputBox setCoordinates(Coordinates coordinates) {
        this.input.setBounds(SwtConverter.from(coordinates));
        return this;
    }
}
