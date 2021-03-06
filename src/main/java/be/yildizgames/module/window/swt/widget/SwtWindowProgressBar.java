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
import be.yildizgames.module.window.widget.WindowProgressBar;
import org.eclipse.swt.widgets.ProgressBar;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowProgressBar extends BaseSwtWindowWidget<WindowProgressBar> implements WindowProgressBar {

    private final ProgressBar progressBar;


    SwtWindowProgressBar(ProgressBar progressBar) {
        super(progressBar);
        this.progressBar = progressBar;
    }

    @Override
    public final WindowProgressBar setVisible(boolean visible) {
        this.progressBar.setVisible(visible);
        return this;
    }

    @Override
    public final WindowProgressBar setProgress(int progress) {
        this.progressBar.setSelection(progress);
        return this;
    }

    @Override
    public final WindowProgressBar setCoordinates(Coordinates coordinates) {
        this.progressBar.setBounds(SwtConverter.from(coordinates));
        return this;
    }

    @Override
    public final WindowProgressBar setSize(Size size) {
        this.progressBar.setSize(size.width, size.height);
        return this;
    }

    @Override
    public WindowProgressBar requestFocus() {
        return this;
    }

    @Override
    public final WindowProgressBar setPosition(Position position) {
        this.progressBar.setLocation(position.left, position.top);
        return this;
    }
}
