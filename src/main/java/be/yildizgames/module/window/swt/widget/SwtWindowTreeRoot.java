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

import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowTreeRoot implements WindowTreeRoot {

    private final Tree tree;

    private final Map<Integer, TreeItem> elementList = new HashMap<>();

    private SwtWindowTreeRoot(Tree tree, int width, int height, WindowTreeElement... elements) {
        super();
        this.tree = tree;
        this.tree.setSize(width, height);
        for(WindowTreeElement element : elements) {
            TreeItem item = new TreeItem(tree, 0);
            item.setText(element.title);
            element.getChildren().forEach(e -> generate(item, e));
            item.setExpanded(true);
            this.elementList.put(element.id, item);
        }
        tree.setEnabled(true);
    }

    static SwtWindowTreeRoot create(Shell shell, int width, int height, WindowTreeElement... elements) {
        Tree tree = new Tree(shell, SWT.NONE);
        tree.setBackground(shell.getBackground());
        return new SwtWindowTreeRoot(tree, width, height, elements);
    }

    private static void generate(TreeItem parent, WindowTreeElement element) {
        TreeItem item = new TreeItem(parent, 0);
        item.setText(element.title);
        for(WindowTreeElement e : element.getChildren()) {
            generate(item, e);
        }
        item.setExpanded(true);
    }

    public void delete() {
        this.tree.dispose();
    }

    public TreeItem getElement(int id) {
        return elementList.get(id);
    }
}
