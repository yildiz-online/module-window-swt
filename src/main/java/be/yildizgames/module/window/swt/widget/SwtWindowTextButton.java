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
import be.yildizgames.module.window.widget.WindowTextButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;

import java.util.Optional;

class SwtWindowTextButton extends SwtWindowButton implements WindowTextButton {

    private PaintListener paintListener;

    private final ButtonHover bh;

    private Color background = Color.WHITE;

    private Color highlightColor = Color.GRAY;

    private Color fontColor = Color.BLACK;

    SwtWindowTextButton(Button button) {
        super(button);
        this.bh = new ButtonHover();
        this.button.addMouseTrackListener(new MouseTrackListener() {
            @Override
            public void mouseHover(final MouseEvent e) {
                bh.hover = true;
            }

            @Override
            public void mouseExit(final MouseEvent e) {
                bh.hover = false;
            }

            @Override
            public void mouseEnter(final MouseEvent e) {
                bh.hover = true;
            }
        });
    }

    @Override
    public final WindowTextButton setText(String text) {
        Image background = new Image(this.button.getShell().getDisplay(), this.button.getBounds().width, this.button.getBounds().height);
        org.eclipse.swt.graphics.Color c = SwtConverter.from(this.background);
        org.eclipse.swt.graphics.Color h = SwtConverter.from(this.highlightColor);
        org.eclipse.swt.graphics.Color t = SwtConverter.from(this.fontColor);
        Optional.ofNullable(this.paintListener).ifPresent(this.button::removePaintListener);
        this.paintListener = e -> {
            GC gcBack = new GC(background);
            gcBack.setAntialias(SWT.ON);
            gcBack.setBackground(bh.hover ? h : c);
            gcBack.setAlpha(255);
            gcBack.fillRectangle(0, 0, button.getBounds().width, button.getBounds().height);
            gcBack.setForeground(t);
            gcBack.setFont(this.button.getFont());
            int textWidth = gcBack.getFontMetrics().getAverageCharWidth() * text.length();
            gcBack.drawText(text, (background.getBounds().width - textWidth) >> 1, 8, true);
            gcBack.setAlpha(255);
            e.gc.drawImage(background, 0, 0);
            gcBack.dispose();
        };
        this.button.addPaintListener(this.paintListener);
        this.button.setText(text);
        return this;
    }

    /**
     * Simple wrapper to be used in anonymous class.
     *
     * @author Van den Borre Grégory
     */
    private final class ButtonHover {

        /**
         * Flag if mouse is over button or not.
         */
        private boolean hover = false;
    }
}
