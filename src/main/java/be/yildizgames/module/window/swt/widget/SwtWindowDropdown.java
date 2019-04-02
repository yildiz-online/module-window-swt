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
import be.yildizgames.module.window.widget.WindowDropdown;
import org.eclipse.swt.widgets.Combo;

import java.util.Arrays;

class SwtWindowDropdown extends BaseSwtWindowWidget<WindowDropdown> implements WindowDropdown {

    private final Combo combo;

    SwtWindowDropdown(Combo combo) {
        super(combo);
        this.combo = combo;
    }

    @Override
    public final WindowDropdown setCoordinates(Coordinates coordinates) {
        this.combo.setBounds(SwtConverter.from(coordinates));
        return this;
    }

    @Override
    public final WindowDropdown setVisible(boolean visible) {
        this.combo.setVisible(visible);
        return this;
    }

    @Override
    public final WindowDropdown select(int line) {
        this.combo.select(line);
        return this;
    }

    @Override
    public WindowDropdown setItems(Object... items) {
        this.combo.setItems(Arrays.stream(items)
                .map(Object::toString)
                .toArray(String[]::new));
        return this;
    }

    @Override
    public final int getSelectionIndex() {
        return this.combo.getSelectionIndex();
    }
}
