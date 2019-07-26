package be.yildizgames.module.window.swt.widget;

import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellFactory;

public class SwtWindowShellFactory implements WindowShellFactory {

    @Override
    public WindowShell buildShell() {
        return SwtWindowShell.withClose();
    }

    @Override
    public WindowShell buildShellWithClose() {
        return SwtWindowShell.withClose();
    }

    @Override
    public WindowShell buildShellNoClose(WindowImageProvider provider) {
        return SwtWindowShell.noClose(provider);
    }
}
