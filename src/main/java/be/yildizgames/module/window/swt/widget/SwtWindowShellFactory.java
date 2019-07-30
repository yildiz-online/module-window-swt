package be.yildizgames.module.window.swt.widget;

import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellFactory;
import be.yildizgames.module.window.widget.WindowShellOptions;

public class SwtWindowShellFactory implements WindowShellFactory {

    @Override
    public WindowShell buildShell(WindowShellOptions... options) {
        return SwtWindowShell.withClose();
    }

}
