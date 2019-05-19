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

import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowMenuElement;
import be.yildizgames.module.window.widget.WindowMenuElementDefinition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
class SwtWindowMenuBar implements WindowMenuBar {

    private final Map<Integer, WindowMenuElement> items = new HashMap<>();

    SwtWindowMenuBar(Shell shell, WindowMenuBarElementDefinition... barElements) {
        super();
        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);
        for(WindowMenuBarElementDefinition e : barElements) {
            this.addToMenu(menu, e);
        }
        menu.setVisible(true);
    }

    private void addToMenu(Menu menu, WindowMenuBarElementDefinition e) {
        MenuItem title = new MenuItem(menu, SWT.CASCADE);
        title.setText("&" + e.title);
        Menu sub = new Menu(menu.getShell(), SWT.DROP_DOWN);
        title.setMenu(sub);
        e.getChildren().forEach(elmt -> createMenuElement(sub, elmt));
    }

    private void createMenuElement(Menu parent, WindowMenuElementDefinition e) {
        MenuItem p = new MenuItem(parent, SWT.PUSH);
        p.setText("&" + e.title);
        p.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                e.listener.select();
            }
        });
        this.items.put(e.id, new SwtWindowMenuElement(p));
    }

    @Override
    public final Optional<WindowMenuElement> getItemById(int id) {
        return Optional.ofNullable(this.items.get(id));
    }
}
