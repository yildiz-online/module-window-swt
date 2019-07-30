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
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowImage extends BaseSwtWindowWidget<WindowImage> implements WindowImage {

    private final Label container;

    private final WindowImageProvider provider;

    SwtWindowImage(Label container, WindowImageProvider provider, String image) {
        super(container);
        this.container = container;
        this.provider = provider;
        this.container.setImage(new Image(this.container.getDisplay(), provider.getImage(image)));
    }

    @Override
    public final SwtWindowImage setCoordinates(Coordinates coordinates) {
        this.container.setBounds(SwtConverter.from(coordinates));
        this.container.pack();
        return this;
    }

    @Override
    public final WindowImage setVisible(boolean visible) {
        this.container.setVisible(visible);
        return this;
    }

    @Override
    public final WindowImage setSize(Size size) {
        this.container.setSize(size.width, size.height);
        return this;
    }

    @Override
    public final WindowImage setPosition(Position position) {
        this.container.setLocation(position.left, position.top);
        return this;
    }

    @Override
    public WindowImage setImage(String url) {
        this.container.setImage(new Image(this.container.getDisplay(), provider.getImage(url)));
        return this;
    }
}
