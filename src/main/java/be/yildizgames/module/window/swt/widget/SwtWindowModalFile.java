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

import be.yildizgames.module.window.widget.WindowModalFileOpenListener;
import be.yildizgames.module.window.widget.WindowModalFile;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowModalFile implements WindowModalFile {

    private final FileDialog fileDialog;

    SwtWindowModalFile(Shell parent) {
        super();
        this.fileDialog = new FileDialog(parent,0);
    }

    @Override
    public WindowModalFile setTitle(String title) {
        this.fileDialog.setText(title);
        return this;
    }

    @Override
    public WindowModalFile setPath(String path) {
        this.fileDialog.setFilterPath(path);
        return this;
    }

    @Override
    public WindowModalFile setExtensions(String... extensions) {
        this.fileDialog.setFilterExtensions(extensions);
        return this;
    }

    @Override
    public final WindowModalFile onOpen(WindowModalFileOpenListener listener) {
        String selected = this.fileDialog.open();
        Optional.ofNullable(selected).ifPresent(s -> listener.open(Paths.get(s)));
        return this;
    }
}
